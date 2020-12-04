package xyz.przemyk.real_minerals.machines.electric.battery;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class BatteryEnergyStorage extends EnergyStorage {

    public final Input input;
    public final Output output;
    public final BatteryTileEntity tileEntity;

    public BatteryEnergyStorage(int capacity, int maxTransfer, BatteryTileEntity tileEntity) {
        super(capacity, maxTransfer);
        this.input = new Input(this);
        this.output = new Output(this);
        this.tileEntity = tileEntity;
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

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = super.receiveEnergy(maxReceive, simulate);
        if (received > 0) {
            tileEntity.updateBlockState();
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = super.extractEnergy(maxExtract, simulate);
        if (extracted > 0) {
            tileEntity.updateBlockState();
        }
        return extracted;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public static class Input implements IEnergyStorage {

        private final BatteryEnergyStorage batteryEnergyStorage;

        public Input(BatteryEnergyStorage batteryEnergyStorage) {
            this.batteryEnergyStorage = batteryEnergyStorage;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return batteryEnergyStorage.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return 0;
        }

        @Override
        public int getEnergyStored() {
            return batteryEnergyStorage.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return batteryEnergyStorage.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return false;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    }

    public static class Output implements IEnergyStorage {

        private final BatteryEnergyStorage batteryEnergyStorage;

        public Output(BatteryEnergyStorage batteryEnergyStorage) {
            this.batteryEnergyStorage = batteryEnergyStorage;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return 0;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return batteryEnergyStorage.extractEnergy(maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() {
            return batteryEnergyStorage.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return batteryEnergyStorage.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    }
}
