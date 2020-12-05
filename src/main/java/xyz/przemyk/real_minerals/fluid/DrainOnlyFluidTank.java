package xyz.przemyk.real_minerals.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class DrainOnlyFluidTank extends FluidTank {
    public DrainOnlyFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    @Deprecated
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    public void fillInternal(FluidStack resource) {
        super.fill(resource, FluidAction.EXECUTE);
    }
}
