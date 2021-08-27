package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.datapack.recipes.AlloyRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloyFurnaceBlockEntity extends ElectricMachineBlockEntity<AlloyRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public AlloyFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.ALLOY_FURNACE_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    @Override
    protected void process(AlloyRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem();
        ItemStack currentOutput = itemHandler.getStackInSlot(5);

        if (currentOutput.isEmpty()) {
            itemHandler.setStackInSlot(5, recipeOutput.copy());
        } else if (recipeOutput.sameItem(currentOutput)) {
            currentOutput.grow(recipeOutput.getCount());
        }

        for (int i = 0; i < 5; ++i) {
            itemHandler.getStackInSlot(i).shrink(1);
        }
    }

    @Override
    protected boolean canProcess(AlloyRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getResultItem();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(5);
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

    private AlloyRecipe cachedRecipe = null;

    @Override
    protected AlloyRecipe getCachedRecipe() {
        NonNullList<ItemStack> itemStacks = NonNullList.create();

        for (int i = 0; i < 5; ++i) {
            ItemStack itemStack = itemHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                itemStacks.add(itemStack);
            }
        }

        if (cachedRecipe != null && cachedRecipe.isValidInput(itemStacks)) {
            return cachedRecipe;
        }

        return cachedRecipe = Recipes.getRecipe(itemStacks, level, Recipes.ALLOY_RECIPE_TYPE);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new AlloyFurnaceContainer(id, playerInventory, getBlockPos(), itemHandler, new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
