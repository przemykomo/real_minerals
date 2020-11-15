package xyz.przemyk.real_minerals.cables;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CableNetwork implements INBTSerializable<CompoundNBT> {

    private final Set<BlockPos> cables;
    public CableEnergyStorage energyStorage;

    public LazyOptional<CableEnergyStorage> energyStorageLazyOptional;

    private String ID;

    public CableNetwork(String id) {
        this();
        this.ID = id;
    }

    public CableNetwork() {
        this.energyStorage = new CableEnergyStorage();
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
        this.cables = new HashSet<>();
    }

    public void addCable(CableTileEntity cableTileEntity) {
        cables.add(cableTileEntity.getPos());
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

        CableNetworksWorldData.get(world).getNetworks().remove(getID());
        remove();
        for (BlockPos blockPos : cables) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof CableTileEntity) {
                ((CableTileEntity) tileEntity).networkID = other.getID();
                other.cables.add(blockPos);
            }
        }
    }

    private void remove() {
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
    }

    public String getID() {
        return ID;
    }
}
