package xyz.przemyk.real_minerals.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.recipes.CrusherRecipe;

@ZenRegister
@ZenCodeType.Name("mods." + RealMinerals.MODID + ".ZenCrusherRecipe")
public class ZenCrusherRecipe {
    private final CrusherRecipe recipeInternal;

    public ZenCrusherRecipe(String id, IIngredient input, IItemStack output) {
        this.recipeInternal = new CrusherRecipe(ResourceLocation.tryCreate(id), input.asVanillaIngredient(), output.getInternal());
    }

    public ZenCrusherRecipe(CrusherRecipe recipe) {
        this.recipeInternal = recipe;
    }

    public CrusherRecipe getInternal() {
        return recipeInternal;
    }
}
