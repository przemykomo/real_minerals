package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyRecipe;

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
    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, new ResourceLocation(RealMinerals.MODID, result.getRegistryName().getPath() + "_alloy"));
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.count, this.ingredients));
    }

    public static class Result implements IFinishedRecipe {
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
        public void serialize(JsonObject json) {
            JsonArray inputArray = new JsonArray();
            for (Ingredient ingredient : ingredients) {
                inputArray.add(ingredient.serialize());
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
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return AlloyRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
