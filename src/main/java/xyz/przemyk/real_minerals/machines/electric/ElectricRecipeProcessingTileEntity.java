package xyz.przemyk.real_minerals.machines.electric;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ElectricRecipeProcessingTileEntity<T extends IRecipe<?>> extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public final ItemStackHandler itemHandler;
    public final ElectricMachineEnergyStorage energyStorage;
    public int workingTime;

    private final int energyPerTick;
    private final int workingTimeTotal;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional;
    protected final LazyOptional<ElectricMachineEnergyStorage> energyStorageLazyOptional;

    public ElectricRecipeProcessingTileEntity(TileEntityType<?> tileEntityTypeIn, ElectricMachineEnergyStorage energyStorage, int energyPerTick, int itemHandlerSize, int workingTimeTotal) {
        super(tileEntityTypeIn);
        this.energyPerTick = energyPerTick;
        this.itemHandler = new ItemStackHandler(itemHandlerSize) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
        this.workingTimeTotal = workingTimeTotal;
        this.itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);
        this.energyStorage = energyStorage;
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if (!world.isRemote()) {
            boolean dirty = false;
            boolean wasWorking = workingTime > 0;
            if (energyStorage.getEnergyStored() >= energyPerTick) {
                T recipe = getCachedRecipe();
                if (canProcess(recipe)) {
                    if (!wasWorking) {
                        dirty = true;
                        world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, true), 3);
                    }
                    ++workingTime;
                    energyStorage.removeEnergy(energyPerTick);
                    if (workingTime >= workingTimeTotal) {
                        workingTime = 0;
                        world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, false), 3);
                        process(recipe);
                    }
                } else {
                    if (wasWorking) {
                        dirty = true;
                        world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, false), 3);
                    }
                    workingTime = 0;
                }
            } else {
                workingTime = 0;
                if (wasWorking) {
                    world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, false), 3);
                }
            }

            if (dirty) {
                markDirty();
            }
        }
    }

    protected abstract void process(T recipe);

    protected abstract boolean canProcess(T recipe);

    protected abstract T getCachedRecipe();

    @Override
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
        energyStorageLazyOptional.invalidate();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        workingTime = nbt.getInt("WorkingTime");
        energyStorage.setEnergy(nbt.getInt("energy"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("WorkingTime", workingTime);
        compound.putInt("energy", energyStorage.getEnergyStored());
        return super.write(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    protected static class RecipeProcessingMachineSyncData implements IIntArray {
        private final ElectricRecipeProcessingTileEntity<?> machine;

        public RecipeProcessingMachineSyncData(ElectricRecipeProcessingTileEntity<?> electricFurnaceTileEntity) {
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
