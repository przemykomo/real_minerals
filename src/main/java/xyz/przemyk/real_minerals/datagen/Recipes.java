package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.init.RealMinerals;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        //TODO: more recipes
        CrusherRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_COPPER), RealMinerals.COPPER_DUST.get(), 2).build(consumer, new ResourceLocation(RealMinerals.MODID, "copper_dust_from_ore"));
    }
}
