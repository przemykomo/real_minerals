package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.alloy_furnace.AlloyRecipe;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class AlloyRecipeCategory implements IRecipeCategory<AlloyRecipe> {
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;
    private final IDrawableAnimated arrow;

    public AlloyRecipeCategory(IGuiHelper guiHelper) {
        staticFlame = guiHelper.createDrawable(RECIPE_GUI_MOD, 119, 0, 14, 14);
        animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 0, 0, 119, 54);
        icon = guiHelper.createDrawableIngredient(new ItemStack(RealMinerals.ALLOY_FURNACE_BLOCK.ITEM.get()));
        localizedName = I18n.format("gui." + RealMinerals.MODID + ".category.alloying");
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_MOD, 119, 14, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.ALLOY_CATEGORY_ID;
    }

    @Override
    public Class<? extends AlloyRecipe> getRecipeClass() {
        return AlloyRecipe.class;
    }

    @Override
    public String getTitle() {
        return localizedName;
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
    public void setIngredients(AlloyRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void draw(AlloyRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        animatedFlame.draw(matrixStack, 20, 22);
        arrow.draw(matrixStack, 61, 19);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloyRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(1, true, 19, 0);
        guiItemStacks.init(2, true, 38, 0);
        guiItemStacks.init(3, true, 0, 18);
        guiItemStacks.init(4, true, 38, 18);

        guiItemStacks.init(6, false, 97, 18);
        guiItemStacks.set(ingredients);
    }
}
