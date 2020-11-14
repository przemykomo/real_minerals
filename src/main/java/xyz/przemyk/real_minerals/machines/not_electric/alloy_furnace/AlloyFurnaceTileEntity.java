package xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.not_electric.MachineItemStackHandler;
import xyz.przemyk.real_minerals.machines.not_electric.MachineSyncData;
import xyz.przemyk.real_minerals.machines.not_electric.MachineTileEntity;

import javax.annotation.Nullable;

public class AlloyFurnaceTileEntity extends MachineTileEntity {

    @Override
    public int getWorkingTimeTotal() {
        return 200;
    }

    public AlloyFurnaceTileEntity() {
        super(Registering.ALLOY_FURNACE_TILE_ENTITY_TYPE.get(), new MachineItemStackHandler(5), RealMinerals.ALLOY_RECIPE_TYPE);
    }

    @Override
    public ITextComponent getDisplayName() {
        return AlloyFurnaceContainer.TITLE;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new AlloyFurnaceContainer(id, playerInventory, getPos(), itemHandler, new MachineSyncData(this), serverPlayer);
    }
}
