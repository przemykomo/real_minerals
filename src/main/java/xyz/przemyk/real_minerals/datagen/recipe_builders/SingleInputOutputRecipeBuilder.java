package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.real_minerals.datapack.recipes.MagnetizerRecipe;
import xyz.przemyk.real_minerals.datapack.recipes.CrusherRecipe;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SingleInputOutputRecipeBuilder {
    private final Item output;
    private final Ingredient input;
    private final int count;
    private final RecipeSerializer<?> serializer;

    public SingleInputOutputRecipeBuilder(RecipeSerializer<?> serializer, Ingredient input, ItemLike resultProviderIn, int count) {
        this.output = resultProviderIn.asItem();
        this.input = input;
        this.count = count;
        this.serializer = serializer;
    }

    public static SingleInputOutputRecipeBuilder crushingRecipe(Ingredient ingredient, ItemLike result) {
        return new SingleInputOutputRecipeBuilder(Recipes.CRUSHER_SERIALIZER.get(), ingredient, result, 1);
    }

    public static SingleInputOutputRecipeBuilder crushingRecipe(Ingredient ingredient, ItemLike result, int count) {
        return new SingleInputOutputRecipeBuilder(Recipes.CRUSHER_SERIALIZER.get(), ingredient, result, count);
    }
    public static SingleInputOutputRecipeBuilder magnetizerRecipe(Ingredient ingredient, ItemLike result) {
        return new SingleInputOutputRecipeBuilder(Recipes.MAGNETIZER_SERIALIZER.get(), ingredient, result, 1);
    }

    public static SingleInputOutputRecipeBuilder magnetizerRecipe(Ingredient ingredient, ItemLike result, int count) {
        return new SingleInputOutputRecipeBuilder(Recipes.MAGNETIZER_SERIALIZER.get(), ingredient, result, count);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ResultRecipe(id, serializer, input, output, count));
    }

    public static class ResultRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item output;
        private final int count;
        private final RecipeSerializer<?> serializer;

        public ResultRecipe(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient input, Item output, int count) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.count = count;
            this.serializer = serializer;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());
            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", output.getRegistryName().toString());
            if (count > 1) {
                outputObject.addProperty("count", count);
            }
            json.add("output", outputObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
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
