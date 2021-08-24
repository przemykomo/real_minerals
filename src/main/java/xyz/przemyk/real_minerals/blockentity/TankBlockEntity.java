package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TankBlockEntity extends BlockEntity {

    public static final int CAPACITY = 10_000;

    public final FluidTank fluidTank = new FluidTank(CAPACITY) {
        @Override
        protected void onContentsChanged() {
            markUpdated();
        }
    };
    public final LazyOptional<FluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    @SuppressWarnings("ConstantConditions")
    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public TankBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.TANK_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidTankLazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag.getCompound("fluid_tank"));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("fluid_tank", fluidTank.writeToNBT(new CompoundTag()));
        return super.save(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return fluidTank.writeToNBT(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        fluidTank.readFromNBT(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }
}
