package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.GasSeparatorBlockEntity;
import xyz.przemyk.real_minerals.datapack.recipes.GasSeparatorRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class GasSeparatorCategory implements IRecipeCategory<GasSeparatorRecipe> {

    public static final TranslatableComponent TITLE = new TranslatableComponent("gui." + RealMinerals.MODID + ".category.gas_separator");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable tankOverlay;
    private final IDrawableAnimated arrow;

    public GasSeparatorCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 0, 147, 102, 50);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MachinesRegistry.GAS_SEPARATOR_BLOCK.ITEM.get()));
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_MOD, 119, 14, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
        tankOverlay = guiHelper.createDrawable(RECIPE_GUI_MOD, 102, 147, 14, 46);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.GAS_SEPARATOR_CATEGORY_ID;
    }

    @Override
    public Class<? extends GasSeparatorRecipe> getRecipeClass() {
        return GasSeparatorRecipe.class;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(GasSeparatorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
        ingredients.setOutput(VanillaTypes.FLUID, recipe.getFluidOutput());
    }

    @Override
    public void draw(GasSeparatorRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        arrow.draw(stack, 24, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GasSeparatorRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 16);
        guiItemStacks.init(1, false, 60, 16);
        guiItemStacks.set(ingredients);

        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
        guiFluidStacks.init(0, false, 86, 2, 14, 46, GasSeparatorBlockEntity.CAPACITY, false, tankOverlay);
        guiFluidStacks.set(ingredients);
        guiFluidStacks.addTooltipCallback(FluidTooltipCallback::onTooltip);
    }
}
