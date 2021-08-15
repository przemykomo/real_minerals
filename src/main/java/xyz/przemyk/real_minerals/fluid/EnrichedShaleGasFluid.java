package xyz.przemyk.real_minerals.fluid;

import net.minecraft.world.item.Item;
import xyz.przemyk.real_minerals.init.Registering;

public class EnrichedShaleGasFluid extends GasFluid {
    @Override
    public Item getBucket() {
        return Registering.ENRICHED_SHALE_GAS_BUCKET.get();
    }
}
