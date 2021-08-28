package xyz.przemyk.real_minerals.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidAttributes;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.datapack.recipes.MixerRecipe;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

import java.util.List;

import static xyz.przemyk.real_minerals.compat.jei.RealMineralsJEIPlugin.RECIPE_GUI_MOD;

public class MixerCategory implements IRecipeCategory<MixerRecipe> {

    public static final TranslatableComponent TITLE = new TranslatableComponent("gui." + RealMinerals.MODID + ".category.mixer");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable tankOverlay;
    private final IDrawableAnimated arrow;

    public MixerCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RECIPE_GUI_MOD, 162, 0, 94, 50);
        icon = guiHelper.createDrawableIngredient(new ItemStack(MachinesRegistry.MIXER_BLOCK.ITEM.get()));
        arrow = guiHelper.drawableBuilder(RECIPE_GUI_MOD, 119, 14, 24, 17).buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
        tankOverlay = guiHelper.createDrawable(RECIPE_GUI_MOD, 102, 147, 14, 46);
    }

    @Override
    public ResourceLocation getUid() {
        return RealMineralsJEIPlugin.MIXER_CATEGORY_ID;
    }

    @Override
    public Class<? extends MixerRecipe> getRecipeClass() {
        return MixerRecipe.class;
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
    public void setIngredients(MixerRecipe recipe, IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.FLUID, List.of(recipe.firstInput(), recipe.secondInput()));
        ingredients.setOutput(VanillaTypes.FLUID, recipe.output());
    }

    @Override
    public void draw(MixerRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        arrow.draw(stack, 44, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MixerRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
        guiFluidStacks.init(0, true, 2, 2, 14, 46, FluidAttributes.BUCKET_VOLUME, false, tankOverlay);
        guiFluidStacks.init(1, true, 22, 2, 14, 46, FluidAttributes.BUCKET_VOLUME, false, tankOverlay);
        guiFluidStacks.init(2, false, 78, 2, 14, 46, FluidAttributes.BUCKET_VOLUME, false, tankOverlay);
        guiFluidStacks.set(ingredients);
        guiFluidStacks.addTooltipCallback(FluidTooltipCallback::onTooltip);
    }
}
