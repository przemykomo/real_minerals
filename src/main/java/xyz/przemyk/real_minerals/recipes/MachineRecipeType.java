package xyz.przemyk.real_minerals.recipes;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import xyz.przemyk.real_minerals.RealMinerals;

public record MachineRecipeType<T extends Recipe<?>>(String id) implements RecipeType<T> {

    public MachineRecipeType(String id) {
        this.id = RealMinerals.MODID + ":" + id;
    }

    @Override
    public String toString() {
        return id;
    }
}
