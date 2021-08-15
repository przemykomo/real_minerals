package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.RealMinerals;

import javax.annotation.Nullable;
import java.util.Objects;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import xyz.przemyk.real_minerals.init.Recipes;

public class GasSeparatorRecipe extends ItemMachineRecipe {

    private final FluidStack fluidOutput;

    public GasSeparatorRecipe(ResourceLocation id, Ingredient input, FluidStack fluidOutput, ItemStack itemOutput) {
        super(NonNullList.withSize(1, itemOutput), id, NonNullList.withSize(1, input));
        this.fluidOutput = fluidOutput;
    }

    public FluidStack getFluidOutput() {
        return fluidOutput;
    }

    @Override
    public boolean isValidInput(NonNullList<ItemStack> input) {
        return ingredients.get(0).test(input.get(0));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Recipes.GAS_SEPARATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.GAS_SEPARATOR_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GasSeparatorRecipe> {

        @Override
        public GasSeparatorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));

            final JsonObject jsonObject = GsonHelper.getAsJsonObject(json, "fluidOutput");
            final FluidStack fluidOutput = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()))), jsonObject.get("amount").getAsInt());

            final ItemStack itemOutput = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "itemOutput"));

            return new GasSeparatorRecipe(recipeId, input, fluidOutput, itemOutput);
        }

        @Nullable
        @Override
        public GasSeparatorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final FluidStack fluidOutput = buffer.readFluidStack();
            final ItemStack itemOutput = buffer.readItem();
            return new GasSeparatorRecipe(recipeId, input, fluidOutput, itemOutput);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, GasSeparatorRecipe recipe) {
            recipe.ingredients.get(0).toNetwork(buffer);
            buffer.writeFluidStack(recipe.fluidOutput);
            buffer.writeItem(recipe.outputs.get(0));
        }
    }
}
