package xyz.przemyk.real_minerals.fluid;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.init.Registering;

public class SulfurTrioxideGasFluid extends GasFluid {
    @Override
    public Item getBucket() {
        return Registering.SULFUR_TRIOXIDE_GAS_BUCKET.get();
    }
}
