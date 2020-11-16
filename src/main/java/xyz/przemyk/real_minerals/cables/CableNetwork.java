package xyz.przemyk.real_minerals.cables;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.*;

public class CableNetwork implements INBTSerializable<CompoundNBT> {

    private final Set<BlockPos> cables;
    public final CableNetworksWorldData worldData;
    public CableEnergyStorage energyStorage;

    public LazyOptional<CableEnergyStorage> energyStorageLazyOptional;

    private String ID;

    public CableNetwork(String id, CableNetworksWorldData worldData) {
        this(worldData);
        this.ID = id;
    }

    public CableNetwork(CableNetworksWorldData worldData) {
        this.energyStorage = new CableEnergyStorage(this);
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
        this.cables = new HashSet<>();
        this.worldData = worldData;
    }

    public void addCable(CableTileEntity cableTileEntity) {
        cables.add(cableTileEntity.getPos());
        worldData.markDirty();
    }

    public int getSize() {
        return cables.size();
    }

    /**
     * Merges this network to other. Removes THIS network.
     * @param other the other network
     */
    public void mergeInto(CableNetwork other, ServerWorld world) {
        if (other == this) {
            return;
        }

        remove();
        for (BlockPos blockPos : cables) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof CableTileEntity) {
                ((CableTileEntity) tileEntity).setNetworkID(other.getID());
                other.cables.add(blockPos);
            }
        }
    }

    private void remove() {
        worldData.getNetworks().remove(getID());
        worldData.markDirty();
        energyStorageLazyOptional.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (BlockPos blockPos : cables) {
            arrayList.add(blockPos.getX());
            arrayList.add(blockPos.getY());
            arrayList.add(blockPos.getZ());
        }

        compoundNBT.putIntArray("blocks", arrayList);
        compoundNBT.putInt("energy", energyStorage.getEnergyStored());
        compoundNBT.putString("networkID", getID());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        int[] blocks = nbt.getIntArray("blocks");
        cables.clear();
        for (int i = 0; i < blocks.length / 3; ++i) {
            int n = i * 3;
            cables.add(new BlockPos(blocks[n], blocks[n + 1], blocks[n + 2]));
        }

        energyStorage.setEnergy(nbt.getInt("energy"));
        ID = nbt.getString("networkID");
    }

    public String getID() {
        return ID;
    }

    public void removeCable(BlockPos removedPos, ServerWorld world, BlockState removedState) {
        cables.remove(removedPos);
        if (cables.size() == 0) {
            remove();
            return;
        }

        Set<BlockPos> connectedBlocks = new HashSet<>();
        for (Direction direction : Direction.values()) {
            BooleanProperty property = CableBlock.getPropertyFromDirection(direction);
            if (removedState.get(property)) {
                BlockPos connectedBlock = removedPos.offset(direction);
                if (world.getBlockState(connectedBlock).getBlock() instanceof CableBlock) {
                    connectedBlocks.add(connectedBlock);
                }
            }
        }

        if (connectedBlocks.size() > 1) {
            Set<Set<BlockPos>> newNetworks = new HashSet<>();
            for (BlockPos blockPos : connectedBlocks) {
                Set<BlockPos> scannedPositions = new HashSet<>();
                scannedPositions.add(removedPos);
                if (!dooNotSplitNetworks(world, blockPos, scannedPositions, connectedBlocks)) {
                    scannedPositions.remove(removedPos);
                    newNetworks.add(scannedPositions);
                }
            }

            Map<BlockPos, Set<BlockPos>> combinedNewNetworks = new HashMap<>();
            for (BlockPos blockPos : connectedBlocks) {
                for (Set<BlockPos> newNetwork : newNetworks) {
                    if (newNetwork.contains(blockPos)) {
                        if (combinedNewNetworks.containsKey(blockPos)) {
                            combinedNewNetworks.get(blockPos).addAll(newNetwork);
                        } else {
                            combinedNewNetworks.put(blockPos, newNetwork);
                        }
                    }
                }
            }

            CableNetworksWorldData worldData = CableNetworksWorldData.get(world);
            for (Set<BlockPos> newNetworkCables : combinedNewNetworks.values()) {
                CableNetwork newNetwork = worldData.createNetwork();
                for (BlockPos blockPos : newNetworkCables) {
                    TileEntity tileEntity = world.getTileEntity(blockPos);
                    if (tileEntity instanceof CableTileEntity) {
                        ((CableTileEntity) tileEntity).setNetworkID(newNetwork.getID());
                        newNetwork.cables.add(blockPos);
                    }
                }
            }
        }

        worldData.markDirty();
    }

    /**
     * Tries to find if currentPos connects to the removed cable.
     * @param world server world
     * @param currentPos current scanning position
     * @param scannedPositions already scanned positions, to not go back
     * @param connectedToRemovedCable blocks connected to the removed cable
     * @return should the network stay the same, without splitting?
     */
    private boolean dooNotSplitNetworks(ServerWorld world, BlockPos currentPos, Set<BlockPos> scannedPositions, final Set<BlockPos> connectedToRemovedCable) {
        Set<BlockPos> connectedBlocks = new HashSet<>();
        BlockState blockState = world.getBlockState(currentPos);
        for (Direction direction : Direction.values()) {
            BooleanProperty property = CableBlock.getPropertyFromDirection(direction);
            if (blockState.get(property)) {
                BlockPos connectedBlock = currentPos.offset(direction);
                if (world.getBlockState(connectedBlock).getBlock() instanceof CableBlock) {
                    if (scannedPositions.contains(connectedBlock)) {
                        continue;
                    }
                    if (connectedToRemovedCable.contains(connectedBlock)) {
                        return true;
                    }
                    connectedBlocks.add(connectedBlock);
                }
            }
        }

        scannedPositions.add(currentPos);
        boolean split = true;
        for (BlockPos connectedBlock : connectedBlocks) {
            //I don't know about any way to do this without recurrence
            if (dooNotSplitNetworks(world, connectedBlock, scannedPositions, connectedToRemovedCable)) {
                split = false;
            }
        }
        return !split;
    }
}
