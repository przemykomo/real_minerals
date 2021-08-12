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
//import xyz.przemyk.real_minerals.recipes.MagnetizerRecipe;
//
//@ZenRegister
//@ZenCodeType.Name("mods." + RealMinerals.MODID + ".Magnetizer")
//public class Magnetizer implements IRecipeManager {
//
//    public Magnetizer() {}
//
//    @ZenCodeType.Method
//    public ZenMagnetizerRecipe create(String id, IIngredient input, IItemStack output) {
//        final ZenMagnetizerRecipe recipe = new ZenMagnetizerRecipe(id, input, output);
//        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe.getInternal(), ""));
//        return recipe;
//    }
//
//    @ZenCodeType.Method
//    public ZenMagnetizerRecipe getRecipe(String id) {
//        final IRecipe<?> recipe = getRecipes().get(ResourceLocation.tryParse(id));
//
//        if (recipe instanceof MagnetizerRecipe) {
//            return new ZenMagnetizerRecipe((MagnetizerRecipe) recipe);
//        }
//
//        throw new IllegalStateException("Invalid magnetizer recipe ID: " + id);
//    }
//
//    @Override
//    public ResourceLocation getBracketResourceLocation() {
//        return MagnetizerRecipe.SERIALIZER.getRegistryName();
//    }
//
//    @Override
//    public IRecipeType<?> getRecipeType() {
//        return RealMinerals.MAGNETIZER_RECIPE_TYPE;
//    }
//}
