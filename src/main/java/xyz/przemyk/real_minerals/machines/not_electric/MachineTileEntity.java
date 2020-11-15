package xyz.przemyk.real_minerals.machines.not_electric;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MachineTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public final MachineItemStackHandler itemHandler;
    public int burnTime;
    public int workingTime;
    public int burnTimeTotal;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    private final IRecipeType<?> recipeType;

    public MachineTileEntity(TileEntityType<?> tileEntityTypeIn, MachineItemStackHandler itemHandler, IRecipeType<?> recipeType) {
        super(tileEntityTypeIn);
        this.itemHandler = itemHandler;
        itemHandler.setMarkDirty(this::markDirty);
        this.itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);
        this.recipeType = recipeType;
    }

    @Override
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
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
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        burnTime = nbt.getInt("BurnTime");
        workingTime = nbt.getInt("WorkingTime");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("WorkingTime", workingTime);
        return super.write(compound);
    }

    private MachineRecipe cachedRecipe = null;

    @Nullable
    protected MachineRecipe getCachedRecipe(NonNullList<ItemStack> input) {
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = RealMinerals.getRecipe(input, world, recipeType);
        return cachedRecipe;
    }

    protected boolean isBurning() {
        return this.burnTime > 0;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        boolean dirty = false;
        boolean wasBurning = isBurning();
        if (wasBurning) {
            --burnTime;
        }

        if (!world.isRemote()) {
            ItemStack fuelStack = itemHandler.getFuelStack();
            NonNullList<ItemStack> inputStacks = itemHandler.getInputStacks();
            boolean areInputsEmpty = itemHandler.areInputsEmpty();
            if ((isBurning() || !fuelStack.isEmpty()) && !areInputsEmpty) {
                MachineRecipe recipe = getCachedRecipe(inputStacks);
                if (itemHandler.canProcess(recipe)) {
                    if (!isBurning()) {
                        burnTime = ForgeHooks.getBurnTime(fuelStack);
                        burnTimeTotal = burnTime;
                        if (isBurning()) {
                            dirty = true;
                            if (fuelStack.hasContainerItem()) {
                                itemHandler.setStackInSlot(1, fuelStack.getContainerItem());
                            } else {
                                fuelStack.shrink(1);
                            }
                        }
                    } else {
                        ++workingTime;
                        if (workingTime >= getWorkingTimeTotal()) {
                            workingTime = 0;
                            itemHandler.processRecipe(recipe);
                            dirty = true;
                        }
                    }
                } else {
                    workingTime = 0;
                }
            } else if (!isBurning() && workingTime > 0) {
                workingTime = MathHelper.clamp(workingTime - 2, 0, getWorkingTimeTotal());
            }

            if (areInputsEmpty) {
                workingTime = 0;
            }

            if (wasBurning != isBurning()) {
                dirty = true;
                world.setBlockState(this.pos, world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (dirty) {
            markDirty();
        }
    }

    protected static class MachineSyncData implements IIntArray {
        private final MachineTileEntity machine;

        public MachineSyncData(MachineTileEntity crusher) {
            this.machine = crusher;
        }

        public int get(int index) {
            switch (index) {
                case 0:
                    return machine.burnTime;
                case 1:
                    return machine.workingTime;
                case 2:
                    return machine.getWorkingTimeTotal();
                case 3:
                    return machine.burnTimeTotal;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    machine.burnTime = value;
                    break;
                case 1:
                    machine.workingTime = value;
                    break;
                case 3:
                    machine.burnTimeTotal = value;
                    break;
            }

        }

        public int size() {
            return 4;
        }
    }

    public abstract int getWorkingTimeTotal();
}
