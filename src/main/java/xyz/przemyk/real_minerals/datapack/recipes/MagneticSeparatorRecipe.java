package xyz.przemyk.real_minerals.datapack.recipes;

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
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;

public class MagneticSeparatorRecipe extends ItemMachineRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public MagneticSeparatorRecipe(ResourceLocation id, Ingredient input, NonNullList<ItemStack> outputs) {
        super(outputs, id, NonNullList.withSize(1, input));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.MAGNETIC_SEPARATOR_RECIPE_TYPE;
    }

    @Override
    public boolean isValidInput(NonNullList<ItemStack> input) {
        return ingredients.get(0).test(input.get(0));
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MagneticSeparatorRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "magnetic_separator"));
        }

        @Override
        public MagneticSeparatorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            final ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            final ItemStack secondOutput = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "secondOutput"));
            NonNullList<ItemStack> outputs = NonNullList.create();
            outputs.add(output);
            outputs.add(secondOutput);
            return new MagneticSeparatorRecipe(recipeId, input, outputs);
        }

        @Nullable
        @Override
        public MagneticSeparatorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final ItemStack output = buffer.readItem();
            final ItemStack secondOutput = buffer.readItem();
            NonNullList<ItemStack> outputs = NonNullList.create();
            outputs.add(output);
            outputs.add(secondOutput);
            return new MagneticSeparatorRecipe(recipeId, input, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MagneticSeparatorRecipe recipe) {
            recipe.ingredients.get(0).toNetwork(buffer);
            buffer.writeItem(recipe.outputs.get(0));
            buffer.writeItem(recipe.outputs.get(1));
        }
    }
}
