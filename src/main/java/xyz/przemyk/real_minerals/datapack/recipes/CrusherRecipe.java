package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;

public record CrusherRecipe(ResourceLocation id, Ingredient input, ItemStack output) implements Recipe<Container> {

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Recipes.CRUSHER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.CRUSHER_RECIPE_TYPE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrusherRecipe> {

        @Override
        public CrusherRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final JsonElement inputElement = GsonHelper.isArrayNode(json, "input") ? GsonHelper.getAsJsonArray(json, "input") : GsonHelper.getAsJsonObject(json, "input");
            final Ingredient input = Ingredient.fromJson(inputElement);
            final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new CrusherRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public CrusherRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final ItemStack output = buffer.readItem();
            return new CrusherRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrusherRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}
