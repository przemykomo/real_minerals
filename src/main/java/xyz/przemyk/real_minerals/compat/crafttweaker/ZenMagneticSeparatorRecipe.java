package xyz.przemyk.real_minerals.compat.crafttweaker;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorRecipe;

@ZenRegister
@ZenCodeType.Name("mods." + RealMinerals.MODID + ".ZenMagneticSeparatorRecipe")
public class ZenMagneticSeparatorRecipe {
    private final MagneticSeparatorRecipe recipeInternal;

    public ZenMagneticSeparatorRecipe(String id, IIngredient input, IItemStack output, IItemStack secondaryOutput) {
        NonNullList<ItemStack> outputs = NonNullList.create();
        outputs.add(output.getInternal());
        outputs.add(secondaryOutput.getInternal());
        this.recipeInternal = new MagneticSeparatorRecipe(ResourceLocation.tryCreate(id), input.asVanillaIngredient(), outputs);
    }

    public ZenMagneticSeparatorRecipe(MagneticSeparatorRecipe recipe) {
        this.recipeInternal = recipe;
    }

    public MagneticSeparatorRecipe getInternal() {
        return recipeInternal;
    }
}
