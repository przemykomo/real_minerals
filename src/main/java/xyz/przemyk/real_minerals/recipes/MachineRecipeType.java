package xyz.przemyk.real_minerals.recipes;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import xyz.przemyk.real_minerals.RealMinerals;

public class MachineRecipeType<T extends IRecipe<?>> implements IRecipeType<T> {

    private final String id;

    public MachineRecipeType(String id) {
        this.id = RealMinerals.MODID + ":" + id;
    }

    @Override
    public String toString() {
        return id;
    }
}
