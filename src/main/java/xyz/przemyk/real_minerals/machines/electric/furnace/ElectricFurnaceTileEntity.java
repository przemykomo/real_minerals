package xyz.przemyk.real_minerals.machines.electric.furnace;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectricFurnaceTileEntity extends ElectricMachineTileEntity {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 80;

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };

    public int workingTime;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public ElectricFurnaceTileEntity() {
        super(Registering.ELECTRIC_FURNACE_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 20));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() { //TODO: change block state and mark dirty
        super.tick();
        if (!world.isRemote()) {
            boolean dirty = false;
            boolean wasWorking = workingTime > 0;
            if (energyStorage.getEnergyStored() >= FE_PER_TICK) {
                FurnaceRecipe recipe = getCachedRecipe();
                if (canSmelt(recipe)) {
                    if (!wasWorking) {
                        dirty = true;
                    }
                    ++workingTime;
                    energyStorage.extractEnergy(FE_PER_TICK, false);
                    if (workingTime >= WORKING_TIME_TOTAL) {
                        workingTime = 0;
                        itemHandler.getStackInSlot(0).shrink(1);
                        ItemStack outputStack = itemHandler.getStackInSlot(1);
                        ItemStack recipeOutput = recipe.getRecipeOutput();
                        if (outputStack.isEmpty()) {
                            itemHandler.setStackInSlot(1, recipeOutput.copy());
                        } else {
                            outputStack.grow(recipeOutput.getCount());
                        }
                    }
                } else {
                    if (wasWorking) {
                        dirty = true;
                    }
                    workingTime = 0;
                }
            } else {
                workingTime = 0;
            }

            if (dirty) {
                markDirty();
            }
        }
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

    private boolean canSmelt(@Nullable FurnaceRecipe recipe) {
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
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        workingTime = nbt.getInt("WorkingTime");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("WorkingTime", workingTime);
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return ElectricFurnaceContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new ElectricFurnaceContainer(id, playerInventory, getPos(), itemHandler, new ElectricFurnaceSyncData(this), serverPlayer);
    }

    private static class ElectricFurnaceSyncData implements IIntArray {
        private final ElectricFurnaceTileEntity machine;
        public ElectricFurnaceSyncData(ElectricFurnaceTileEntity electricFurnaceTileEntity) {
            machine = electricFurnaceTileEntity;
        }

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return machine.workingTime;
                case 1:
                    return machine.energyStorage.getEnergyStored();
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    machine.workingTime = value;
                    break;
                case 1:
                    machine.energyStorage.setEnergy(value);
            }
        }

        @Override
        public int size() {
            return 2;
        }
    }
}
