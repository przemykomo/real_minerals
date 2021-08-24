package xyz.przemyk.real_minerals.fluid;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.init.Registering;

public class ShaleGasFluid extends DummyFluid {
    public ShaleGasFluid() {
        super(true);
    }

    @Override
    public Item getBucket() {
        return Registering.SHALE_GAS_BUCKET.get();
    }
}
