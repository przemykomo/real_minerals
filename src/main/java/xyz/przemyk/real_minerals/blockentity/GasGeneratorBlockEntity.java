package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.datapack.GasBurningEntry;
import xyz.przemyk.real_minerals.datapack.GasBurningReloadListener;
import xyz.przemyk.real_minerals.fluid.FillOnlyFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasGeneratorBlockEntity extends EnergyOutputBlockEntity {

    public final FillOnlyFluidTank fluidTank = new FillOnlyFluidTank(FluidAttributes.BUCKET_VOLUME) {
        @Override
        protected void onContentsChanged() {
            markUpdated();
        }
    };

    @SuppressWarnings("ConstantConditions")
    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public int burnTime;
    public int burnTimeTotal;
    public int energyPerTick;

    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);

    public GasGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.GAS_GENERATOR_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 0, 160), blockPos, blockState);
        fluidTank.setValidator(fluidStack -> GasBurningReloadListener.gasBurningEntries.containsKey(fluidStack.getFluid()));
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandlerLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (isBurning()) {
                --burnTime;
                setChanged();
                energyStorage.addEnergy(energyPerTick);
                if (!isBurning()) {
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                }
            } else if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored() && !fluidTank.isEmpty()) {
                GasBurningEntry gasBurningEntry = GasBurningReloadListener.gasBurningEntries.get(fluidTank.getFluid().getFluid());
                if (gasBurningEntry != null) {
                    if (fluidTank.getFluidAmount() >= gasBurningEntry.fluidStack().getAmount()) {
                        fluidTank.drainInternal(gasBurningEntry.fluidStack().getAmount());
                        energyPerTick = gasBurningEntry.energyPerTick();
                        burnTime = burnTimeTotal = gasBurningEntry.burnTime();
                        setChanged();
                        level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, true), 3);
                    }
                }
            }
        }
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt);
        burnTime = nbt.getInt("BurnTime");
        burnTimeTotal = nbt.getInt("BurnTimeTotal");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound = super.save(compound);
        fluidTank.writeToNBT(compound);
        compound.putInt("BurnTime", burnTime);
        compound.putInt("BurnTimeTotal", burnTimeTotal);
        return compound;
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return GasGeneratorContainer.TITLE;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new GasGeneratorContainer(id, playerInventory, getBlockPos(), new GeneratorSyncData(this), serverPlayer);
    }

    private record GeneratorSyncData(GasGeneratorBlockEntity machine) implements ContainerData {

        public int get(int index) {
            return switch (index) {
                case 0 -> machine.burnTime;
                case 1 -> machine.burnTimeTotal;
                case 2 -> machine.energyStorage.getEnergyStored();
                default -> 0;
            };
        }

        public void set(int index, int value) {}

        public int getCount() {
            return 3;
        }
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
