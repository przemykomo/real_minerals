package xyz.przemyk.real_minerals.fluid;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.function.Supplier;

public class ShaleGasFluid extends GasFluid {
    @Override
    public Item getBucket() {
        return Registering.SHALE_GAS_BUCKET.get();
    }
}
