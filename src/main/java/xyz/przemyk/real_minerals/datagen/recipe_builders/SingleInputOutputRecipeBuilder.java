package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.gson.JsonObject;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerRecipe;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherRecipe;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SingleInputOutputRecipeBuilder {
    private final Item output;
    private final Ingredient input;
    private final int count;
    private final IRecipeSerializer<?> serializer;

    public SingleInputOutputRecipeBuilder(IRecipeSerializer<?> serializer, Ingredient input, IItemProvider resultProviderIn, int count) {
        this.output = resultProviderIn.asItem();
        this.input = input;
        this.count = count;
        this.serializer = serializer;
    }

    public static SingleInputOutputRecipeBuilder crushingRecipe(Ingredient ingredient, IItemProvider result) {
        return new SingleInputOutputRecipeBuilder(CrusherRecipe.SERIALIZER, ingredient, result, 1);
    }

    public static SingleInputOutputRecipeBuilder crushingRecipe(Ingredient ingredient, IItemProvider result, int count) {
        return new SingleInputOutputRecipeBuilder(CrusherRecipe.SERIALIZER, ingredient, result, count);
    }
    public static SingleInputOutputRecipeBuilder magnetizerRecipe(Ingredient ingredient, IItemProvider result) {
        return new SingleInputOutputRecipeBuilder(MagnetizerRecipe.SERIALIZER, ingredient, result, 1);
    }

    public static SingleInputOutputRecipeBuilder magnetizerRecipe(Ingredient ingredient, IItemProvider result, int count) {
        return new SingleInputOutputRecipeBuilder(MagnetizerRecipe.SERIALIZER, ingredient, result, count);
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ResultRecipe(id, serializer, input, output, count));
    }

    public static class ResultRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final Item output;
        private final int count;
        private final IRecipeSerializer<?> serializer;

        public ResultRecipe(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient input, Item output, int count) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.count = count;
            this.serializer = serializer;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void serialize(JsonObject json) {
            json.add("input", input.serialize());
            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", output.getRegistryName().toString());
            if (count > 1) {
                outputObject.addProperty("count", count);
            }
            json.add("output", outputObject);
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
