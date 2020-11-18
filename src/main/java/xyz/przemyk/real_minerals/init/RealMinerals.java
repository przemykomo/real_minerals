package xyz.przemyk.real_minerals.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorRecipe;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorRecipeType;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerRecipe;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerRecipeType;
import xyz.przemyk.real_minerals.machines.not_electric.MachineRecipe;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyRecipe;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyRecipeType;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherRecipe;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherRecipeType;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Mod(RealMinerals.MODID)
public class RealMinerals {
    public static final String MODID = "real_minerals";

    public RealMinerals() {
        Registering.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);
    }

    public static final IRecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = new CrusherRecipeType();
    public static final IRecipeType<AlloyRecipe> ALLOY_RECIPE_TYPE = new AlloyRecipeType();
    public static final IRecipeType<MagnetizerRecipe> MAGNETIZER_RECIPE_TYPE = new MagnetizerRecipeType();
    public static final IRecipeType<MagneticSeparatorRecipe> MAGNETIC_SEPARATOR_RECIPE_TYPE = new MagneticSeparatorRecipeType();

    private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE_TYPE.toString()), CRUSHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ALLOY_RECIPE_TYPE.toString()), ALLOY_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIZER_RECIPE_TYPE.toString()), MAGNETIZER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MAGNETIC_SEPARATOR_RECIPE_TYPE.toString()), MAGNETIC_SEPARATOR_RECIPE_TYPE);
        event.getRegistry().registerAll(CrusherRecipe.SERIALIZER, AlloyRecipe.SERIALIZER, MagnetizerRecipe.SERIALIZER, MagneticSeparatorRecipe.SERIALIZER);
    }

    @Nullable
    public static<T extends MachineRecipe> T getRecipe(NonNullList<ItemStack> input, World world, IRecipeType<T> recipeType) {
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
    public static<T extends IRecipe<?>> Set<T> getAllRecipes(World world, IRecipeType<?> recipeType) {
        return ((Set<T>) world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == recipeType).collect(Collectors.toSet()));
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Registering.CRUSHER_BLOCK.ITEM.get());
        }
    };


}