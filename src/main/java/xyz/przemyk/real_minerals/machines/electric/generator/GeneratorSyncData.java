package xyz.przemyk.real_minerals.machines.electric.generator;

import net.minecraft.util.IIntArray;

public class GeneratorSyncData implements IIntArray {
    private final BurningGeneratorTileEntity machine;

    public GeneratorSyncData(BurningGeneratorTileEntity crusher) {
        this.machine = crusher;
    }

    public int get(int index) {
        switch(index) {
            case 0:
                return machine.burnTime;
            case 1:
                return machine.burnTimeTotal;
            case 2:
                return machine.energyStorage.getEnergyStored();
            default:
                return 0;
        }
    }

    public void set(int index, int value) {
        switch(index) {
            case 0:
                machine.burnTime = value;
                break;
            case 1:
                machine.burnTimeTotal = value;
                break;
            case 2:
                machine.energyStorage.setEnergy(value);
        }

    }

    public int size() {
        return 3;
    }
}
