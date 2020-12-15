package xyz.przemyk.real_minerals.machines.generators.gas;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.przemyk.real_minerals.datagen.providers.FluidTags;
import xyz.przemyk.real_minerals.fluid.FillOnlyFluidTank;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.EnergyOutputTileEntity;

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

    public GasGeneratorTileEntity() {
        super(Registering.GAS_GENERATOR_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 0, 160));
        fluidTank.setValidator(fluidStack -> FluidTags.BURNABLE_GAS.contains(fluidStack.getFluid()));
    }

    @Override
    public void remove() {
        super.remove();
        fluidHandlerLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote) {
            boolean dirty = false;
            if (isBurning()) {
                --burnTime;
                dirty = true;
                energyStorage.addEnergy(energyPerTick);
                if (!isBurning()) {
                    world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, false), 3);
                }
            } else if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                if (fluidTank.getFluidAmount() >= 50) {
                    burnTime = burnTimeTotal = 100;
                    fluidTank.drainInternal(50);
                    energyPerTick = 40;
                    dirty = true;
                    world.setBlockState(pos, getBlockState().with(BlockStateProperties.LIT, true), 3);
                }
            }

            if (dirty) {
                markDirty();
            }
        }
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        fluidTank.readFromNBT(nbt);
        burnTime = nbt.getInt("BurnTime");
        burnTimeTotal = nbt.getInt("BurnTimeTotal");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
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
    public ITextComponent getDisplayName() {
        return GasGeneratorContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new GasGeneratorContainer(id, playerInventory, getPos(), new GeneratorSyncData(this), serverPlayer);
    }

    private static class GeneratorSyncData implements IIntArray {
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

        public int size() {
            return 4;
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected void syncToClient() {
        if (!world.isRemote) {
            ((ServerWorld) world).getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .forEach(player -> player.connection.sendPacket(getUpdatePacket()));
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        fluidTank.writeToNBT(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        fluidTank.readFromNBT(pkt.getNbtCompound());
    }
}
