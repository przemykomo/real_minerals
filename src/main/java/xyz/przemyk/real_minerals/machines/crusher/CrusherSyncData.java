package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.util.IIntArray;

public class CrusherSyncData implements IIntArray {
    private final CrusherTileEntity crusher;

    public CrusherSyncData(CrusherTileEntity crusher) {
        this.crusher = crusher;
    }

    public int get(int index) {
        switch(index) {
            case 0:
                return crusher.burnTime;
            case 1:
                return crusher.crushTime;
            default:
                return 0;
        }
    }

    public void set(int index, int value) {
        switch(index) {
            case 0:
                crusher.burnTime = value;
                break;
            case 1:
                crusher.crushTime = value;
                break;
        }

    }

    public int size() {
        return 2;
    }
}
