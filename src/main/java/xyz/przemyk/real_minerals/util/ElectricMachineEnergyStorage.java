package xyz.przemyk.real_minerals.util;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
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

    public void trySendToNeighbors(BlockGetter world, BlockPos pos) {
        for (Direction side : Direction.values()) {
            if (energy <= 0) {
                return;
            }
            trySendTo(world, pos, side);
        }
    }

    public void trySendTo(BlockGetter world, BlockPos pos, Direction side) {
        BlockEntity tileEntity = world.getBlockEntity(pos.relative(side));
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
