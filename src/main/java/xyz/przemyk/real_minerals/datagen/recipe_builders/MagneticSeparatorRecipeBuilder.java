package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.recipes.MagneticSeparatorRecipe;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MagneticSeparatorRecipeBuilder {
    private final Item output;
    private final int outputCount;
    private Item secondOutput = Items.AIR;
    private int secondOutputCount = 0;

    private final Ingredient input;
    private final IRecipeSerializer<?> serializer;

    public MagneticSeparatorRecipeBuilder(IRecipeSerializer<?> serializer, Ingredient input, Item output, int count) {
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

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ResultRecipe(id, serializer, input, output, outputCount, secondOutput, secondOutputCount));
    }

    public static class ResultRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item output;
        private final int outputCount;
        private final Item secondOutput;
        private final int secondOutputCount;
        private final IRecipeSerializer<?> serializer;


        public ResultRecipe(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient input, Item output, int outputCount, Item secondOutput, int secondOutputCount) {
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
        public void serialize(JsonObject json) {
            json.add("input", input.serialize());

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
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return serializer;
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
