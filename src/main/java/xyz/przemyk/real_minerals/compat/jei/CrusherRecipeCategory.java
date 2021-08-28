package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.datapack.recipes.CrusherRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import java.util.List;

public class CrusherRecipeCategory implements IRecipeCategory<CrusherRecipe> {

    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, "textures/gui/gui_vanilla.png");

    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    private final IDrawable background;
    private final IDrawable icon;
    private static final TranslatableComponent name = new TranslatableComponent("gui." + RealMinerals.MODID + ".category.crushing");
    private final IDrawableAnimated arrow;

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        staticFlame = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 82, 114, 14, 14);
        animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 114, 82, 54);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MachinesRegistry.CRUSHER_BLOCK.ITEM.get()));
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 82, 128, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.CRUSHER_CATEGORY_ID;
    }

    @Override
    public Class<? extends CrusherRecipe> getRecipeClass() {
        return CrusherRecipe.class;
    }

    @Override
    public Component getTitle() {
        return name;
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
    public void setIngredients(CrusherRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(List.of(recipe.input()));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.output());
    }

    @Override
    public void draw(CrusherRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        animatedFlame.draw(matrixStack, 1, 20);
        arrow.draw(matrixStack, 24, 18);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrusherRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(1, false, 60, 18);
        guiItemStacks.set(ingredients);
    }
}
