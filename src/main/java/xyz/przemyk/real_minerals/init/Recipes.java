package xyz.przemyk.real_minerals.init;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.datapack.recipes.*;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Recipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RealMinerals.MODID);

    public static final RecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = new MachineRecipeType<>("crusher");
    public static final RegistryObject<CrusherRecipe.Serializer> CRUSHER_SERIALIZER = RECIPE_SERIALIZERS.register("crusher", CrusherRecipe.Serializer::new);

    public static final RecipeType<AlloyRecipe> ALLOY_RECIPE_TYPE = new MachineRecipeType<>("alloy");
    public static final RegistryObject<AlloyRecipe.Serializer> ALLOY_SERIALIZER = RECIPE_SERIALIZERS.register("alloy", AlloyRecipe.Serializer::new);

    public static final RecipeType<MagnetizerRecipe> MAGNETIZER_RECIPE_TYPE = new MachineRecipeType<>("magnetizer");
    public static final RegistryObject<MagnetizerRecipe.Serializer> MAGNETIZER_SERIALIZER = RECIPE_SERIALIZERS.register("magnetizer", MagnetizerRecipe.Serializer::new);

    public static final RecipeType<MagneticSeparatorRecipe> MAGNETIC_SEPARATOR_RECIPE_TYPE = new MachineRecipeType<>("magnetic_separator");
    public static final RegistryObject<MagneticSeparatorRecipe.Serializer> MAGNETIC_SEPARATOR_SERIALIZER = RECIPE_SERIALIZERS.register("magnetic_separator", MagneticSeparatorRecipe.Serializer::new);

    public static final RecipeType<GasSeparatorRecipe> GAS_SEPARATOR_RECIPE_TYPE = new MachineRecipeType<>("gas_separator");
    public static final RegistryObject<GasSeparatorRecipe.Serializer> GAS_SEPARATOR_SERIALIZER = RECIPE_SERIALIZERS.register("gas_separator", GasSeparatorRecipe.Serializer::new);

    public static final RecipeType<GasEnricherRecipe> GAS_ENRICHER_RECIPE_TYPE = new MachineRecipeType<>("gas_enricher");
    public static final RegistryObject<GasEnricherRecipe.Serializer> GAS_ENRICHER_SERIALIZER = RECIPE_SERIALIZERS.register("gas_enricher", GasEnricherRecipe.Serializer::new);

    public static final RecipeType<OxidizerRecipe> OXIDIZER_RECIPE_TYPE = new MachineRecipeType<>("oxidizer");
    public static final RegistryObject<OxidizerRecipe.Serializer> OXIDIZER_SERIALIZER = RECIPE_SERIALIZERS.register("oxidizer", OxidizerRecipe.Serializer::new);

    public static void init(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        eventBus.addListener(Recipes::commonSetup);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Recipes::registerRecipeTypes);
    }

    private static void registerRecipeTypes() {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE_TYPE.toString()), CRUSHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ALLOY_RECIPE_TYPE.toString()), ALLOY_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIZER_RECIPE_TYPE.toString()), MAGNETIZER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIC_SEPARATOR_RECIPE_TYPE.toString()), MAGNETIC_SEPARATOR_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(GAS_SEPARATOR_RECIPE_TYPE.toString()), GAS_SEPARATOR_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(GAS_ENRICHER_RECIPE_TYPE.toString()), GAS_ENRICHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(OXIDIZER_RECIPE_TYPE.toString()), OXIDIZER_RECIPE_TYPE);
    }

    @Nullable
    public static<T extends ItemMachineRecipe> T getRecipe(NonNullList<ItemStack> input, Level world, RecipeType<T> recipeType) {
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

    public static FluidStack readFluidStack(JsonObject jsonObject) {
        return new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()))), jsonObject.get("amount").getAsInt());
    }
}
