package xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace;

import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class AlloyRecipeType implements IRecipeType<AlloyRecipe> {
    @Override
    public String toString() {
        return RealMinerals.MODID + ":alloy";
    }
}
