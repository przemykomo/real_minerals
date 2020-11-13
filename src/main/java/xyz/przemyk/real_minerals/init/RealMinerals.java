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
import xyz.przemyk.real_minerals.machines.MachineRecipe;
import xyz.przemyk.real_minerals.machines.alloy_furnace.AlloyRecipe;
import xyz.przemyk.real_minerals.machines.alloy_furnace.AlloyRecipeType;
import xyz.przemyk.real_minerals.machines.crusher.CrusherRecipe;
import xyz.przemyk.real_minerals.machines.crusher.CrusherRecipeType;

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

    private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE_TYPE.toString()), CRUSHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ALLOY_RECIPE_TYPE.toString()), ALLOY_RECIPE_TYPE);
        event.getRegistry().registerAll(CrusherRecipe.SERIALIZER, AlloyRecipe.SERIALIZER);
    }

    @Nullable
    public static MachineRecipe getRecipe(NonNullList<ItemStack> input, World world, IRecipeType<?> recipeType) {
        Set<MachineRecipe> recipes = getAllRecipes(world, recipeType);
        for (MachineRecipe recipe : recipes){
            if (recipe.isValidInput(input)) {
                return recipe;
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