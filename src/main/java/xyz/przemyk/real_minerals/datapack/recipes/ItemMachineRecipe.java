package xyz.przemyk.real_minerals.datapack.recipes;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public abstract class ItemMachineRecipe implements Recipe<Container> {

    protected final NonNullList<ItemStack> outputs;
    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> ingredients;

    public ItemMachineRecipe(NonNullList<ItemStack> outputs, ResourceLocation id, NonNullList<Ingredient> ingredients) {
        this.outputs = outputs;
        this.id = id;
        this.ingredients = ingredients;
    }

    @Deprecated // ignored method, use isValidInput
    @Override
    public boolean matches(Container inv, Level worldIn) {
        return false;
    }

    @Deprecated // ignored method, use getRecipeOutput
    @Override
    public ItemStack assemble(Container inv) {
        return outputs.get(0).copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override // use getRecipeOutputs for multiple outputs
    public ItemStack getResultItem() {
        return outputs.get(0);
    }

    public NonNullList<ItemStack> getRecipeOutputs() {
        return outputs;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public abstract boolean isValidInput(NonNullList<ItemStack> input);
}
