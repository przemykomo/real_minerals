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
import xyz.przemyk.real_minerals.datapack.recipes.AlloyRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class AlloyRecipeCategory implements IRecipeCategory<AlloyRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private static final TranslatableComponent name = new TranslatableComponent("gui." + RealMinerals.MODID + ".category.alloying");
    private final IDrawableAnimated arrow;

    public AlloyRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 0, 0, 119, 54);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MachinesRegistry.ALLOY_FURNACE_BLOCK.ITEM.get()));
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
    public void setIngredients(AlloyRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void draw(AlloyRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 61, 19);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloyRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 9);
        guiItemStacks.init(1, true, 19, 9);
        guiItemStacks.init(2, true, 38, 9);
        guiItemStacks.init(3, true, 0, 27);
        guiItemStacks.init(4, true, 38, 27);

        guiItemStacks.init(5, false, 97, 18);
        guiItemStacks.set(ingredients);
    }
}
