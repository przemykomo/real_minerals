package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.datapack.recipes.CrusherRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CrusherBlockEntity extends ElectricMachineBlockEntity<CrusherRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 80;

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public CrusherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.CRUSHER_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    private CrusherRecipe cachedRecipe = null;

    @Override
    public CrusherRecipe getCachedRecipe() {
        if (cachedRecipe != null && testRecipe(cachedRecipe)) {
            return cachedRecipe;
        }

        return cachedRecipe = level.getRecipeManager().getAllRecipesFor(Recipes.CRUSHER_RECIPE_TYPE).stream().filter(this::testRecipe).findFirst().orElse(null);
    }

    private boolean testRecipe(CrusherRecipe crusherRecipe) {
        return crusherRecipe.input().test(itemHandler.getStackInSlot(0));
    }

    @Override
    protected boolean canProcess(CrusherRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.output();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            if (currentOutput.isEmpty()) {
                return true;
            }
            return currentOutput.is(outputStack.getItem()) && currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    @Override
    protected void process(CrusherRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack outputStack = itemHandler.getStackInSlot(1);
        ItemStack recipeOutput = recipe.output();
        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(1, recipeOutput.copy());
        } else {
            outputStack.grow(recipeOutput.getCount());
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new CrusherContainer(id, playerInventory, getBlockPos(), itemHandler, new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
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
