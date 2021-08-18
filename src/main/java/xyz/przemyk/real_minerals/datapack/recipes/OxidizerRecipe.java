package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;

public record OxidizerRecipe(ResourceLocation id, FluidStack inputFluidStack, Ingredient ingredient, FluidStack outputFluidStack) implements Recipe<Container> {
    @Override
    public boolean matches(Container p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(Container p_44001_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Recipes.OXIDIZER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.OXIDIZER_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<OxidizerRecipe> {

        @Override
        public OxidizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            FluidStack inputFluidStack;
            try {
                inputFluidStack = Recipes.readFluidStack(GsonHelper.getAsJsonObject(json, "input_fluid"));
            } catch (JsonSyntaxException e) {
                inputFluidStack = FluidStack.EMPTY;
            }
            Ingredient ingredient;
            try {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input_item"));
            } catch (JsonSyntaxException e) {
                if (inputFluidStack.isEmpty()) {
                    throw new IllegalArgumentException("input_fluid and input_item can't be both empty!");
                }
                ingredient = Ingredient.EMPTY;
            }
            FluidStack outputFluidStack = Recipes.readFluidStack(GsonHelper.getAsJsonObject(json, "output"));

            return new OxidizerRecipe(recipeId, inputFluidStack, ingredient, outputFluidStack);
        }

        @Nullable
        @Override
        public OxidizerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            FluidStack inputFluidStack = buffer.readFluidStack();
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            FluidStack outputFluidStack = buffer.readFluidStack();
            return new OxidizerRecipe(recipeId, inputFluidStack, ingredient, outputFluidStack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, OxidizerRecipe recipe) {
            buffer.writeFluidStack(recipe.inputFluidStack);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeFluidStack(recipe.outputFluidStack);
        }
    }
}
