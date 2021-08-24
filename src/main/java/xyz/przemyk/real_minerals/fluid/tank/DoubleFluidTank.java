package xyz.przemyk.real_minerals.fluid.tank;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class DoubleFluidTank implements IFluidHandler {

    public final FluidTank input;
    public final FluidTank output;

    public DoubleFluidTank(int capacity, Predicate<FluidStack> inputValidator, Runnable markUpdated) {
        this.input = new FluidTank(capacity, inputValidator) {
            @Override
            protected void onContentsChanged() {
                markUpdated.run();
            }
        };
        this.output = new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                markUpdated.run();
            }
        };
    }

    @Override
    public int getTanks() {
        return 2;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return switch (tank) {
            case 0 -> input.getFluid();
            case 1 -> output.getFluid();
            default -> FluidStack.EMPTY;
        };
    }

    @Override
    public int getTankCapacity(int tank) {
        return switch (tank) {
            case 0 -> input.getCapacity();
            case 1 -> output.getCapacity();
            default -> 0;
        };
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return tank == 0 && input.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return input.fill(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return output.drain(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return output.drain(maxDrain, action);
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
        nbt.put("input", input.writeToNBT(new CompoundTag()));
        nbt.put("output", output.writeToNBT(new CompoundTag()));
        return nbt;
    }

    public void readFromNBT(CompoundTag nbt) {
        input.readFromNBT(nbt.getCompound("input"));
        output.readFromNBT(nbt.getCompound("output"));
    }
}
