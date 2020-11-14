package xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.not_electric.MachineRecipe;

import javax.annotation.Nullable;

public class AlloyRecipe extends MachineRecipe {

    public static final Serializer SERIALIZER = new Serializer();

    public AlloyRecipe(ItemStack output, ResourceLocation id, NonNullList<Ingredient> ingredients) {
        super(output, id, ingredients);
    }

    @Override
    public boolean isValidInput(NonNullList<ItemStack> input) {
        //I'm not sure if it works but looks like findMatches is made for shapeless recipes
        return RecipeMatcher.findMatches(input, ingredients) != null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return ingredients.size() <= width * height;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RealMinerals.ALLOY_RECIPE_TYPE;
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloyRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "alloy"));
        }

        @Override
        public AlloyRecipe read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> ingredients = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for alloying recipe.");
            } else {
                ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
                return new AlloyRecipe(result, recipeId, ingredients);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.deserialize(ingredientArray.get(i));
                if (!ingredient.hasNoMatchingItems()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }
        @Nullable
        @Override
        public AlloyRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int ingredientsSize = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsSize, Ingredient.EMPTY);

            for (int i = 0; i < ingredientsSize; ++i) {
                ingredients.set(i, Ingredient.read(buffer));
            }

            ItemStack output = buffer.readItemStack();
            return new AlloyRecipe(output, recipeId, ingredients);
        }

        @Override
        public void write(PacketBuffer buffer, AlloyRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.output);
        }
    }
}
