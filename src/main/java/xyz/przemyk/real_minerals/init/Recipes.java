package xyz.przemyk.real_minerals.init;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import xyz.przemyk.real_minerals.recipes.*;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

public class Recipes {
    public static final RecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = new MachineRecipeType<>("crusher");
    public static final RecipeType<AlloyRecipe> ALLOY_RECIPE_TYPE = new MachineRecipeType<>("alloy");
    public static final RecipeType<MagnetizerRecipe> MAGNETIZER_RECIPE_TYPE = new MachineRecipeType<>("magnetizer");
    public static final RecipeType<MagneticSeparatorRecipe> MAGNETIC_SEPARATOR_RECIPE_TYPE = new MachineRecipeType<>("magnetic_separator");
    public static final RecipeType<GasSeparatorRecipe> GAS_SEPARATOR_RECIPE_TYPE = new MachineRecipeType<>("gas_separator");

    public static void init(IEventBus eventBus) {
        eventBus.addGenericListener(RecipeSerializer.class, Recipes::registerRecipeSerializers);
    }

    private static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE_TYPE.toString()), CRUSHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ALLOY_RECIPE_TYPE.toString()), ALLOY_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIZER_RECIPE_TYPE.toString()), MAGNETIZER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIC_SEPARATOR_RECIPE_TYPE.toString()), MAGNETIC_SEPARATOR_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(GAS_SEPARATOR_RECIPE_TYPE.toString()), GAS_SEPARATOR_RECIPE_TYPE);
        event.getRegistry().registerAll(CrusherRecipe.SERIALIZER, AlloyRecipe.SERIALIZER, MagnetizerRecipe.SERIALIZER, MagneticSeparatorRecipe.SERIALIZER, GasSeparatorRecipe.SERIALIZER);
    }

    @Nullable
    public static<T extends MachineRecipe> T getRecipe(NonNullList<ItemStack> input, Level world, RecipeType<T> recipeType) {
        if (input.size() > 0) {
            Set<T> recipes = getAllRecipes(world, recipeType);
            for (T recipe : recipes){
                if (recipe.isValidInput(input)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T extends Recipe<?>> Set<T> getAllRecipes(Level world, RecipeType<?> recipeType) {
        return ((Set<T>) world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == recipeType).collect(Collectors.toSet()));
    }
}
