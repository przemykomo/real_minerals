package xyz.przemyk.real_minerals.fluid.tank;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.fluid.DummyFluid;
import xyz.przemyk.real_minerals.init.Registering;

public class EnrichedShaleGasFluid extends DummyFluid {
    public EnrichedShaleGasFluid() {
        super(true);
    }

    @Override
    public Item getBucket() {
        return Registering.ENRICHED_SHALE_GAS_BUCKET.get();
    }
}
