package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EnergyOutputBlockEntity extends BlockEntity implements TickableBlockEntity, MenuProvider {

    public final ElectricMachineEnergyStorage energyStorage;
    protected final LazyOptional<ElectricMachineEnergyStorage> energyStorageLazyOptional;

    public EnergyOutputBlockEntity(BlockEntityType<?> tileEntityTypeIn, ElectricMachineEnergyStorage energyStorage, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        this.energyStorage = energyStorage;
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energyStorageLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if (!level.isClientSide()) {
            energyStorage.trySendToNeighbors(level, worldPosition);
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        energyStorage.setEnergy(nbt.getInt("energy"));
        super.load(nbt);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putInt("energy", energyStorage.getEnergyStored());
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
