package xyz.przemyk.real_minerals.machines.electric.furnace;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricRecipeProcessingTileEntity;

import javax.annotation.Nullable;

public class ElectricFurnaceTileEntity extends ElectricRecipeProcessingTileEntity<FurnaceRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 80;

    public ElectricFurnaceTileEntity() {
        super(Registering.ELECTRIC_FURNACE_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0), FE_PER_TICK, 2, WORKING_TIME_TOTAL);
    }

    private FurnaceRecipe cachedRecipe = null;

    @SuppressWarnings("ConstantConditions")
    protected FurnaceRecipe getCachedRecipe() {
        if (cachedRecipe != null && cachedRecipe.matches(new Inventory(itemHandler.getStackInSlot(0)), world)) {
            return cachedRecipe;
        }

        cachedRecipe = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(itemHandler.getStackInSlot(0)), world).orElse(null);
        return cachedRecipe;
    }

    protected boolean canProcess(@Nullable FurnaceRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getRecipeOutput();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            if (currentOutput.isEmpty()) {
                return true;
            }
            if (!currentOutput.isItemEqual(outputStack)) {
                return false;
            }
            return currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    @Override
    protected void process(FurnaceRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack outputStack = itemHandler.getStackInSlot(1);
        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(1, recipeOutput.copy());
        } else {
            outputStack.grow(recipeOutput.getCount());
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return ElectricFurnaceContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new ElectricFurnaceContainer(id, playerInventory, getPos(), itemHandler, new RecipeProcessingMachineSyncData(this), serverPlayer);
    }
}
