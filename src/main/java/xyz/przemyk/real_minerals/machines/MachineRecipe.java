package xyz.przemyk.real_minerals.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

//TODO
public abstract class MachineRecipe implements IRecipe<IInventory> {

    protected final ItemStack output;
    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> ingredients;

    @SuppressWarnings("ConstantConditions")
    public MachineRecipe(ItemStack output, ResourceLocation id, NonNullList<Ingredient> ingredients) {
        this.output = output;
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
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public abstract boolean isValidInput(NonNullList<ItemStack> input);
}
