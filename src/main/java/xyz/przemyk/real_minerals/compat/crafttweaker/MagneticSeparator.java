//package xyz.przemyk.real_minerals.compat.crafttweaker;
//
//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.annotations.ZenRegister;
//import com.blamejared.crafttweaker.api.item.IIngredient;
//import com.blamejared.crafttweaker.api.item.IItemStack;
//import com.blamejared.crafttweaker.api.managers.IRecipeManager;
//import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.IRecipeType;
//import net.minecraft.util.ResourceLocation;
//import org.openzen.zencode.java.ZenCodeType;
//import xyz.przemyk.real_minerals.RealMinerals;
//import xyz.przemyk.real_minerals.recipes.MagneticSeparatorRecipe;
//
//@ZenRegister
//@ZenCodeType.Name("mods." + RealMinerals.MODID + ".MagneticSeparator")
//public class MagneticSeparator implements IRecipeManager {
//
//    public MagneticSeparator() {}
//
//    @ZenCodeType.Method
//    public ZenMagneticSeparatorRecipe create(String id, IIngredient input, IItemStack output, IItemStack secondaryOutput) {
//        final ZenMagneticSeparatorRecipe recipe = new ZenMagneticSeparatorRecipe(id, input, output, secondaryOutput);
//        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe.getInternal(), ""));
//        return recipe;
//    }
//
//    @ZenCodeType.Method
//    public ZenMagneticSeparatorRecipe getRecipe(String id) {
//        final IRecipe<?> recipe = getRecipes().get(ResourceLocation.tryParse(id));
//
//        if (recipe instanceof MagneticSeparatorRecipe) {
//            return new ZenMagneticSeparatorRecipe((MagneticSeparatorRecipe) recipe);
//        }
//
//        throw new IllegalStateException("Invalid magnetic separator recipe ID: " + id);
//    }
//
//    @Override
//    public ResourceLocation getBracketResourceLocation() {
//        return MagneticSeparatorRecipe.SERIALIZER.getRegistryName();
//    }
//
//    @Override
//    public IRecipeType<?> getRecipeType() {
//        return RealMinerals.MAGNETIC_SEPARATOR_RECIPE_TYPE;
//    }
//}
