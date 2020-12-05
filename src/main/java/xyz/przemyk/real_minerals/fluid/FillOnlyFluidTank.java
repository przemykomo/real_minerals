package xyz.przemyk.real_minerals.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class FillOnlyFluidTank extends FluidTank {

    public FillOnlyFluidTank(int capacity) {
        super(capacity);
    }

    @Nonnull
    @Override
    @Deprecated
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    public FluidStack drainInternal(int maxDrain) {
        return super.drain(maxDrain, FluidAction.EXECUTE);
    }
}
