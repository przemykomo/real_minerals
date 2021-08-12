package xyz.przemyk.real_minerals.compat.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.recipes.AlloyRecipe;

@ZenRegister
@ZenCodeType.Name("mods." + RealMinerals.MODID + ".Alloys")
public class Alloys implements IRecipeManager {

    public Alloys() {}

    @ZenCodeType.Method
    public ZenAlloyRecipe create(String id, IIngredient[] inputs, IItemStack output) {
        final ZenAlloyRecipe recipe = new ZenAlloyRecipe(id, inputs, output);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe.getInternal(), ""));
        return recipe;
    }

    @ZenCodeType.Method
    public ZenAlloyRecipe getRecipe(String id) {
        final IRecipe<?> recipe = getRecipes().get(ResourceLocation.tryCreate(id));

        if (recipe instanceof AlloyRecipe) {
            return new ZenAlloyRecipe((AlloyRecipe) recipe);
        }

        throw new IllegalStateException("Invalid alloy recipe ID: " + id);
    }

    @Override
    public ResourceLocation getBracketResourceLocation() {
        return AlloyRecipe.SERIALIZER.getRegistryName();
    }

    @Override
    public IRecipeType<?> getRecipeType() {
        return RealMinerals.ALLOY_RECIPE_TYPE;
    }
}
