package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nullable;

public class CrafterBlockEntity extends ElectricMachineBlockEntity<CraftingRecipe> {

    private final ItemStackHandler craftingGridItemHandler = new ItemStackHandler(9);
    private final DummyCraftingContainer dummyCraftingContainer = new DummyCraftingContainer(craftingGridItemHandler);
    private final ItemStackHandler inputItemHandler = new ItemStackHandler(9);
    private final ItemStackHandler outputItemHandler = new ItemStackHandler();

    public CrafterBlockEntity(BlockEntityType<?> tileEntityTypeIn, ElectricMachineEnergyStorage energyStorage, int energyPerTick, int workingTimeTotal, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, energyStorage, energyPerTick, workingTimeTotal, blockPos, blockState);
    }

    @Override
    protected void process(CraftingRecipe recipe) {
        outputItemHandler.insertItem(0, recipe.getResultItem().copy(), false);
    }

    @Override
    protected boolean canProcess(CraftingRecipe recipe) {
        if (recipe != null) {
            //todo: use Recipe#assemble
            ItemStack outputStack = recipe.getResultItem();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = outputItemHandler.getStackInSlot(0);
            if (currentOutput.isEmpty() || (currentOutput.sameItem(outputStack) && currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize())) {
                StackedContents stackedContents = new StackedContents();
                for (int i = 0; i < inputItemHandler.getSlots(); i++) {
                    stackedContents.accountSimpleStack(inputItemHandler.getStackInSlot(i));
                }

                for (int i = 0; i < craftingGridItemHandler.getSlots(); i++) {
                    stackedContents.accountSimpleStack(craftingGridItemHandler.getStackInSlot(i));
                }

                return stackedContents.canCraft(recipe, null);
            }
        }
        return false;
    }

    private CraftingRecipe cachedRecipe = null;

    @Override
    protected CraftingRecipe getCachedRecipe() {
        if (cachedRecipe != null && cachedRecipe.matches(dummyCraftingContainer, level)) {
            return cachedRecipe;
        }

        return cachedRecipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, dummyCraftingContainer, level).orElse(null);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return null;
    }

    private static class DummyCraftingContainer extends CraftingContainer {

        private final ItemStackHandler itemStackHandler;

        public DummyCraftingContainer(ItemStackHandler itemStackHandler) {
            super(null, 3, 3);
            this.itemStackHandler = itemStackHandler;
        }

        @Override
        public int getContainerSize() {
            return itemStackHandler.getSlots();
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public ItemStack getItem(int pIndex) {
            return itemStackHandler.getStackInSlot(pIndex);
        }

        @Override
        public ItemStack removeItemNoUpdate(int pIndex) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(pIndex).copy();
            itemStackHandler.setStackInSlot(pIndex, ItemStack.EMPTY);
            return itemStack;
        }

        @Override
        public ItemStack removeItem(int pIndex, int pCount) {
            return itemStackHandler.extractItem(pIndex, pCount, false);
        }

        @Override
        public void setItem(int pIndex, ItemStack pStack) {
            itemStackHandler.setStackInSlot(pIndex, pStack);
        }

        @Override
        public void clearContent() {
            for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        @Override
        public void fillStackedContents(StackedContents pHelper) {
            for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                pHelper.accountSimpleStack(itemStackHandler.getStackInSlot(i));
            }
        }
    }
}
