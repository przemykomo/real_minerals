package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.util.MachineItemStackHandler;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.recipes.AlloyRecipe;

import javax.annotation.Nullable;

public class AlloyFurnaceTileEntity extends MachineTileEntity<AlloyRecipe> {

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
