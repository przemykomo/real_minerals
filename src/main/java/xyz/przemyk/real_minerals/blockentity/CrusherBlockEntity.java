package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.datapack.recipes.CrusherRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.MachineItemStackHandler;

import javax.annotation.Nullable;

public class CrusherBlockEntity extends BasicMachineBlockEntity<CrusherRecipe> {

    @Override
    public int getWorkingTimeTotal() {
        return 100;
    }

    public CrusherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.CRUSHER_BLOCK_ENTITY_TYPE.get(), new MachineItemStackHandler(1), Recipes.CRUSHER_RECIPE_TYPE, blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new CrusherContainer(id, playerInventory, getBlockPos(), itemHandler, new BasicMachineSyncData(this), serverPlayer);
    }
}
