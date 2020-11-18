package xyz.przemyk.real_minerals.machines.not_electric;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class MachineRecipe implements IRecipe<IInventory> {

    protected final NonNullList<ItemStack> outputs;
    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> ingredients;

    public MachineRecipe(NonNullList<ItemStack> outputs, ResourceLocation id, NonNullList<Ingredient> ingredients) {
        this.outputs = outputs;
        this.id = id;
        this.ingredients = ingredients;
    }

    @Deprecated // ignored method, use isValidInput
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Deprecated // ignored method, use getRecipeOutput
    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return outputs.get(0).copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override // use getRecipeOutputs for multiple outputs
    public ItemStack getRecipeOutput() {
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
