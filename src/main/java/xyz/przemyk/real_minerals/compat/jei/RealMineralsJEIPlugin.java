package xyz.przemyk.real_minerals.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorScreen;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerContainer;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerScreen;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceScreen;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherContainer;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherScreen;

@JeiPlugin
public class RealMineralsJEIPlugin implements IModPlugin {
    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");
    public static final ResourceLocation RECIPE_GUI_MOD = new ResourceLocation(RealMinerals.MODID, "textures/gui/jei_gui.png");

    public static final ResourceLocation CRUSHER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "crushing");
    public static final ResourceLocation ALLOY_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "alloying");
    public static final ResourceLocation MAGNETIZER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "magnetizer");
    public static final ResourceLocation MAGNETIC_SEPARATOR_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "magnetic_separator");

    public RealMineralsJEIPlugin() {

    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealMinerals.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new CrusherRecipeCategory(guiHelper),
                new AlloyRecipeCategory(guiHelper),
                new MagnetizerRecipeCategory(guiHelper),
                new MagneticSeparatorRecipeCategory(guiHelper)
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        World world = Minecraft.getInstance().world;
        registration.addRecipes(RealMinerals.getAllRecipes(world, RealMinerals.CRUSHER_RECIPE_TYPE), CRUSHER_CATEGORY_ID);
        registration.addRecipes(RealMinerals.getAllRecipes(world, RealMinerals.ALLOY_RECIPE_TYPE), ALLOY_CATEGORY_ID);
        registration.addRecipes(RealMinerals.getAllRecipes(world, RealMinerals.MAGNETIZER_RECIPE_TYPE), MAGNETIZER_CATEGORY_ID);
        registration.addRecipes(RealMinerals.getAllRecipes(world, RealMinerals.MAGNETIC_SEPARATOR_RECIPE_TYPE), MAGNETIC_SEPARATOR_CATEGORY_ID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CrusherContainer.class, CRUSHER_CATEGORY_ID, 0, 1, 3, 36);
        registration.addRecipeTransferHandler(CrusherContainer.class, VanillaRecipeCategoryUid.FUEL, 1, 1, 3, 36);

        registration.addRecipeTransferHandler(AlloyFurnaceContainer.class, ALLOY_CATEGORY_ID, 0, 5, 7, 36);
        registration.addRecipeTransferHandler(AlloyFurnaceContainer.class, VanillaRecipeCategoryUid.FUEL, 1, 1, 7, 36);

        registration.addRecipeTransferHandler(MagnetizerContainer.class, MAGNETIZER_CATEGORY_ID, 0, 1, 2, 36);

        registration.addRecipeTransferHandler(MagneticSeparatorContainer.class, MAGNETIC_SEPARATOR_CATEGORY_ID, 0, 1, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Registering.CRUSHER_BLOCK.ITEM.get()), CRUSHER_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(Registering.ALLOY_FURNACE_BLOCK.ITEM.get()), ALLOY_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(Registering.MAGNETIZER_BLOCK.ITEM.get()), MAGNETIZER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(Registering.MAGNETIC_SEPARATOR_BLOCK.ITEM.get()), MAGNETIC_SEPARATOR_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(Registering.ELECTRIC_FURNACE_BLOCK.ITEM.get()), VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(Registering.BURNING_GENERATOR_BLOCK.ITEM.get()), VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrusherScreen.class, 78, 32, 28, 23, CRUSHER_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeClickArea(AlloyFurnaceScreen.class, 78, 32, 28, 23, ALLOY_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeClickArea(MagnetizerScreen.class, 78, 32, 28, 23, MAGNETIZER_CATEGORY_ID);
        registration.addRecipeClickArea(MagneticSeparatorScreen.class, 65, 26, 33, 34, MAGNETIC_SEPARATOR_CATEGORY_ID);
    }
}
