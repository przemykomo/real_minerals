package xyz.przemyk.real_minerals.machines.not_electric;

import net.minecraft.util.IIntArray;

public class MachineSyncData implements IIntArray {
    private final MachineTileEntity machine;

    public MachineSyncData(MachineTileEntity crusher) {
        this.machine = crusher;
    }

    public int get(int index) {
        switch(index) {
            case 0:
                return machine.burnTime;
            case 1:
                return machine.workingTime;
            case 2:
                return machine.getWorkingTimeTotal();
            case 3:
                return machine.burnTimeTotal;
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
                machine.workingTime = value;
                break;
            case 3:
                machine.burnTimeTotal = value;
                break;
        }

    }

    public int size() {
        return 4;
    }
}