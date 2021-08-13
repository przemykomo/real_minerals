package xyz.przemyk.real_minerals.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FillOnlyFluidTank extends FluidTank {

    public FillOnlyFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    public void drainInternal(int maxDrain) {
        super.drain(maxDrain, FluidAction.EXECUTE);
    }
}
