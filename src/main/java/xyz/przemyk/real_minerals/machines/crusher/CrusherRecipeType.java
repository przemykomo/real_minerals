package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class CrusherRecipeType implements IRecipeType<CrusherRecipe> {
    @Override
    public String toString() {
        return RealMinerals.MODID + ":crusher";
    }
}
