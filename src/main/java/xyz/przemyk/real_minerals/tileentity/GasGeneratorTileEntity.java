package xyz.przemyk.real_minerals.tileentity;

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
import net.minecraft.world.level.ChunkPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.datagen.providers.FluidTags;
import xyz.przemyk.real_minerals.fluid.FillOnlyFluidTank;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasGeneratorTileEntity extends EnergyOutputTileEntity {

    public static final int TANK_VOLUME = FluidAttributes.BUCKET_VOLUME;

    public final FillOnlyFluidTank fluidTank = new FillOnlyFluidTank(TANK_VOLUME) {
        @Override
        protected void onContentsChanged() {
            syncToClient();
        }
    };

    public int burnTime;
    public int burnTimeTotal;
    public int energyPerTick;

    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);

    public GasGeneratorTileEntity(BlockPos blockPos, BlockState blockState) {
        super(Registering.GAS_GENERATOR_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 0, 160), blockPos, blockState);
        fluidTank.setValidator(fluidStack -> FluidTags.BURNABLE_GAS.contains(fluidStack.getFluid()));
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
            boolean dirty = false;
            if (isBurning()) {
                --burnTime;
                dirty = true;
                energyStorage.addEnergy(energyPerTick);
                if (!isBurning()) {
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                }
            } else if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                if (fluidTank.getFluidAmount() >= 50) {
                    burnTime = burnTimeTotal = 100;
                    fluidTank.drainInternal(50);
                    energyPerTick = 40;
                    dirty = true;
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, true), 3);
                }
            }

            if (dirty) {
                setChanged();
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

    private static class GeneratorSyncData implements ContainerData {
        private final GasGeneratorTileEntity machine;

        public GeneratorSyncData(GasGeneratorTileEntity machine) {
            this.machine = machine;
        }

        public int get(int index) {
            switch(index) {
                case 0:
                    return machine.burnTime;
                case 1:
                    return machine.burnTimeTotal;
                case 2:
                    return machine.energyStorage.getEnergyStored();
                case 3:
                    return machine.fluidTank.getFluidAmount();
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    machine.burnTime = value;
                    break;
                case 1:
                    machine.burnTimeTotal = value;
                    break;
                case 2:
                    machine.energyStorage.setEnergy(value);
            }

        }

        public int getCount() {
            return 4;
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void syncToClient() {
        if (!level.isClientSide) {
            ((ServerLevel) level).getChunkSource().chunkMap.getPlayers(new ChunkPos(worldPosition), false)
                    .forEach(player -> player.connection.send(getUpdatePacket()));
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        fluidTank.writeToNBT(nbt);
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        fluidTank.readFromNBT(pkt.getTag());
    }
}
