package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.datapack.recipes.MagnetizerRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class MagnetizerRecipeCategory implements IRecipeCategory<MagnetizerRecipe> {

    public static final TranslatableComponent TITLE = new TranslatableComponent("gui." + RealMinerals.MODID + ".category.magnetizer");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;

    public MagnetizerRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 0, 55, 82, 38);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MachinesRegistry.MAGNETIZER_BLOCK.ITEM.get()));
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_MOD, 119, 14, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.MAGNETIZER_CATEGORY_ID;
    }

    @Override
    public Class<? extends MagnetizerRecipe> getRecipeClass() {
        return MagnetizerRecipe.class;
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
    public void setIngredients(MagnetizerRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void draw(MagnetizerRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 24, 4);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MagnetizerRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 4);
        guiItemStacks.init(1, false, 60, 4);
        guiItemStacks.set(ingredients);
    }
}
