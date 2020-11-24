package xyz.przemyk.real_minerals.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyRecipe;

@ZenRegister
@ZenCodeType.Name("mods." + RealMinerals.MODID + ".ZenAlloyRecipe")
public class ZenAlloyRecipe {
    private final AlloyRecipe recipeInternal;

    public ZenAlloyRecipe(String id, IIngredient[] ingredients, IItemStack output) {
        NonNullList<Ingredient> inputs = NonNullList.create();
        for (IIngredient ingredient : ingredients) {
            inputs.add(ingredient.asVanillaIngredient());
        }
        this.recipeInternal = new AlloyRecipe(output.getInternal(), ResourceLocation.tryCreate(id), inputs);
    }

    public ZenAlloyRecipe(AlloyRecipe recipe) {
        this.recipeInternal = recipe;
    }

    public AlloyRecipe getInternal() {
        return recipeInternal;
    }
}
