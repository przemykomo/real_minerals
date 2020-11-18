package xyz.przemyk.real_minerals.machines.electric.magnetic_separator;

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
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.not_electric.MachineRecipe;

import javax.annotation.Nullable;

public class MagneticSeparatorRecipe extends MachineRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public MagneticSeparatorRecipe(ResourceLocation id, Ingredient input, NonNullList<ItemStack> outputs) {
        super(outputs, id, NonNullList.withSize(1, input));
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RealMinerals.MAGNETIC_SEPARATOR_RECIPE_TYPE;
    }

    @Override
    public boolean isValidInput(NonNullList<ItemStack> input) {
        return ingredients.get(0).test(input.get(0));
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MagneticSeparatorRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "magnetic_separator"));
        }

        @Override
        public MagneticSeparatorRecipe read(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            final ItemStack secondOutput = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "secondOutput"));
            NonNullList<ItemStack> outputs = NonNullList.create();
            outputs.add(output);
            outputs.add(secondOutput);
            return new MagneticSeparatorRecipe(recipeId, input, outputs);
        }

        @Nullable
        @Override
        public MagneticSeparatorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            final ItemStack secondOutput = buffer.readItemStack();
            NonNullList<ItemStack> outputs = NonNullList.create();
            outputs.add(output);
            outputs.add(secondOutput);
            return new MagneticSeparatorRecipe(recipeId, input, outputs);
        }

        @Override
        public void write(PacketBuffer buffer, MagneticSeparatorRecipe recipe) {
            recipe.ingredients.get(0).write(buffer);
            buffer.writeItemStack(recipe.outputs.get(0));
            buffer.writeItemStack(recipe.outputs.get(1));
        }
    }
}
