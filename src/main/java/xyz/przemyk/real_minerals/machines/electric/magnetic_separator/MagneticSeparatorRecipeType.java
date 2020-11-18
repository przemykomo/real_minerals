package xyz.przemyk.real_minerals.machines.electric.magnetic_separator;

import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class MagneticSeparatorRecipeType implements IRecipeType<MagneticSeparatorRecipe> {
    @Override
    public String toString() {
        return RealMinerals.MODID + ":magnetic_separator";
    }
}
