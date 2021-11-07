package xyz.przemyk.real_minerals.datapack.recipes;

import com.google.gson.JsonObject;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.Recipes;

import javax.annotation.Nullable;
import java.util.Objects;

public record ManaExtractorRecipe(ResourceLocation id, Ingredient ingredient, FluidStack result) implements Recipe<Container> {

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
        return Recipes.MANA_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.MANA_EXTRACTOR_RECIPE_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ManaExtractorRecipe> {

        @Override
        public ManaExtractorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            final Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));

            final JsonObject jsonObject = GsonHelper.getAsJsonObject(pSerializedRecipe, "result");
            final FluidStack fluidOutput = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()))), jsonObject.get("amount").getAsInt());

            return new ManaExtractorRecipe(pRecipeId, input, fluidOutput);
        }

        @Nullable
        @Override
        public ManaExtractorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            final Ingredient input = Ingredient.fromNetwork(pBuffer);
            final FluidStack fluidOutput = pBuffer.readFluidStack();
            return new ManaExtractorRecipe(pRecipeId, input, fluidOutput);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ManaExtractorRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeFluidStack(pRecipe.result);
        }
    }
}
