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
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;

public record MixerRecipe(ResourceLocation id, FluidStack firstInput, FluidStack secondInput, FluidStack output) implements Recipe<Container> {
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
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
        return Recipes.MIXER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.MIXER_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MixerRecipe> {

        @Override
        public MixerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            FluidStack firstInput = Recipes.readFluidStack(GsonHelper.getAsJsonObject(json, "first_input"));
            FluidStack secondInput = Recipes.readFluidStack(GsonHelper.getAsJsonObject(json, "second_input"));
            FluidStack output = Recipes.readFluidStack(GsonHelper.getAsJsonObject(json, "output"));

            return new MixerRecipe(recipeId, firstInput, secondInput, output);
        }

        @Nullable
        @Override
        public MixerRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            FluidStack firstInput = buffer.readFluidStack();
            FluidStack secondInput = buffer.readFluidStack();
            FluidStack output = buffer.readFluidStack();
            return new MixerRecipe(recipeId, firstInput, secondInput, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MixerRecipe recipe) {
            buffer.writeFluidStack(recipe.firstInput);
            buffer.writeFluidStack(recipe.secondInput);
            buffer.writeFluidStack(recipe.output);
        }
    }
}
