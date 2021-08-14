package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.containers.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.recipes.MagneticSeparatorRecipe;

public class MagneticSeparatorBlockEntity extends ElectricMachineBlockEntity<MagneticSeparatorRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public MagneticSeparatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.MAGNETIC_SEPARATOR_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, 3, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    private MagneticSeparatorRecipe cachedRecipe = null;

    @Override
    public MagneticSeparatorRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = Recipes.getRecipe(input, level, Recipes.MAGNETIC_SEPARATOR_RECIPE_TYPE);
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

            if (!currentOutput.sameItem(outputStack)) {
                return false;
            }

            if (!currentSecondaryOutput.isEmpty() && !currentSecondaryOutput.sameItem(secondaryOutputStack)) {
                return false;
            }

            return (currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize()) && (currentSecondaryOutput.getCount() + secondaryOutputStack.getCount() <= currentSecondaryOutput.getMaxStackSize());
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
    public Component getDisplayName() {
        return MagneticSeparatorContainer.TITLE;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new MagneticSeparatorContainer(id, playerInventory, getBlockPos(), itemHandler, new RecipeProcessingMachineSyncData(this), serverPlayer);
    }
}
