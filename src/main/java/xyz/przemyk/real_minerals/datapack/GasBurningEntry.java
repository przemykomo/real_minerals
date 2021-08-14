package xyz.przemyk.real_minerals.datapack;

import net.minecraftforge.fluids.FluidStack;

public record GasBurningEntry(FluidStack fluidStack, int energyPerTick, int burnTime) {
}
