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
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.recipes.CrusherRecipe;

import javax.annotation.Nullable;

public class CrusherTileEntity extends MachineTileEntity<CrusherRecipe> {

    @Override
    public int getWorkingTimeTotal() {
        return 100;
    }

    public CrusherTileEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.CRUSHER_TILE_ENTITY_TYPE.get(), new MachineItemStackHandler(1), Recipes.CRUSHER_RECIPE_TYPE, blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return CrusherContainer.TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new CrusherContainer(id, playerInventory, getBlockPos(), itemHandler, new MachineSyncData(this), serverPlayer);
    }
}
