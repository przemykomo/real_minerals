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
import xyz.przemyk.real_minerals.containers.MagnetizerContainer;
import xyz.przemyk.real_minerals.datapack.recipes.MagnetizerRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagnetizerBlockEntity extends ElectricMachineBlockEntity<MagnetizerRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public MagnetizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.MAGNETIZER_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    private MagnetizerRecipe cachedRecipe = null;

    @Override
    protected MagnetizerRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = Recipes.getRecipe(input, level, Recipes.MAGNETIZER_RECIPE_TYPE);
        return cachedRecipe;
    }

    @Override
    protected boolean canProcess(MagnetizerRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getResultItem();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            if (currentOutput.isEmpty()) {
                return true;
            }
            return currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    @Override
    protected void process(MagnetizerRecipe recipe) {
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
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new MagnetizerContainer(id, playerInventory, getBlockPos(), itemHandler, new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
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
