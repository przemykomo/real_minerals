package xyz.przemyk.real_minerals.machines.electric.magnetizer;

import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class MagnetizerRecipeType implements IRecipeType<MagnetizerRecipe> {
    @Override
    public String toString() {
        return RealMinerals.MODID + ":magnetizer";
    }
}
