package xyz.przemyk.real_minerals.machines.crusher;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nullable;

public class CrusherRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack output;
    private final ResourceLocation id;

    public CrusherRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return "CrusherRecipe{" +
                "input=" + input +
                ", output=" + output +
                ", id=" + id +
                '}';
    }

    // ignored method, use isValidInput
    @Deprecated
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return isValidInput(inv.getStackInSlot(0));
    }

    // ignored method, use getRecipeOutput
    @Deprecated
    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
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
        return RealMinerals.CRUSHER_RECIPE_TYPE;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(RealMinerals.CRUSHER_BLOCK.ITEM.get());
    }

    public boolean isValidInput(ItemStack input) {
        return this.input.test(input);
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrusherRecipe> {

        public Serializer() {
            setRegistryName(new ResourceLocation(RealMinerals.MODID, "crusher"));
        }

        @Override
        public CrusherRecipe read(ResourceLocation recipeId, JsonObject json) {
            final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
            final Ingredient input = Ingredient.deserialize(inputElement);
            final ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            return new CrusherRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public CrusherRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            final Ingredient input = Ingredient.read(buffer);
            final ItemStack output = buffer.readItemStack();
            return new CrusherRecipe(recipeId, input, output);
        }

        @Override
        public void write(PacketBuffer buffer, CrusherRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output);
        }
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return list;
    }
}
