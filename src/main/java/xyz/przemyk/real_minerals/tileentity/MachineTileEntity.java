package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.recipes.MachineRecipe;
import xyz.przemyk.real_minerals.util.MachineItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MachineTileEntity<T extends MachineRecipe> extends BlockEntity implements TickableBlockEntity, MenuProvider {

    public final MachineItemStackHandler itemHandler;
    public int burnTime;
    public int workingTime;
    public int burnTimeTotal;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    private final RecipeType<T> recipeType;

    public MachineTileEntity(BlockEntityType<?> tileEntityTypeIn, MachineItemStackHandler itemHandler, RecipeType<T> recipeType, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        this.itemHandler = itemHandler;
        itemHandler.setMarkDirty(this::setChanged);
        this.itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);
        this.recipeType = recipeType;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerLazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        burnTime = nbt.getInt("BurnTime");
        workingTime = nbt.getInt("WorkingTime");
        super.load(nbt);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("WorkingTime", workingTime);
        return super.save(compound);
    }

    private MachineRecipe cachedRecipe = null;

    @Nullable
    protected MachineRecipe getCachedRecipe(NonNullList<ItemStack> input) {
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = RealMinerals.getRecipe(input, level, recipeType);
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

        if (!level.isClientSide()) {
            ItemStack fuelStack = itemHandler.getFuelStack();
            NonNullList<ItemStack> inputStacks = itemHandler.getInputStacks();
            boolean areInputsEmpty = itemHandler.areInputsEmpty();
            if ((isBurning() || !fuelStack.isEmpty()) && !areInputsEmpty) {
                MachineRecipe recipe = getCachedRecipe(inputStacks);
                if (itemHandler.canProcess(recipe)) {
                    if (!isBurning()) {
                        burnTime = ForgeHooks.getBurnTime(fuelStack, null);
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
                workingTime = Mth.clamp(workingTime - 2, 0, getWorkingTimeTotal());
            }

            if (areInputsEmpty) {
                workingTime = 0;
            }

            if (wasBurning != isBurning()) {
                dirty = true;
                level.setBlock(this.worldPosition, level.getBlockState(this.worldPosition).setValue(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }

        if (dirty) {
            setChanged();
        }
    }

    protected static class MachineSyncData implements ContainerData {
        private final MachineTileEntity<?> machine;

        public MachineSyncData(MachineTileEntity<?> crusher) {
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

        public int getCount() {
            return 4;
        }
    }

    public abstract int getWorkingTimeTotal();
}
