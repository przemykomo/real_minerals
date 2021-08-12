package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.real_minerals.recipes.MagneticSeparatorRecipe;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MagneticSeparatorRecipeBuilder {
    private final Item output;
    private final int outputCount;
    private Item secondOutput = Items.AIR;
    private int secondOutputCount = 0;

    private final Ingredient input;
    private final RecipeSerializer<?> serializer;

    public MagneticSeparatorRecipeBuilder(RecipeSerializer<?> serializer, Ingredient input, Item output, int count) {
        this.serializer = serializer;
        this.input = input;
        this.output = output;
        this.outputCount = count;
    }

    public static MagneticSeparatorRecipeBuilder magneticSeparatorRecipe(Ingredient ingredient, Item output, int count) {
        return new MagneticSeparatorRecipeBuilder(MagneticSeparatorRecipe.SERIALIZER, ingredient, output, count);
    }

    public static MagneticSeparatorRecipeBuilder magneticSeparatorRecipe(Ingredient ingredient, Item output) {
        return new MagneticSeparatorRecipeBuilder(MagneticSeparatorRecipe.SERIALIZER, ingredient, output, 1);
    }

    public MagneticSeparatorRecipeBuilder secondOutput(Item secondOutput, int count) {
        this.secondOutput = secondOutput;
        this.secondOutputCount = count;
        return this;
    }

    public void secondOutput(Item secondOutput) {
        secondOutput(secondOutput, outputCount);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ResultRecipe(id, serializer, input, output, outputCount, secondOutput, secondOutputCount));
    }

    public static class ResultRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item output;
        private final int outputCount;
        private final Item secondOutput;
        private final int secondOutputCount;
        private final RecipeSerializer<?> serializer;


        public ResultRecipe(ResourceLocation id, RecipeSerializer<?> serializer, Ingredient input, Item output, int outputCount, Item secondOutput, int secondOutputCount) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
            this.secondOutput = secondOutput;
            this.secondOutputCount = secondOutputCount;
            this.serializer = serializer;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());

            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", output.getRegistryName().toString());
            if (outputCount > 1) {
                outputObject.addProperty("count", outputCount);
            }
            json.add("output", outputObject);

            if (secondOutputCount > 0) {
                JsonObject secondOutputObject = new JsonObject();
                secondOutputObject.addProperty("item", secondOutput.getRegistryName().toString());
                if (secondOutputCount > 1) {
                    secondOutputObject.addProperty("count", secondOutputCount);
                }
                json.add("secondOutput", secondOutputObject);
            }
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
