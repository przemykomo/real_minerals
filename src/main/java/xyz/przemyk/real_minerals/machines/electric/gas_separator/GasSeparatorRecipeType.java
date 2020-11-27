package xyz.przemyk.real_minerals.machines.electric.gas_separator;

import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class GasSeparatorRecipeType implements IRecipeType<GasSeparatorRecipe> {
    @Override
    public String toString() {
        return RealMinerals.MODID + ":gas_separator";
    }
}
