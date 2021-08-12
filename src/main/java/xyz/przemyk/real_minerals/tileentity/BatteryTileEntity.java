package xyz.przemyk.real_minerals.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.containers.BatteryContainer;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.util.BatteryEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BatteryTileEntity extends BlockEntity implements TickableBlockEntity, MenuProvider {

    public final BatteryEnergyStorage energyStorage;

    public final LazyOptional<BatteryEnergyStorage.Input> inputEnergyStorageLazyOptional;
    public final LazyOptional<BatteryEnergyStorage.Output> outputEnergyStorageLazyOptional;

    public BatteryTileEntity(BlockPos blockPos, BlockState blockState) {
        super(Registering.BATTERY_TILE_ENTITY_TYPE.get(), blockPos, blockState);
        this.energyStorage = new BatteryEnergyStorage(1_000_000, 1_000, this);
        this.inputEnergyStorageLazyOptional = LazyOptional.of(() -> energyStorage.input);
        this.outputEnergyStorageLazyOptional = LazyOptional.of(() -> energyStorage.output);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        energyStorage.trySendTo(level, worldPosition, getBlockState().getValue(HorizontalDirectionalBlock.FACING).getCounterClockWise());
    }

    @SuppressWarnings("ConstantConditions")
    public void updateBlockState() {
        int charge = energyStorage.getEnergyStored() * 4 / energyStorage.getMaxEnergyStored();
        BlockState blockState = getBlockState();
        if (charge != blockState.getValue(BlockStateProperties.RESPAWN_ANCHOR_CHARGES)) {
            level.setBlockAndUpdate(worldPosition, blockState.setValue(BlockStateProperties.RESPAWN_ANCHOR_CHARGES, charge));
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputEnergyStorageLazyOptional.invalidate();
        outputEnergyStorageLazyOptional.invalidate();
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
            Direction facing = getBlockState().getValue(HorizontalDirectionalBlock.FACING);

            if (side == facing.getClockWise()) {
                return inputEnergyStorageLazyOptional.cast();
            }

            if (side == facing.getCounterClockWise()) {
                return outputEnergyStorageLazyOptional.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return BatteryContainer.TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new BatteryContainer(id, playerInventory, getBlockPos(), new BatterySyncData(this), serverPlayer);
    }

    private static class BatterySyncData implements ContainerData {
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
        public int getCount() {
            return 1;
        }
    }
}
