package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.recipes.MagneticSeparatorRecipe;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class MagneticSeparatorRecipeCategory implements IRecipeCategory<MagneticSeparatorRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;
    private final IDrawableAnimated arrow;

    public MagneticSeparatorRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 0, 93, 88, 54);
        icon = guiHelper.createDrawableIngredient(new ItemStack(Registering.MAGNETIC_SEPARATOR_BLOCK.ITEM.get()));
        localizedName = I18n.format("gui." + RealMinerals.MODID + ".category.magnetic_separator");
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_MOD, 88, 93, 31, 33).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.MAGNETIC_SEPARATOR_CATEGORY_ID;
    }

    @Override
    public Class<? extends MagneticSeparatorRecipe> getRecipeClass() {
        return MagneticSeparatorRecipe.class;
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
    public void setIngredients(MagneticSeparatorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getRecipeOutputs());
    }

    @Override
    public void draw(MagneticSeparatorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 22, 10);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MagneticSeparatorRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiIemStacks = recipeLayout.getItemStacks();
        guiIemStacks.init(0, true, 0, 10);
        guiIemStacks.init(1, false, 66, 4);
        guiIemStacks.init(2, false, 66, 32);
        guiIemStacks.set(ingredients);
    }
}
