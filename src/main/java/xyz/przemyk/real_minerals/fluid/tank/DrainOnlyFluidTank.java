package xyz.przemyk.real_minerals.fluid.tank;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class DrainOnlyFluidTank extends FluidTank {

    public DrainOnlyFluidTank(int capacity) {
        super(capacity);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return 0;
    }

    public void fillInternal(FluidStack resource) {
        super.fill(resource, FluidAction.EXECUTE);
    }
}
