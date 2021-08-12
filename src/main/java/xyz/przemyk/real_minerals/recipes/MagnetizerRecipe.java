package xyz.przemyk.real_minerals.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nullable;

public class MagnetizerRecipe extends MachineRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public MagnetizerRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        super(NonNullList.withSize(1, output), id, NonNullList.withSize(1, input));
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RealMinerals.MAGNETIZER_RECIPE_TYPE;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Registering.MAGNETIZER_BLOCK.ITEM.get());
    }

    public boolean isValidInput(NonNullList<ItemStack> inputList) {
        ItemStack input = inputList.get(0);
        return ingredients.get(0).test(input);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MagnetizerRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "magnetizer"));
        }

        @Override
        public MagnetizerRecipe read(ResourceLocation recipeId, JsonObject json) {
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
            final Ingredient input = Ingredient.deserialize(inputElement);
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            return new MagnetizerRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public MagnetizerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            return new MagnetizerRecipe(recipeId, input, output);
        }

        @Override
        public void write(PacketBuffer buffer, MagnetizerRecipe recipe) {
            recipe.ingredients.get(0).write(buffer);
            buffer.writeItemStack(recipe.outputs.get(0));
        }
    }
}
