package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.datapack.recipes.AlloyRecipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class AlloyRecipeBuilder {

    private final Item result;
    private final int count;
    private final List<Ingredient> ingredients = Lists.newArrayList();

    public AlloyRecipeBuilder(Item result, int count) {
        this.result = result;
        this.count = count;
    }

    public static AlloyRecipeBuilder alloyRecipe(Item result) {
        return new AlloyRecipeBuilder(result, 1);
    }

    public static AlloyRecipeBuilder alloyRecipe(Item result, int count) {
        return new AlloyRecipeBuilder(result, count);
    }

    /**
     * Adds an ingredient.
     */
    public AlloyRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return this.addIngredient(ingredientIn, 1);
    }

    /**
     * Adds an ingredient multiple times.
     */
    public AlloyRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredientIn);
        }

        return this;
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    @SuppressWarnings("ConstantConditions")
    public void build(Consumer<FinishedRecipe> consumerIn) {
        this.build(consumerIn, new ResourceLocation(RealMinerals.MODID, result.getRegistryName().getPath() + "_alloy"));
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.count, this.ingredients));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final List<Ingredient> ingredients;

        public Result(ResourceLocation id, Item result, int count, List<Ingredient> ingredients) {
            this.id = id;
            this.result = result;
            this.count = count;
            this.ingredients = ingredients;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray inputArray = new JsonArray();
            for (Ingredient ingredient : ingredients) {
                inputArray.add(ingredient.toJson());
            }

            json.add("ingredients", inputArray);
            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", result.getRegistryName().toString());
            if (count > 1) {
                resultJson.addProperty("count", count);
            }

            json.add("result", resultJson);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return AlloyRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
