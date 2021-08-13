package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.MachineItemStackHandler;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.recipes.AlloyRecipe;

import javax.annotation.Nullable;

public class AlloyFurnaceTileEntity extends MachineTileEntity<AlloyRecipe> {

    @Override
    public int getWorkingTimeTotal() {
        return 200;
    }

    public AlloyFurnaceTileEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.ALLOY_FURNACE_TILE_ENTITY_TYPE.get(), new MachineItemStackHandler(5), Recipes.ALLOY_RECIPE_TYPE, blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return AlloyFurnaceContainer.TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new AlloyFurnaceContainer(id, playerInventory, getBlockPos(), itemHandler, new MachineSyncData(this), serverPlayer);
    }
}
