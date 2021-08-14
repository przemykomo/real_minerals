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
//import xyz.przemyk.real_minerals.datapack.recipes.CrusherRecipe;
//
//@ZenRegister
//@ZenCodeType.Name("mods." + RealMinerals.MODID + ".Crusher")
//public class Crusher implements IRecipeManager {
//
//    public Crusher() {}
//
//    @ZenCodeType.Method
//    public ZenCrusherRecipe create(String id, IIngredient input, IItemStack output) {
//        final ZenCrusherRecipe recipe = new ZenCrusherRecipe(id, input, output);
//        CraftTweakerAPI.apply(new ActionAddRecipe(this, recipe.getInternal(), ""));
//        return recipe;
//    }
//
//    @ZenCodeType.Method
//    public ZenCrusherRecipe getRecipe(String id) {
//        final IRecipe<?> recipe = getRecipes().get(ResourceLocation.tryParse(id));
//
//        if (recipe instanceof CrusherRecipe) {
//            return new ZenCrusherRecipe((CrusherRecipe) recipe);
//        }
//
//        throw new IllegalStateException("Invalid crusher recipe ID: " + id);
//    }
//
//    @Override
//    public ResourceLocation getBracketResourceLocation() {
//        return CrusherRecipe.SERIALIZER.getRegistryName();
//    }
//
//    @Override
//    public IRecipeType<?> getRecipeType() {
//        return RealMinerals.CRUSHER_RECIPE_TYPE;
//    }
//}
