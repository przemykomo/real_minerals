package xyz.przemyk.real_minerals.machines;

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
            // you should never try to set workingTimeTotal
        }

    }

    public int size() {
        return 3;
    }
}