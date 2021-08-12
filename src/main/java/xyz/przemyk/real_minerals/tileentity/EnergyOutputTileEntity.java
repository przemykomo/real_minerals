package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EnergyOutputTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public final ElectricMachineEnergyStorage energyStorage;
    protected final LazyOptional<ElectricMachineEnergyStorage> energyStorageLazyOptional;

    public EnergyOutputTileEntity(TileEntityType<?> tileEntityTypeIn, ElectricMachineEnergyStorage energyStorage) {
        super(tileEntityTypeIn);
        this.energyStorage = energyStorage;
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void remove() {
        super.remove();
        energyStorageLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if (!world.isRemote()) {
            energyStorage.trySendToNeighbors(world, pos);
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        energyStorage.setEnergy(nbt.getInt("energy"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("energy", energyStorage.getEnergyStored());
        return super.write(compound);
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
