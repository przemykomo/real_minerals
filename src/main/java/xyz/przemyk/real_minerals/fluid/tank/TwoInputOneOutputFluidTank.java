package xyz.przemyk.real_minerals.fluid.tank;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class TwoInputOneOutputFluidTank implements IFluidHandler {

    public final FluidTank firstInput;
    public final FluidTank secondInput;
    public final FluidTank output;

    public TwoInputOneOutputFluidTank(int capacity, Predicate<FluidStack> inputValidator, Runnable markUpdated) {
        this.firstInput = new FluidTank(capacity, inputValidator) {
            @Override
            protected void onContentsChanged() {
                markUpdated.run();
            }
        };
        this.secondInput = new FluidTank(capacity, inputValidator) {
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
        return 3;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return switch (tank) {
            case 0 -> firstInput.getFluid();
            case 1 -> secondInput.getFluid();
            case 2 -> output.getFluid();
            default -> FluidStack.EMPTY;
        };
    }

    @Override
    public int getTankCapacity(int tank) {
        return switch (tank) {
            case 0 -> firstInput.getCapacity();
            case 1 -> secondInput.getCapacity();
            case 2 -> output.getCapacity();
            default -> 0;
        };
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return tank < 2 && firstInput.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        int filled = firstInput.fill(resource, action);
        FluidStack modified = resource.copy();
        modified.shrink(filled);
        filled += secondInput.fill(modified, action);
        return filled;
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

    public Tag writeToNBT(CompoundTag nbt) {
        nbt.put("first_input", firstInput.writeToNBT(new CompoundTag()));
        nbt.put("second_input", secondInput.writeToNBT(new CompoundTag()));
        nbt.put("output", output.writeToNBT(new CompoundTag()));
        return nbt;
    }

    public void readFromNBT(CompoundTag nbt) {
        firstInput.readFromNBT(nbt.getCompound("first_input"));
        secondInput.readFromNBT(nbt.getCompound("second_input"));
        output.readFromNBT(nbt.getCompound("output"));
    }
}
