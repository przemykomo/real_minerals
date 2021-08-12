package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import xyz.przemyk.real_minerals.datagen.recipe_builders.AlloyRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.MagneticSeparatorRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.SingleInputOutputRecipeBuilder;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.function.Consumer;

import static xyz.przemyk.real_minerals.RealMinerals.MODID;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerBaseRecipes(consumer);
        registerAlloyRecipes(consumer);
        registerMagnetizerRecipes(consumer);
        registerMagneticSeparatorRecipes(consumer);
    }

    private void registerBaseRecipes(Consumer<IFinishedRecipe> consumer) {
        ////////////////////////////////////////////////////////////////// METALS WITH STONE ORES
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_COPPER), Registering.COPPER_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "copper_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_COPPER), Registering.COPPER_DUST.get()).build(consumer, new ResourceLocation(MODID, "copper_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 100).addCriterion("has_copper_ore", hasItem(ItemTags.ORES_COPPER)).build(consumer,   new ResourceLocation(MODID, "copper_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 200).addCriterion("has_copper_ore", hasItem(ItemTags.ORES_COPPER)).build(consumer,   new ResourceLocation(MODID, "copper_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 100).addCriterion("has_copper_dust", hasItem(ItemTags.DUSTS_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 200).addCriterion("has_copper_dust", hasItem(ItemTags.DUSTS_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.COPPER_INGOT.get()).key('#', ItemTags.NUGGETS_COPPER).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_copper_nugget", hasItem(ItemTags.NUGGETS_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.COPPER_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_COPPER).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_copper_ingot", hasItem(ItemTags.INGOTS_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.COPPER_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_COPPER).addCriterion("has_copper_block", hasItem(ItemTags.STORAGE_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.COPPER_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_COPPER).addCriterion("has_copper_ingot", hasItem(ItemTags.INGOTS_COPPER)).build(consumer, new ResourceLocation(MODID, "copper_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_LEAD), Registering.LEAD_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "lead_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_LEAD), Registering.LEAD_DUST.get()).build(consumer, new ResourceLocation(MODID, "lead_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 100).addCriterion("has_lead_ore", hasItem(ItemTags.ORES_LEAD)).build(consumer,   new ResourceLocation(MODID, "lead_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 200).addCriterion("has_lead_ore", hasItem(ItemTags.ORES_LEAD)).build(consumer,   new ResourceLocation(MODID, "lead_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 100).addCriterion("has_lead_dust", hasItem(ItemTags.DUSTS_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 200).addCriterion("has_lead_dust", hasItem(ItemTags.DUSTS_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.LEAD_INGOT.get()).key('#', ItemTags.NUGGETS_LEAD).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_lead_nugget", hasItem(ItemTags.NUGGETS_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.LEAD_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_LEAD).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_lead_ingot", hasItem(ItemTags.INGOTS_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.LEAD_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_LEAD).addCriterion("has_lead_block", hasItem(ItemTags.STORAGE_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.LEAD_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_LEAD).addCriterion("has_lead_ingot", hasItem(ItemTags.INGOTS_LEAD)).build(consumer, new ResourceLocation(MODID, "lead_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "magnesium_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_MAGNESIUM), Registering.MAGNESIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "magnesium_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 100).addCriterion("has_magnesium_ore", hasItem(ItemTags.ORES_MAGNESIUM)).build(consumer,   new ResourceLocation(MODID, "magnesium_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 200).addCriterion("has_magnesium_ore", hasItem(ItemTags.ORES_MAGNESIUM)).build(consumer,   new ResourceLocation(MODID, "magnesium_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 100).addCriterion("has_magnesium_dust", hasItem(ItemTags.DUSTS_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 200).addCriterion("has_magnesium_dust", hasItem(ItemTags.DUSTS_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.MAGNESIUM_INGOT.get()).key('#', ItemTags.NUGGETS_MAGNESIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_magnesium_nugget", hasItem(ItemTags.NUGGETS_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.MAGNESIUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_MAGNESIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_magnesium_ingot", hasItem(ItemTags.INGOTS_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.MAGNESIUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_MAGNESIUM).addCriterion("has_magnesium_block", hasItem(ItemTags.STORAGE_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.MAGNESIUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_MAGNESIUM).addCriterion("has_magnesium_ingot", hasItem(ItemTags.INGOTS_MAGNESIUM)).build(consumer, new ResourceLocation(MODID, "magnesium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_NICKEL), Registering.NICKEL_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "nickel_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_NICKEL), Registering.NICKEL_DUST.get()).build(consumer, new ResourceLocation(MODID, "nickel_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 100).addCriterion("has_nickel_ore", hasItem(ItemTags.ORES_NICKEL)).build(consumer,   new ResourceLocation(MODID, "nickel_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 200).addCriterion("has_nickel_ore", hasItem(ItemTags.ORES_NICKEL)).build(consumer,   new ResourceLocation(MODID, "nickel_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 100).addCriterion("has_nickel_dust", hasItem(ItemTags.DUSTS_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 200).addCriterion("has_nickel_dust", hasItem(ItemTags.DUSTS_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.NICKEL_INGOT.get()).key('#', ItemTags.NUGGETS_NICKEL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_nickel_nugget", hasItem(ItemTags.NUGGETS_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.NICKEL_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_NICKEL).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_nickel_ingot", hasItem(ItemTags.INGOTS_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.NICKEL_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_NICKEL).addCriterion("has_nickel_block", hasItem(ItemTags.STORAGE_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.NICKEL_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_NICKEL).addCriterion("has_nickel_ingot", hasItem(ItemTags.INGOTS_NICKEL)).build(consumer, new ResourceLocation(MODID, "nickel_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_PLATINUM), Registering.PLATINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_PLATINUM), Registering.PLATINUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 100).addCriterion("has_platinum_ore", hasItem(ItemTags.ORES_PLATINUM)).build(consumer,   new ResourceLocation(MODID, "platinum_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 200).addCriterion("has_platinum_ore", hasItem(ItemTags.ORES_PLATINUM)).build(consumer,   new ResourceLocation(MODID, "platinum_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 100).addCriterion("has_platinum_dust", hasItem(ItemTags.DUSTS_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 200).addCriterion("has_platinum_dust", hasItem(ItemTags.DUSTS_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.PLATINUM_INGOT.get()).key('#', ItemTags.NUGGETS_PLATINUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_platinum_nugget", hasItem(ItemTags.NUGGETS_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.PLATINUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_PLATINUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_platinum_ingot", hasItem(ItemTags.INGOTS_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.PLATINUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_PLATINUM).addCriterion("has_platinum_block", hasItem(ItemTags.STORAGE_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.PLATINUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_PLATINUM).addCriterion("has_platinum_ingot", hasItem(ItemTags.INGOTS_PLATINUM)).build(consumer, new ResourceLocation(MODID, "platinum_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_SILVER), Registering.SILVER_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "silver_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_SILVER), Registering.SILVER_DUST.get()).build(consumer, new ResourceLocation(MODID, "silver_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 100).addCriterion("has_silver_ore", hasItem(ItemTags.ORES_SILVER)).build(consumer,   new ResourceLocation(MODID, "silver_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 200).addCriterion("has_silver_ore", hasItem(ItemTags.ORES_SILVER)).build(consumer,   new ResourceLocation(MODID, "silver_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 100).addCriterion("has_silver_dust", hasItem(ItemTags.DUSTS_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 200).addCriterion("has_silver_dust", hasItem(ItemTags.DUSTS_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.SILVER_INGOT.get()).key('#', ItemTags.NUGGETS_SILVER).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_silver_nugget", hasItem(ItemTags.NUGGETS_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.SILVER_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_SILVER).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_silver_ingot", hasItem(ItemTags.INGOTS_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.SILVER_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_SILVER).addCriterion("has_silver_block", hasItem(ItemTags.STORAGE_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.SILVER_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_SILVER).addCriterion("has_silver_ingot", hasItem(ItemTags.INGOTS_SILVER)).build(consumer, new ResourceLocation(MODID, "silver_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_TIN), Registering.TIN_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "tin_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_TIN), Registering.TIN_DUST.get()).build(consumer, new ResourceLocation(MODID, "tin_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_TIN), Registering.TIN_INGOT.get(), 0.7F, 100).addCriterion("has_tin_ore", hasItem(ItemTags.ORES_TIN)).build(consumer,   new ResourceLocation(MODID, "tin_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_TIN), Registering.TIN_INGOT.get(), 0.7F, 200).addCriterion("has_tin_ore", hasItem(ItemTags.ORES_TIN)).build(consumer,   new ResourceLocation(MODID, "tin_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_TIN), Registering.TIN_INGOT.get(), 0.7F, 100).addCriterion("has_tin_dust", hasItem(ItemTags.DUSTS_TIN)).build(consumer, new ResourceLocation(MODID, "tin_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_TIN), Registering.TIN_INGOT.get(), 0.7F, 200).addCriterion("has_tin_dust", hasItem(ItemTags.DUSTS_TIN)).build(consumer, new ResourceLocation(MODID, "tin_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.TIN_INGOT.get()).key('#', ItemTags.NUGGETS_TIN).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_tin_nugget", hasItem(ItemTags.NUGGETS_TIN)).build(consumer, new ResourceLocation(MODID, "tin_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.TIN_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_TIN).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_tin_ingot", hasItem(ItemTags.INGOTS_TIN)).build(consumer, new ResourceLocation(MODID, "tin_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.TIN_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_TIN).addCriterion("has_tin_block", hasItem(ItemTags.STORAGE_TIN)).build(consumer, new ResourceLocation(MODID, "tin_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.TIN_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_TIN).addCriterion("has_tin_ingot", hasItem(ItemTags.INGOTS_TIN)).build(consumer, new ResourceLocation(MODID, "tin_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "aluminum_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_ALUMINUM), Registering.ALUMINUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "aluminum_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 100).addCriterion("has_aluminum_ore", hasItem(ItemTags.ORES_ALUMINUM)).build(consumer,   new ResourceLocation(MODID, "aluminum_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 200).addCriterion("has_aluminum_ore", hasItem(ItemTags.ORES_ALUMINUM)).build(consumer,   new ResourceLocation(MODID, "aluminum_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 100).addCriterion("has_aluminum_dust", hasItem(ItemTags.DUSTS_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 200).addCriterion("has_aluminum_dust", hasItem(ItemTags.DUSTS_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ALUMINUM_INGOT.get()).key('#', ItemTags.NUGGETS_ALUMINUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_aluminum_nugget", hasItem(ItemTags.NUGGETS_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ALUMINUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_ALUMINUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_aluminum_ingot", hasItem(ItemTags.INGOTS_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ALUMINUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_ALUMINUM).addCriterion("has_aluminum_block", hasItem(ItemTags.STORAGE_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ALUMINUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_ALUMINUM).addCriterion("has_aluminum_ingot", hasItem(ItemTags.INGOTS_ALUMINUM)).build(consumer, new ResourceLocation(MODID, "aluminum_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_ZINC), Registering.ZINC_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "zinc_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_ZINC), Registering.ZINC_DUST.get()).build(consumer, new ResourceLocation(MODID, "zinc_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 100).addCriterion("has_zinc_ore", hasItem(ItemTags.ORES_ZINC)).build(consumer,   new ResourceLocation(MODID, "zinc_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 200).addCriterion("has_zinc_ore", hasItem(ItemTags.ORES_ZINC)).build(consumer,   new ResourceLocation(MODID, "zinc_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 100).addCriterion("has_zinc_dust", hasItem(ItemTags.DUSTS_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 200).addCriterion("has_zinc_dust", hasItem(ItemTags.DUSTS_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ZINC_INGOT.get()).key('#', ItemTags.NUGGETS_ZINC).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_zinc_nugget", hasItem(ItemTags.NUGGETS_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ZINC_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_ZINC).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_zinc_ingot", hasItem(ItemTags.INGOTS_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ZINC_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_ZINC).addCriterion("has_zinc_block", hasItem(ItemTags.STORAGE_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ZINC_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_ZINC).addCriterion("has_zinc_ingot", hasItem(ItemTags.INGOTS_ZINC)).build(consumer, new ResourceLocation(MODID, "zinc_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "magnetite_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_MAGNETITE), Registering.MAGNETITE_DUST.get()).build(consumer, new ResourceLocation(MODID, "magnetite_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 100).addCriterion("has_magnetite_ore", hasItem(ItemTags.ORES_MAGNETITE)).build(consumer,   new ResourceLocation(MODID, "magnetite_ingot_from_blasting_ore"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 200).addCriterion("has_magnetite_ore", hasItem(ItemTags.ORES_MAGNETITE)).build(consumer,   new ResourceLocation(MODID, "magnetite_ingot_from_smelting_ore"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 100).addCriterion("has_magnetite_dust", hasItem(ItemTags.DUSTS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 200).addCriterion("has_magnetite_dust", hasItem(ItemTags.DUSTS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.MAGNETITE_INGOT.get()).key('#', ItemTags.NUGGETS_MAGNETITE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_magnetite_nugget", hasItem(ItemTags.NUGGETS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.MAGNETITE_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_MAGNETITE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_magnetite_ingot", hasItem(ItemTags.INGOTS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.MAGNETITE_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_MAGNETITE).addCriterion("has_magnetite_block", hasItem(ItemTags.STORAGE_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.MAGNETITE_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_MAGNETITE).addCriterion("has_magnetite_ingot", hasItem(ItemTags.INGOTS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_nugget_from_ingot"));
        ShapedRecipeBuilder.shapedRecipe(Registering.MAGNETITE_GEAR.get()).key('#', ItemTags.INGOTS_MAGNETITE).patternLine(" # ").patternLine("# #").patternLine(" # ").addCriterion("has_magnetite_ingot", hasItem(ItemTags.INGOTS_MAGNETITE)).build(consumer, new ResourceLocation(MODID, "magnetite_gear_from_ingots"));

        ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_RUTHENIUM), Registering.RUTHENIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "ruthenium_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_RUTHENIUM), Registering.RUTHENIUM_INGOT.get(), 0.7F, 100).addCriterion("has_ruthenium_dust", hasItem(ItemTags.DUSTS_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_RUTHENIUM), Registering.RUTHENIUM_INGOT.get(), 0.7F, 200).addCriterion("has_ruthenium_dust", hasItem(ItemTags.DUSTS_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.RUTHENIUM_INGOT.get()).key('#', ItemTags.NUGGETS_RUTHENIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_ruthenium_nugget", hasItem(ItemTags.NUGGETS_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.RUTHENIUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_RUTHENIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_ruthenium_ingot", hasItem(ItemTags.INGOTS_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.RUTHENIUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_RUTHENIUM).addCriterion("has_ruthenium_block", hasItem(ItemTags.STORAGE_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.RUTHENIUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_RUTHENIUM).addCriterion("has_ruthenium_ingot", hasItem(ItemTags.INGOTS_RUTHENIUM)).build(consumer, new ResourceLocation(MODID, "ruthenium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_ZIRCONIUM), Registering.ZIRCONIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "zirconium_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ZIRCONIUM), Registering.ZIRCONIUM_INGOT.get(), 0.7F, 100).addCriterion("has_zirconium_dust", hasItem(ItemTags.DUSTS_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_ZIRCONIUM), Registering.ZIRCONIUM_INGOT.get(), 0.7F, 200).addCriterion("has_zirconium_dust", hasItem(ItemTags.DUSTS_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ZIRCONIUM_INGOT.get()).key('#', ItemTags.NUGGETS_ZIRCONIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_zirconium_nugget", hasItem(ItemTags.NUGGETS_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.ZIRCONIUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_ZIRCONIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_zirconium_ingot", hasItem(ItemTags.INGOTS_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ZIRCONIUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_ZIRCONIUM).addCriterion("has_zirconium_block", hasItem(ItemTags.STORAGE_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.ZIRCONIUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_ZIRCONIUM).addCriterion("has_zirconium_ingot", hasItem(ItemTags.INGOTS_ZIRCONIUM)).build(consumer, new ResourceLocation(MODID, "zirconium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_IRIDIUM), Registering.IRIDIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "iridium_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_IRIDIUM), Registering.IRIDIUM_INGOT.get(), 0.7F, 100).addCriterion("has_iridium_dust", hasItem(ItemTags.DUSTS_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_IRIDIUM), Registering.IRIDIUM_INGOT.get(), 0.7F, 200).addCriterion("has_iridium_dust", hasItem(ItemTags.DUSTS_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.IRIDIUM_INGOT.get()).key('#', ItemTags.NUGGETS_IRIDIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_iridium_nugget", hasItem(ItemTags.NUGGETS_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.IRIDIUM_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_IRIDIUM).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_iridium_ingot", hasItem(ItemTags.INGOTS_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.IRIDIUM_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_IRIDIUM).addCriterion("has_iridium_block", hasItem(ItemTags.STORAGE_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.IRIDIUM_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_IRIDIUM).addCriterion("has_iridium_ingot", hasItem(ItemTags.INGOTS_IRIDIUM)).build(consumer, new ResourceLocation(MODID, "iridium_nugget_from_ingot"));


        ////////////////////////////////////////////////////////////////// ALLOYS

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_BRASS), Registering.BRASS_DUST.get()).build(consumer, new ResourceLocation(MODID, "brass_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_BRASS), Registering.BRASS_INGOT.get(), 0.7F, 100).addCriterion("has_brass_dust", hasItem(ItemTags.DUSTS_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_BRASS), Registering.BRASS_INGOT.get(), 0.7F, 200).addCriterion("has_brass_dust", hasItem(ItemTags.DUSTS_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.BRASS_INGOT.get()).key('#', ItemTags.NUGGETS_BRASS).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_brass_nugget", hasItem(ItemTags.NUGGETS_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.BRASS_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_BRASS).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_brass_ingot", hasItem(ItemTags.INGOTS_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.BRASS_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_BRASS).addCriterion("has_brass_block", hasItem(ItemTags.STORAGE_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.BRASS_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_BRASS).addCriterion("has_brass_ingot", hasItem(ItemTags.INGOTS_BRASS)).build(consumer, new ResourceLocation(MODID, "brass_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.fromTag(ItemTags.INGOTS_BRONZE), Registering.BRONZE_DUST.get()).build(consumer, new ResourceLocation(MODID, "bronze_dust_from_ingot"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ItemTags.DUSTS_BRONZE), Registering.BRONZE_INGOT.get(), 0.7F, 100).addCriterion("has_bronze_dust", hasItem(ItemTags.DUSTS_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_ingot_from_blasting_dust"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ItemTags.DUSTS_BRONZE), Registering.BRONZE_INGOT.get(), 0.7F, 200).addCriterion("has_bronze_dust", hasItem(ItemTags.DUSTS_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shapedRecipe(Registering.BRONZE_INGOT.get()).key('#', ItemTags.NUGGETS_BRONZE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_bronze_nugget", hasItem(ItemTags.NUGGETS_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_ingot_from_nuggets"));
        ShapedRecipeBuilder.shapedRecipe(Registering.BRONZE_BLOCK.ITEM.get()).key('#', ItemTags.INGOTS_BRONZE).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_bronze_ingot", hasItem(ItemTags.INGOTS_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_block_from_ingots"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.BRONZE_INGOT.get(), 9).addIngredient(ItemTags.STORAGE_BRONZE).addCriterion("has_bronze_block", hasItem(ItemTags.STORAGE_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_ingot_from_block"));
        ShapelessRecipeBuilder.shapelessRecipe(Registering.BRONZE_NUGGET.get(), 9).addIngredient(ItemTags.INGOTS_BRONZE).addCriterion("has_bronze_ingot", hasItem(ItemTags.INGOTS_BRONZE)).build(consumer, new ResourceLocation(MODID, "bronze_nugget_from_ingot"));

    }

    private void registerAlloyRecipes(Consumer<IFinishedRecipe> consumer) {
        AlloyRecipeBuilder.alloyRecipe(Registering.BRONZE_INGOT.get(), 5).addIngredient(Ingredient.fromTag(ItemTags.INGOTS_COPPER), 4).addIngredient(Ingredient.fromTag(ItemTags.INGOTS_TIN)).build(consumer);
        AlloyRecipeBuilder.alloyRecipe(Registering.BRASS_INGOT.get(), 5).addIngredient(Ingredient.fromTag(ItemTags.INGOTS_COPPER), 3).addIngredient(Ingredient.fromTag(ItemTags.INGOTS_ZINC), 2).build(consumer);
    }

    private void registerMagnetizerRecipes(Consumer<IFinishedRecipe> consumer) {
        SingleInputOutputRecipeBuilder.magnetizerRecipe(Ingredient.fromTag(Tags.Items.INGOTS_IRON), Registering.MAGNETITE_INGOT.get()).build(consumer, new ResourceLocation(MODID, "magnetite_from_iron_ingot"));
    }

    private void registerMagneticSeparatorRecipes(Consumer<IFinishedRecipe> consumer) {
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.fromTag(ItemTags.GRAVEL_ORES_PLATINUM), Items.GRAVEL).secondOutput(Registering.PLATINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.fromTag(ItemTags.GRAVEL_ORES_IRIDIUM), Items.GRAVEL).secondOutput(Registering.IRIDIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "iridium_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.fromTag(ItemTags.GRAVEL_ORES_RUTHENIUM), Items.GRAVEL).secondOutput(Registering.RUTHENIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "ruthenium_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.fromTag(ItemTags.GRAVEL_ORES_ZIRCONIUM), Items.GRAVEL).secondOutput(Registering.ZIRCONIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "zirconium_dust_from_gravel_ore"));
    }
}
