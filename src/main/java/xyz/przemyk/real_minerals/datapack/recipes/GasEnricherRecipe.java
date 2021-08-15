package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;
import java.util.Objects;

public record GasEnricherRecipe(ResourceLocation id, FluidStack input, FluidStack output) implements Recipe<Container> {

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
        return Recipes.GAS_ENRICHER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.GAS_ENRICHER_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GasEnricherRecipe> {

        @Override
        public GasEnricherRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonObject input = GsonHelper.getAsJsonObject(json, "input");
            JsonObject output = GsonHelper.getAsJsonObject(json, "output");
            FluidStack inputStack = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(input.get("fluid").getAsString()))), input.get("amount").getAsInt());
            FluidStack outputStack = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(output.get("fluid").getAsString()))), output.get("amount").getAsInt());

            return new GasEnricherRecipe(recipeId, inputStack, outputStack);
        }

        @Nullable
        @Override
        public GasEnricherRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            FluidStack input = buffer.readFluidStack();
            FluidStack output = buffer.readFluidStack();
            return new GasEnricherRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, GasEnricherRecipe recipe) {
            buffer.writeFluidStack(recipe.input);
            buffer.writeFluidStack(recipe.output);
        }
    }
}
