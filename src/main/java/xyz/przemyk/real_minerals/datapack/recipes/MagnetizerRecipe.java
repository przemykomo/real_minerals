package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;

public class MagnetizerRecipe extends ItemMachineRecipe {

    public MagnetizerRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        super(NonNullList.withSize(1, output), id, NonNullList.withSize(1, input));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Recipes.MAGNETIZER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.MAGNETIZER_RECIPE_TYPE;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(MachinesRegistry.MAGNETIZER_BLOCK.ITEM.get());
    }

    public boolean isValidInput(NonNullList<ItemStack> inputList) {
        ItemStack input = inputList.get(0);
        return ingredients.get(0).test(input);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MagnetizerRecipe> {

        @Override
        public MagnetizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final JsonElement inputElement = GsonHelper.isArrayNode(json, "input") ? GsonHelper.getAsJsonArray(json, "input") : GsonHelper.getAsJsonObject(json, "input");
            final Ingredient input = Ingredient.fromJson(inputElement);
            final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new MagnetizerRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public MagnetizerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final ItemStack output = buffer.readItem();
            return new MagnetizerRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MagnetizerRecipe recipe) {
            recipe.ingredients.get(0).toNetwork(buffer);
            buffer.writeItem(recipe.outputs.get(0));
        }
    }
}
