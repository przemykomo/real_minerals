package xyz.przemyk.real_minerals.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.crusher.CrusherContainer;
import xyz.przemyk.real_minerals.machines.crusher.CrusherScreen;
import xyz.przemyk.real_minerals.machines.crusher.CrusherTileEntity;

@JeiPlugin
public class RealMineralsJEIPlugin implements IModPlugin {

    public static final ResourceLocation CRUSHER_CATEGORY_ID = new ResourceLocation(RealMinerals.MODID, "crushing");

    public RealMineralsJEIPlugin() {

    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(RealMinerals.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CrusherTileEntity.findRecipeByType(RealMinerals.CRUSHER_RECIPE_TYPE, Minecraft.getInstance().world), CRUSHER_CATEGORY_ID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CrusherContainer.class, CRUSHER_CATEGORY_ID, 0, 1, 3, 36);
        registration.addRecipeTransferHandler(CrusherContainer.class, VanillaRecipeCategoryUid.FUEL, 1, 1, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(RealMinerals.CRUSHER_BLOCK.ITEM.get()), CRUSHER_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrusherScreen.class, 78, 32, 28, 23, CRUSHER_CATEGORY_ID, VanillaRecipeCategoryUid.FUEL);
    }
}
