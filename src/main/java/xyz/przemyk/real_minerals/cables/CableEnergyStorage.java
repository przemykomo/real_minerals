package xyz.przemyk.real_minerals.cables;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class CableEnergyStorage extends EnergyStorage {
    public static final int CAPACITY = 500; //TODO
    private final CableNetwork cableNetwork;

    public CableEnergyStorage(CableNetwork cableNetwork) {
        super(CAPACITY);
        this.cableNetwork = cableNetwork;
    }

    // used only to deserialize so doesn't mark world data dirty
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void trySendToNeighbors(IBlockReader world, BlockPos pos) {
        for (Direction side : Direction.values()) {
            if (energy <= 0) {
                return;
            }
            trySendTo(world, pos, side);
        }
    }

    public void trySendTo(IBlockReader world, BlockPos pos, Direction side) {
        TileEntity tileEntity = world.getTileEntity(pos.offset(side));
        if (tileEntity != null && !(tileEntity instanceof CableTileEntity)) {
            tileEntity.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).ifPresent(other -> {
                if (other.canReceive()) {
                    int toSend = extractEnergy(maxExtract, true);
                    int sent = other.receiveEnergy(toSend, false);
                    if (sent > 0) {
                        extractEnergy(sent, false);
                    }
                }
            });
        }
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (!simulate && extracted > 0) {
            cableNetwork.worldData.markDirty();
        }
        return extracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (!simulate && received > 0) {
            cableNetwork.worldData.markDirty();
        }
        return received;
    }
}
