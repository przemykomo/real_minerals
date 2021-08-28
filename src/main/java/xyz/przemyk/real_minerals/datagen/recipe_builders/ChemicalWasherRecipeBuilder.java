package xyz.przemyk.real_minerals.datagen.recipe_builders;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ChemicalWasherRecipeBuilder {
    private final FluidStack inputFluidStack;
    private final Ingredient ingredient;
    private final FluidStack outputFluidStack;
    private final RecipeSerializer<?> serializer;

    public ChemicalWasherRecipeBuilder(RecipeSerializer<?> serializer, FluidStack inputFluidStack, Ingredient ingredient, FluidStack outputFluidStack) {
        this.inputFluidStack = inputFluidStack;
        this.ingredient = ingredient;
        this.outputFluidStack = outputFluidStack;
        this.serializer = serializer;
    }

    public static ChemicalWasherRecipeBuilder dissolvingRecipe(Ingredient ingredient, Item dissolvedItem, int fluidCount) {
        FluidStack input = new FluidStack(Registering.ACID_FLUID.get(), fluidCount);
        FluidStack output = input.copy();
        output.getOrCreateTag().putString("item", dissolvedItem.getRegistryName().toString());
        return new ChemicalWasherRecipeBuilder(Recipes.CHEMICAL_WASHER_SERIALIZER.get(), input, ingredient, output);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        consumer.accept(new ResultRecipe(id, inputFluidStack, ingredient, outputFluidStack, serializer));
    }

    public record ResultRecipe(ResourceLocation id,
                               FluidStack inputFluidStack,
                               Ingredient ingredient,
                               FluidStack outputFluidStack,
                               RecipeSerializer<?> serializer) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input_fluid", Recipes.writeFluidStack(inputFluidStack));
            pJson.add("input_item", ingredient.toJson());
            pJson.add("output", Recipes.writeFluidStack(outputFluidStack));
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
