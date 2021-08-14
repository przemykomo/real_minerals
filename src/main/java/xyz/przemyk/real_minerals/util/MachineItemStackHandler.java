package xyz.przemyk.real_minerals.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.datapack.recipes.ItemMachineRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MachineItemStackHandler extends ItemStackHandler {

    // this class assumes inputSize input slots, 1 fuel slot and 1 output slot
    public final int inputSize;

    private Runnable markDirty;

    public MachineItemStackHandler(int inputSize) {
        super(inputSize + 2);
        this.inputSize = inputSize;
    }

    public void setMarkDirty(Runnable markDirty) {
        this.markDirty = markDirty;
    }

    @Override
    protected void onContentsChanged(int slot) {
        markDirty.run();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (slot == inputSize + 1 && ForgeHooks.getBurnTime(stack, null) == 0) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }

    public ItemStack getFuelStack() {
        return getStackInSlot(inputSize);
    }

    public ItemStack getOutputStack() {
        return getStackInSlot(inputSize + 1);
    }

    public boolean areInputsEmpty() {
        for (int i = 0; i < inputSize; ++i) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean canProcess(@Nullable ItemMachineRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getResultItem();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = getStackInSlot(getSlots() - 1);
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

    public void processRecipe(ItemMachineRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem();
        ItemStack currentOutput = getOutputStack();

        if (currentOutput.isEmpty()) {
            setStackInSlot(getSlots() - 1, recipeOutput.copy());
        } else if (recipeOutput.sameItem(currentOutput)) {
            currentOutput.grow(recipeOutput.getCount());
        }

        getInputStacks().forEach(itemStack -> itemStack.shrink(1));
    }

    public NonNullList<ItemStack> getInputStacks() {
        NonNullList<ItemStack> itemStacks = NonNullList.create();

        for (int i = 0; i < inputSize; ++i) {
            ItemStack itemStack = getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                itemStacks.add(getStackInSlot(i));
            }
        }

        return itemStacks;
    }
}
