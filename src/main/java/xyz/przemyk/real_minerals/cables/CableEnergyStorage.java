package xyz.przemyk.real_minerals.cables;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CableEnergyStorage implements IEnergyStorage {
    public static final int CAPACITY_PER_CABLE = 1000;
    public final CableNetwork cableNetwork;

    protected int energy;

    public CableEnergyStorage(CableNetwork cableNetwork) {
        this.cableNetwork = cableNetwork;
    }

    // used only to deserialize so doesn't mark world data dirty
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void trySendToNeighbors(IBlockReader world, BlockPos pos, int maxExtract) {
        for (Direction side : Direction.values()) {
            if (energy <= 0) {
                return;
            }
            trySendTo(world, pos, side, maxExtract);
        }
    }

    public void trySendTo(IBlockReader world, BlockPos pos, Direction side, int maxExtract) {
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

    public int maxExtractPerConnection() {
        return energy / cableNetwork.getSize();
    }

    public int maxReceivePerConnection() {
        return CAPACITY_PER_CABLE;
    }

    @Override
    public int getMaxEnergyStored() {
        return CAPACITY_PER_CABLE * cableNetwork.getSize();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(getMaxEnergyStored() - energy, Math.min(maxReceivePerConnection(), maxReceive));
        if (!simulate) {
            energy += energyReceived;

            if (energyReceived > 0) {
                cableNetwork.worldData.markDirty();
            }
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(energy, maxExtract);
        if (!simulate) {
            energy -= energyExtracted;

            if (energyExtracted > 0) {
                cableNetwork.worldData.markDirty();
            }
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
