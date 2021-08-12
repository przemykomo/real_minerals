package xyz.przemyk.real_minerals.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.RealMinerals;

import javax.annotation.Nullable;
import java.util.Objects;

public class GasSeparatorRecipe extends MachineRecipe {

    public static final Serializer SERIALIZER = new Serializer();

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
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RealMinerals.GAS_SEPARATOR_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<GasSeparatorRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "gas_separator"));
        }

        @Override
        public GasSeparatorRecipe read(ResourceLocation recipeId, JsonObject json) {
            final Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));

            final JsonObject jsonObject = JSONUtils.getJsonObject(json, "fluidOutput");
            final FluidStack fluidOutput = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()))), jsonObject.get("amount").getAsInt());

            final ItemStack itemOutput = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "itemOutput"));

            return new GasSeparatorRecipe(recipeId, input, fluidOutput, itemOutput);
        }

        @Nullable
        @Override
        public GasSeparatorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input = Ingredient.read(buffer);
            final FluidStack fluidOutput = buffer.readFluidStack();
            final ItemStack itemOutput = buffer.readItemStack();
            return new GasSeparatorRecipe(recipeId, input, fluidOutput, itemOutput);
        }

        @Override
        public void write(PacketBuffer buffer, GasSeparatorRecipe recipe) {
            recipe.ingredients.get(0).write(buffer);
            buffer.writeFluidStack(recipe.fluidOutput);
            buffer.writeItemStack(recipe.outputs.get(0));
        }
    }
}
