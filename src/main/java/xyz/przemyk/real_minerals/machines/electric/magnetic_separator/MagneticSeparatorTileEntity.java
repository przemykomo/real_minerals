package xyz.przemyk.real_minerals.machines.electric.magnetic_separator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.RecipeProcessingTileEntity;

public class MagneticSeparatorTileEntity extends RecipeProcessingTileEntity<MagneticSeparatorRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public MagneticSeparatorTileEntity() {
        super(Registering.MAGNETIC_SEPARATOR_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, FE_PER_TICK), FE_PER_TICK, 3, WORKING_TIME_TOTAL);
    }

    private MagneticSeparatorRecipe cachedRecipe = null;

    @Override
    public MagneticSeparatorRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = RealMinerals.getRecipe(input, world, RealMinerals.MAGNETIC_SEPARATOR_RECIPE_TYPE);
        return cachedRecipe;
    }

    @Override
    protected boolean canProcess(MagneticSeparatorRecipe recipe) {
        if (recipe != null) {
            NonNullList<ItemStack> recipeOutputs = recipe.getRecipeOutputs();
            ItemStack outputStack = recipeOutputs.get(0);
            ItemStack secondaryOutputStack = recipeOutputs.get(1);
            if (outputStack.isEmpty()) {
                return false; // if there is only one output then it needs to be primary
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            ItemStack currentSecondaryOutput = itemHandler.getStackInSlot(2);
            if (currentOutput.isEmpty() && (currentSecondaryOutput.isEmpty() || secondaryOutputStack.isEmpty())) {
                return true;
            }

            return (currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize()) && (currentSecondaryOutput.getMaxStackSize() + secondaryOutputStack.getMaxStackSize() <= currentSecondaryOutput.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void process(MagneticSeparatorRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack outputStack = itemHandler.getStackInSlot(1);
        ItemStack secondaryOutputStack = itemHandler.getStackInSlot(2);

        NonNullList<ItemStack> recipeOutputs = recipe.getRecipeOutputs();
        ItemStack recipeOutput = recipeOutputs.get(0);
        ItemStack secondaryRecipeOutput = recipeOutputs.get(1);

        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(1, recipeOutput.copy());
        } else {
            outputStack.grow(recipeOutput.getCount());
        }

        if (!secondaryRecipeOutput.isEmpty()) {
            if (secondaryOutputStack.isEmpty()) {
                itemHandler.setStackInSlot(2, secondaryRecipeOutput.copy());
            } else {
                secondaryOutputStack.grow(secondaryRecipeOutput.getCount());
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return MagneticSeparatorContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new MagneticSeparatorContainer(id, playerInventory, getPos(), itemHandler, new RecipeProcessingMachineSyncData(this), serverPlayer);
    }
}
