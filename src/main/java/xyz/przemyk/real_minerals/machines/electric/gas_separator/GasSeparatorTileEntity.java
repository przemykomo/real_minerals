package xyz.przemyk.real_minerals.machines.electric.gas_separator;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.przemyk.real_minerals.fluid.DrainOnlyFluidTank;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricRecipeProcessingTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasSeparatorTileEntity extends ElectricRecipeProcessingTileEntity<GasSeparatorRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public final DrainOnlyFluidTank fluidTank = new DrainOnlyFluidTank(FluidAttributes.BUCKET_VOLUME);

    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);

    public GasSeparatorTileEntity() {
        super(Registering.GAS_SEPARATOR_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0), FE_PER_TICK, 2, WORKING_TIME_TOTAL);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();

        for (Direction direction : Direction.values()) {
            TileEntity tileEntity = world.getTileEntity(pos.offset(direction));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())
                        .ifPresent(fluidHandler -> FluidUtil.tryFluidTransfer(fluidHandler, fluidTank, fluidTank.getFluidAmount(), true));
            }
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        fluidTank.readFromNBT(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        fluidTank.writeToNBT(compound);
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

    private GasSeparatorRecipe cachedRecipe = null;

    @Override
    public GasSeparatorRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = RealMinerals.getRecipe(input, world, RealMinerals.GAS_SEPARATOR_RECIPE_TYPE);
        return cachedRecipe;
    }

    @Override
    protected boolean canProcess(GasSeparatorRecipe recipe) {
        if (recipe != null) {
            FluidStack fluidOutput = recipe.getFluidOutput();
            ItemStack itemOutput = recipe.getRecipeOutput();
            if (fluidOutput.isEmpty()) {
                return false;
            }
            FluidStack currentFluidOutput = fluidTank.getFluid();
            ItemStack currentItemOutput = itemHandler.getStackInSlot(1);
            if (currentFluidOutput.isEmpty() && (currentItemOutput.isEmpty() || itemOutput.isEmpty())) {
                return true;
            }

            if (!currentFluidOutput.isFluidEqual(fluidOutput)) {
                return false;
            }

            if (!currentItemOutput.isEmpty() && !currentItemOutput.isItemEqual(itemOutput)) {
                return false;
            }

            return (currentFluidOutput.getAmount() + fluidOutput.getAmount() <= fluidTank.getCapacity()) && (currentItemOutput.getCount() + itemOutput.getCount() <= currentItemOutput.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void process(GasSeparatorRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack itemStack = itemHandler.getStackInSlot(1);

        FluidStack fluidOutput = recipe.getFluidOutput();
        ItemStack itemOutput = recipe.getRecipeOutput();

        fluidTank.fillInternal(fluidOutput);

        if (!itemOutput.isEmpty()) {
            if (itemStack.isEmpty()) {
                itemHandler.setStackInSlot(1, itemOutput.copy());
            } else {
                itemStack.grow(itemOutput.getCount());
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return GasSeparatorContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new GasSeparatorContainer(id, playerInventory, getPos(), itemHandler, new GasSeparatorSyncData(this), serverPlayer);
    }

    protected static class GasSeparatorSyncData implements IIntArray {
        private final GasSeparatorTileEntity machine;

        public GasSeparatorSyncData(GasSeparatorTileEntity machine) {
            this.machine = machine;
        }

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return machine.workingTime;
                case 1:
                    return machine.energyStorage.getEnergyStored();
                case 2:
                    return machine.fluidTank.getFluidAmount();
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
                    break;
            }
        }

        @Override
        public int size() {
            return 4;
        }
    }
}
