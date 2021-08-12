package xyz.przemyk.real_minerals;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public class ElectricMachineEnergyStorage extends EnergyStorage {

    public ElectricMachineEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ElectricMachineEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    //used internally in generators
    public void addEnergy(int energy) {
        this.energy += Math.min(capacity - this.energy, energy);
    }

    //used internally in machines
    public void removeEnergy(int energy) {
        this.energy -= Math.min(this.energy, energy);
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
        if (tileEntity != null) {
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
}
