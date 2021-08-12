package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.containers.BatteryContainer;
import xyz.przemyk.real_minerals.BatteryEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BatteryTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    public final BatteryEnergyStorage energyStorage;

    public final LazyOptional<BatteryEnergyStorage.Input> inputEnergyStorageLazyOptional;
    public final LazyOptional<BatteryEnergyStorage.Output> outputEnergyStorageLazyOptional;

    public BatteryTileEntity() {
        super(Registering.BATTERY_TILE_ENTITY_TYPE.get());
        this.energyStorage = new BatteryEnergyStorage(1_000_000, 1_000, this);
        this.inputEnergyStorageLazyOptional = LazyOptional.of(() -> energyStorage.input);
        this.outputEnergyStorageLazyOptional = LazyOptional.of(() -> energyStorage.output);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        energyStorage.trySendTo(world, pos, getBlockState().get(HorizontalBlock.HORIZONTAL_FACING).rotateYCCW());
    }

    @SuppressWarnings("ConstantConditions")
    public void updateBlockState() {
        int charge = energyStorage.getEnergyStored() * 4 / energyStorage.getMaxEnergyStored();
        BlockState blockState = getBlockState();
        if (charge != blockState.get(BlockStateProperties.CHARGES)) {
            world.setBlockState(pos, blockState.with(BlockStateProperties.CHARGES, charge));
        }
    }

    @Override
    public void remove() {
        super.remove();
        inputEnergyStorageLazyOptional.invalidate();
        outputEnergyStorageLazyOptional.invalidate();
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
            Direction facing = getBlockState().get(HorizontalBlock.HORIZONTAL_FACING);

            if (side == facing.rotateY()) {
                return inputEnergyStorageLazyOptional.cast();
            }

            if (side == facing.rotateYCCW()) {
                return outputEnergyStorageLazyOptional.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return BatteryContainer.TITLE;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new BatteryContainer(id, playerInventory, getPos(), new BatterySyncData(this), serverPlayer);
    }

    private static class BatterySyncData implements IIntArray {
        private final BatteryTileEntity battery;
        public BatterySyncData(BatteryTileEntity batteryTileEntity) {
            battery = batteryTileEntity;
        }

        @Override
        public int get(int index) {
            if (index == 0) {
                return battery.energyStorage.getEnergyStored();
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                battery.energyStorage.setEnergy(value);
            }
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
