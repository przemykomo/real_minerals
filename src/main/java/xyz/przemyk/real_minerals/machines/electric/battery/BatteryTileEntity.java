package xyz.przemyk.real_minerals.machines.electric.battery;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineTileEntity;

import javax.annotation.Nullable;

public class BatteryTileEntity extends ElectricMachineTileEntity {
    public BatteryTileEntity() {
        super(Registering.BATTERY_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(1_000_000, 1_000));
    }

    @Override
    public ITextComponent getDisplayName() {
        return BatteryContainer.TITLE;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new BatteryContainer(id, playerInventory, getPos(), new BatterySyncData(this), serverPlayer);
    }

    private static class BatterySyncData implements IIntArray {
        private final BatteryTileEntity battery;
        public BatterySyncData(BatteryTileEntity batteryTileEntity) {
            battery = batteryTileEntity;
        }

        @Override
        public int get(int index) {
            if (index == 0) {
                return battery.energyStorage.getEnergyStored();
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                battery.energyStorage.setEnergy(value);
            }
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
