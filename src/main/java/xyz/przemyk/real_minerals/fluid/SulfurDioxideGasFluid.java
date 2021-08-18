package xyz.przemyk.real_minerals.fluid;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.init.Registering;

public class SulfurDioxideGasFluid extends GasFluid {
    @Override
    public Item getBucket() {
        return Registering.SULFUR_DIOXIDE_GAS_BUCKET.get();
    }
}
