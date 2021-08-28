package xyz.przemyk.real_minerals.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.containers.*;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.screen.*;

@JeiPlugin
public class RealMineralsJEIPlugin implements IModPlugin {
    public static final ResourceLocation RECIPE_GUI_MOD = new ResourceLocation(RealMinerals.MODID, "textures/gui/jei_gui.png");

    public static final ResourceLocation CRUSHER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "crushing");
    public static final ResourceLocation ALLOY_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "alloying");
    public static final ResourceLocation MAGNETIZER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "magnetizer");
    public static final ResourceLocation MAGNETIC_SEPARATOR_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "magnetic_separator");
    public static final ResourceLocation GAS_SEPARATOR_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "gas_separator");
    public static final ResourceLocation GAS_ENRICHER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "gas_enricher");
    public static final ResourceLocation MIXER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "mixer");
    public static final ResourceLocation CHEMICAL_WASHER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "chemical_washer");
    public static final ResourceLocation OXIDIZER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "oxidizer");

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
                new MagneticSeparatorRecipeCategory(guiHelper),
                new GasSeparatorCategory(guiHelper),
                new GasEnricherCategory(guiHelper),
                new MixerCategory(guiHelper),
                new OxidizerCategory(guiHelper),
                new ChemicalWasherCategory(guiHelper)
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.CRUSHER_RECIPE_TYPE), CRUSHER_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.ALLOY_RECIPE_TYPE), ALLOY_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.MAGNETIZER_RECIPE_TYPE), MAGNETIZER_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.MAGNETIC_SEPARATOR_RECIPE_TYPE), MAGNETIC_SEPARATOR_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.GAS_SEPARATOR_RECIPE_TYPE), GAS_SEPARATOR_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.GAS_ENRICHER_RECIPE_TYPE), GAS_ENRICHER_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.MIXER_RECIPE_TYPE), MIXER_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.OXIDIZER_RECIPE_TYPE), OXIDIZER_CATEGORY_ID);
        registration.addRecipes(Recipes.getAllRecipes(level, Recipes.CHEMICAL_WASHER_RECIPE_TYPE), CHEMICAL_WASHER_CATEGORY_ID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CrusherContainer.class, CRUSHER_CATEGORY_ID, 0, 1, 2, 36);
        registration.addRecipeTransferHandler(AlloyFurnaceContainer.class, ALLOY_CATEGORY_ID, 0, 5, 6, 36);
        registration.addRecipeTransferHandler(MagnetizerContainer.class, MAGNETIZER_CATEGORY_ID, 0, 1, 2, 36);
        registration.addRecipeTransferHandler(MagneticSeparatorContainer.class, MAGNETIC_SEPARATOR_CATEGORY_ID, 0, 1, 3, 36);
        registration.addRecipeTransferHandler(GasSeparatorContainer.class, GAS_SEPARATOR_CATEGORY_ID, 0, 1, 2, 36);
        registration.addRecipeTransferHandler(OxidizerContainer.class, OXIDIZER_CATEGORY_ID, 0, 1, 1, 36);
        registration.addRecipeTransferHandler(ChemicalWasherContainer.class, CHEMICAL_WASHER_CATEGORY_ID, 0, 1, 1, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.CRUSHER_BLOCK.ITEM.get()), CRUSHER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.ALLOY_FURNACE_BLOCK.ITEM.get()), ALLOY_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.MAGNETIZER_BLOCK.ITEM.get()), MAGNETIZER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.MAGNETIC_SEPARATOR_BLOCK.ITEM.get()), MAGNETIC_SEPARATOR_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.ELECTRIC_FURNACE_BLOCK.ITEM.get()), VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.BURNING_GENERATOR_BLOCK.ITEM.get()), VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.GAS_SEPARATOR_BLOCK.ITEM.get()), GAS_SEPARATOR_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.GAS_ENRICHER_BLOCK.ITEM.get()), GAS_ENRICHER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.MIXER_BLOCK.ITEM.get()), MIXER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.OXIDIZER_BLOCK.ITEM.get()), OXIDIZER_CATEGORY_ID);
        registration.addRecipeCatalyst(new ItemStack(MachinesRegistry.CHEMICAL_WASHER_BLOCK.ITEM.get()), CHEMICAL_WASHER_CATEGORY_ID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrusherScreen.class, 78, 32, 28, 23, CRUSHER_CATEGORY_ID);
        registration.addRecipeClickArea(ElectricFurnaceScreen.class, 78, 32, 28, 23, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeClickArea(AlloyFurnaceScreen.class, 78, 32, 28, 23, ALLOY_CATEGORY_ID);
        registration.addRecipeClickArea(MagnetizerScreen.class, 78, 32, 28, 23, MAGNETIZER_CATEGORY_ID);
        registration.addRecipeClickArea(MagneticSeparatorScreen.class, 65, 26, 33, 34, MAGNETIC_SEPARATOR_CATEGORY_ID);
        registration.addRecipeClickArea(GasSeparatorScreen.class, 78, 32, 28, 23, GAS_SEPARATOR_CATEGORY_ID);
        registration.addRecipeClickArea(GasEnricherScreen.class, 78, 32, 28, 23, GAS_ENRICHER_CATEGORY_ID);
        registration.addRecipeClickArea(MixerScreen.class, 78, 32, 28, 23, MIXER_CATEGORY_ID);
        registration.addRecipeClickArea(OxidizerScreen.class, 78, 32, 28, 23, OXIDIZER_CATEGORY_ID);
        registration.addRecipeClickArea(ChemicalWasherScreen.class, 78, 32, 28, 23, CHEMICAL_WASHER_CATEGORY_ID);
    }
}
