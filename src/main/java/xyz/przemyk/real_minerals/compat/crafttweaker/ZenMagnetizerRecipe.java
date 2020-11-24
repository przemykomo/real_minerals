package xyz.przemyk.real_minerals.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerRecipe;

@ZenRegister
@ZenCodeType.Name("mods." + RealMinerals.MODID + ".ZenMagnetizerRecipe")
public class ZenMagnetizerRecipe {
    private final MagnetizerRecipe recipeInternal;

    public ZenMagnetizerRecipe(String id, IIngredient input, IItemStack output) {
        this.recipeInternal = new MagnetizerRecipe(ResourceLocation.tryCreate(id), input.asVanillaIngredient(), output.getInternal());
    }

    public ZenMagnetizerRecipe(MagnetizerRecipe recipe) {
        this.recipeInternal = recipe;
    }

    public MagnetizerRecipe getInternal() {
        return recipeInternal;
    }
}
