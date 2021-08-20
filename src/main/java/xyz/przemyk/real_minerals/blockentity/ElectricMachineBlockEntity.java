package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ElectricMachineBlockEntity<T extends Recipe<?>> extends BlockEntity implements TickableBlockEntity, MenuProvider {

    public final ElectricMachineEnergyStorage energyStorage;
    public int workingTime;

    private final int energyPerTick;
    private final int workingTimeTotal;

    protected final LazyOptional<ElectricMachineEnergyStorage> energyStorageLazyOptional;

    public ElectricMachineBlockEntity(BlockEntityType<?> tileEntityTypeIn, ElectricMachineEnergyStorage energyStorage, int energyPerTick, int workingTimeTotal, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        this.energyPerTick = energyPerTick;
        this.workingTimeTotal = workingTimeTotal;
        this.energyStorage = energyStorage;
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean wasWorking = workingTime > 0;
            if (energyStorage.getEnergyStored() >= energyPerTick) {
                T recipe = getCachedRecipe();
                if (canProcess(recipe)) {
                    if (!wasWorking) {
                        setChanged();
                        level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, true), 3);
                    }
                    ++workingTime;
                    energyStorage.removeEnergy(energyPerTick);
                    if (workingTime >= workingTimeTotal) {
                        workingTime = 0;
                        level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                        process(recipe);
                    }
                } else {
                    if (wasWorking) {
                        setChanged();
                        level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                    }
                    workingTime = 0;
                }
            } else {
                workingTime = 0;
                if (wasWorking) {
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                }
            }
        }
    }

    protected abstract void process(T recipe);

    protected abstract boolean canProcess(T recipe);

    protected abstract T getCachedRecipe();

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        workingTime = nbt.getInt("WorkingTime");
        energyStorage.setEnergy(nbt.getInt("energy"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putInt("WorkingTime", workingTime);
        compound.putInt("energy", energyStorage.getEnergyStored());
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    protected static class ElectricRecipeProcessingMachineSyncData implements ContainerData {
        private final ElectricMachineBlockEntity<?> machine;

        public ElectricRecipeProcessingMachineSyncData(ElectricMachineBlockEntity<?> machineBlockEntity) {
            machine = machineBlockEntity;
        }

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> machine.workingTime;
                case 1 -> machine.energyStorage.getEnergyStored();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {}

        @Override
        public int getCount() {
            return 2;
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }
}
