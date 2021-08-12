package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.containers.ElectricFurnaceContainer;

import javax.annotation.Nullable;

import xyz.przemyk.real_minerals.tileentity.ElectricRecipeProcessingTileEntity.RecipeProcessingMachineSyncData;

public class ElectricFurnaceTileEntity extends ElectricRecipeProcessingTileEntity<SmeltingRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 80;

    public ElectricFurnaceTileEntity(BlockPos blockPos, BlockState blockState) {
        super(Registering.ELECTRIC_FURNACE_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, 2, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    private SmeltingRecipe cachedRecipe = null;

    @SuppressWarnings("ConstantConditions")
    protected SmeltingRecipe getCachedRecipe() {
        if (cachedRecipe != null && cachedRecipe.matches(new SimpleContainer(itemHandler.getStackInSlot(0)), level)) {
            return cachedRecipe;
        }

        cachedRecipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemHandler.getStackInSlot(0)), level).orElse(null);
        return cachedRecipe;
    }

    protected boolean canProcess(@Nullable SmeltingRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getResultItem();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            if (currentOutput.isEmpty()) {
                return true;
            }
            if (!currentOutput.sameItem(outputStack)) {
                return false;
            }
            return currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    @Override
    protected void process(SmeltingRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack outputStack = itemHandler.getStackInSlot(1);
        ItemStack recipeOutput = recipe.getResultItem();
        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(1, recipeOutput.copy());
        } else {
            outputStack.grow(recipeOutput.getCount());
        }
    }

    @Override
    public Component getDisplayName() {
        return ElectricFurnaceContainer.TITLE;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new ElectricFurnaceContainer(id, playerInventory, getBlockPos(), itemHandler, new RecipeProcessingMachineSyncData(this), serverPlayer);
    }
}
