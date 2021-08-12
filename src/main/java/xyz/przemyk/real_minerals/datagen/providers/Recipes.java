package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import xyz.przemyk.real_minerals.datagen.recipe_builders.AlloyRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.MagneticSeparatorRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.SingleInputOutputRecipeBuilder;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.function.Consumer;

import static xyz.przemyk.real_minerals.RealMinerals.MODID;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        registerBaseRecipes(consumer);
        registerAlloyRecipes(consumer);
        registerMagnetizerRecipes(consumer);
        registerMagneticSeparatorRecipes(consumer);
    }

    private void registerBaseRecipes(Consumer<FinishedRecipe> consumer) {
        ////////////////////////////////////////////////////////////////// METALS WITH STONE ORES
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_COPPER), Registering.COPPER_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "copper_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_COPPER), Registering.COPPER_DUST.get()).build(consumer, new ResourceLocation(MODID, "copper_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 100).unlockedBy("has_copper_ore", has(ItemTags.ORES_COPPER)).save(consumer,   new ResourceLocation(MODID, "copper_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 200).unlockedBy("has_copper_ore", has(ItemTags.ORES_COPPER)).save(consumer,   new ResourceLocation(MODID, "copper_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 100).unlockedBy("has_copper_dust", has(ItemTags.DUSTS_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_COPPER), Registering.COPPER_INGOT.get(), 0.7F, 200).unlockedBy("has_copper_dust", has(ItemTags.DUSTS_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.COPPER_INGOT.get()).define('#', ItemTags.NUGGETS_COPPER).pattern("###").pattern("###").pattern("###").unlockedBy("has_copper_nugget", has(ItemTags.NUGGETS_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.COPPER_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_COPPER).pattern("###").pattern("###").pattern("###").unlockedBy("has_copper_ingot", has(ItemTags.INGOTS_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.COPPER_INGOT.get(), 9).requires(ItemTags.STORAGE_COPPER).unlockedBy("has_copper_block", has(ItemTags.STORAGE_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.COPPER_NUGGET.get(), 9).requires(ItemTags.INGOTS_COPPER).unlockedBy("has_copper_ingot", has(ItemTags.INGOTS_COPPER)).save(consumer, new ResourceLocation(MODID, "copper_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_LEAD), Registering.LEAD_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "lead_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_LEAD), Registering.LEAD_DUST.get()).build(consumer, new ResourceLocation(MODID, "lead_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 100).unlockedBy("has_lead_ore", has(ItemTags.ORES_LEAD)).save(consumer,   new ResourceLocation(MODID, "lead_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 200).unlockedBy("has_lead_ore", has(ItemTags.ORES_LEAD)).save(consumer,   new ResourceLocation(MODID, "lead_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 100).unlockedBy("has_lead_dust", has(ItemTags.DUSTS_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_LEAD), Registering.LEAD_INGOT.get(), 0.7F, 200).unlockedBy("has_lead_dust", has(ItemTags.DUSTS_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.LEAD_INGOT.get()).define('#', ItemTags.NUGGETS_LEAD).pattern("###").pattern("###").pattern("###").unlockedBy("has_lead_nugget", has(ItemTags.NUGGETS_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.LEAD_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_LEAD).pattern("###").pattern("###").pattern("###").unlockedBy("has_lead_ingot", has(ItemTags.INGOTS_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.LEAD_INGOT.get(), 9).requires(ItemTags.STORAGE_LEAD).unlockedBy("has_lead_block", has(ItemTags.STORAGE_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.LEAD_NUGGET.get(), 9).requires(ItemTags.INGOTS_LEAD).unlockedBy("has_lead_ingot", has(ItemTags.INGOTS_LEAD)).save(consumer, new ResourceLocation(MODID, "lead_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "magnesium_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_MAGNESIUM), Registering.MAGNESIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "magnesium_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 100).unlockedBy("has_magnesium_ore", has(ItemTags.ORES_MAGNESIUM)).save(consumer,   new ResourceLocation(MODID, "magnesium_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 200).unlockedBy("has_magnesium_ore", has(ItemTags.ORES_MAGNESIUM)).save(consumer,   new ResourceLocation(MODID, "magnesium_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 100).unlockedBy("has_magnesium_dust", has(ItemTags.DUSTS_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_MAGNESIUM), Registering.MAGNESIUM_INGOT.get(), 0.7F, 200).unlockedBy("has_magnesium_dust", has(ItemTags.DUSTS_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.MAGNESIUM_INGOT.get()).define('#', ItemTags.NUGGETS_MAGNESIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_magnesium_nugget", has(ItemTags.NUGGETS_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.MAGNESIUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_MAGNESIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_magnesium_ingot", has(ItemTags.INGOTS_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.MAGNESIUM_INGOT.get(), 9).requires(ItemTags.STORAGE_MAGNESIUM).unlockedBy("has_magnesium_block", has(ItemTags.STORAGE_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.MAGNESIUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_MAGNESIUM).unlockedBy("has_magnesium_ingot", has(ItemTags.INGOTS_MAGNESIUM)).save(consumer, new ResourceLocation(MODID, "magnesium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_NICKEL), Registering.NICKEL_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "nickel_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_NICKEL), Registering.NICKEL_DUST.get()).build(consumer, new ResourceLocation(MODID, "nickel_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 100).unlockedBy("has_nickel_ore", has(ItemTags.ORES_NICKEL)).save(consumer,   new ResourceLocation(MODID, "nickel_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 200).unlockedBy("has_nickel_ore", has(ItemTags.ORES_NICKEL)).save(consumer,   new ResourceLocation(MODID, "nickel_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 100).unlockedBy("has_nickel_dust", has(ItemTags.DUSTS_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_NICKEL), Registering.NICKEL_INGOT.get(), 0.7F, 200).unlockedBy("has_nickel_dust", has(ItemTags.DUSTS_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.NICKEL_INGOT.get()).define('#', ItemTags.NUGGETS_NICKEL).pattern("###").pattern("###").pattern("###").unlockedBy("has_nickel_nugget", has(ItemTags.NUGGETS_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.NICKEL_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_NICKEL).pattern("###").pattern("###").pattern("###").unlockedBy("has_nickel_ingot", has(ItemTags.INGOTS_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.NICKEL_INGOT.get(), 9).requires(ItemTags.STORAGE_NICKEL).unlockedBy("has_nickel_block", has(ItemTags.STORAGE_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.NICKEL_NUGGET.get(), 9).requires(ItemTags.INGOTS_NICKEL).unlockedBy("has_nickel_ingot", has(ItemTags.INGOTS_NICKEL)).save(consumer, new ResourceLocation(MODID, "nickel_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_PLATINUM), Registering.PLATINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_PLATINUM), Registering.PLATINUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 100).unlockedBy("has_platinum_ore", has(ItemTags.ORES_PLATINUM)).save(consumer,   new ResourceLocation(MODID, "platinum_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 200).unlockedBy("has_platinum_ore", has(ItemTags.ORES_PLATINUM)).save(consumer,   new ResourceLocation(MODID, "platinum_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 100).unlockedBy("has_platinum_dust", has(ItemTags.DUSTS_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_PLATINUM), Registering.PLATINUM_INGOT.get(), 0.7F, 200).unlockedBy("has_platinum_dust", has(ItemTags.DUSTS_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.PLATINUM_INGOT.get()).define('#', ItemTags.NUGGETS_PLATINUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_platinum_nugget", has(ItemTags.NUGGETS_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.PLATINUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_PLATINUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_platinum_ingot", has(ItemTags.INGOTS_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.PLATINUM_INGOT.get(), 9).requires(ItemTags.STORAGE_PLATINUM).unlockedBy("has_platinum_block", has(ItemTags.STORAGE_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.PLATINUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_PLATINUM).unlockedBy("has_platinum_ingot", has(ItemTags.INGOTS_PLATINUM)).save(consumer, new ResourceLocation(MODID, "platinum_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_SILVER), Registering.SILVER_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "silver_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_SILVER), Registering.SILVER_DUST.get()).build(consumer, new ResourceLocation(MODID, "silver_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 100).unlockedBy("has_silver_ore", has(ItemTags.ORES_SILVER)).save(consumer,   new ResourceLocation(MODID, "silver_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 200).unlockedBy("has_silver_ore", has(ItemTags.ORES_SILVER)).save(consumer,   new ResourceLocation(MODID, "silver_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 100).unlockedBy("has_silver_dust", has(ItemTags.DUSTS_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_SILVER), Registering.SILVER_INGOT.get(), 0.7F, 200).unlockedBy("has_silver_dust", has(ItemTags.DUSTS_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.SILVER_INGOT.get()).define('#', ItemTags.NUGGETS_SILVER).pattern("###").pattern("###").pattern("###").unlockedBy("has_silver_nugget", has(ItemTags.NUGGETS_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.SILVER_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_SILVER).pattern("###").pattern("###").pattern("###").unlockedBy("has_silver_ingot", has(ItemTags.INGOTS_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.SILVER_INGOT.get(), 9).requires(ItemTags.STORAGE_SILVER).unlockedBy("has_silver_block", has(ItemTags.STORAGE_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.SILVER_NUGGET.get(), 9).requires(ItemTags.INGOTS_SILVER).unlockedBy("has_silver_ingot", has(ItemTags.INGOTS_SILVER)).save(consumer, new ResourceLocation(MODID, "silver_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_TIN), Registering.TIN_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "tin_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_TIN), Registering.TIN_DUST.get()).build(consumer, new ResourceLocation(MODID, "tin_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_TIN), Registering.TIN_INGOT.get(), 0.7F, 100).unlockedBy("has_tin_ore", has(ItemTags.ORES_TIN)).save(consumer,   new ResourceLocation(MODID, "tin_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_TIN), Registering.TIN_INGOT.get(), 0.7F, 200).unlockedBy("has_tin_ore", has(ItemTags.ORES_TIN)).save(consumer,   new ResourceLocation(MODID, "tin_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_TIN), Registering.TIN_INGOT.get(), 0.7F, 100).unlockedBy("has_tin_dust", has(ItemTags.DUSTS_TIN)).save(consumer, new ResourceLocation(MODID, "tin_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_TIN), Registering.TIN_INGOT.get(), 0.7F, 200).unlockedBy("has_tin_dust", has(ItemTags.DUSTS_TIN)).save(consumer, new ResourceLocation(MODID, "tin_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.TIN_INGOT.get()).define('#', ItemTags.NUGGETS_TIN).pattern("###").pattern("###").pattern("###").unlockedBy("has_tin_nugget", has(ItemTags.NUGGETS_TIN)).save(consumer, new ResourceLocation(MODID, "tin_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.TIN_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_TIN).pattern("###").pattern("###").pattern("###").unlockedBy("has_tin_ingot", has(ItemTags.INGOTS_TIN)).save(consumer, new ResourceLocation(MODID, "tin_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.TIN_INGOT.get(), 9).requires(ItemTags.STORAGE_TIN).unlockedBy("has_tin_block", has(ItemTags.STORAGE_TIN)).save(consumer, new ResourceLocation(MODID, "tin_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.TIN_NUGGET.get(), 9).requires(ItemTags.INGOTS_TIN).unlockedBy("has_tin_ingot", has(ItemTags.INGOTS_TIN)).save(consumer, new ResourceLocation(MODID, "tin_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "aluminum_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_ALUMINUM), Registering.ALUMINUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "aluminum_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 100).unlockedBy("has_aluminum_ore", has(ItemTags.ORES_ALUMINUM)).save(consumer,   new ResourceLocation(MODID, "aluminum_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 200).unlockedBy("has_aluminum_ore", has(ItemTags.ORES_ALUMINUM)).save(consumer,   new ResourceLocation(MODID, "aluminum_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 100).unlockedBy("has_aluminum_dust", has(ItemTags.DUSTS_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_ALUMINUM), Registering.ALUMINUM_INGOT.get(), 0.7F, 200).unlockedBy("has_aluminum_dust", has(ItemTags.DUSTS_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.ALUMINUM_INGOT.get()).define('#', ItemTags.NUGGETS_ALUMINUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_aluminum_nugget", has(ItemTags.NUGGETS_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.ALUMINUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_ALUMINUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_aluminum_ingot", has(ItemTags.INGOTS_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.ALUMINUM_INGOT.get(), 9).requires(ItemTags.STORAGE_ALUMINUM).unlockedBy("has_aluminum_block", has(ItemTags.STORAGE_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.ALUMINUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_ALUMINUM).unlockedBy("has_aluminum_ingot", has(ItemTags.INGOTS_ALUMINUM)).save(consumer, new ResourceLocation(MODID, "aluminum_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_ZINC), Registering.ZINC_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "zinc_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_ZINC), Registering.ZINC_DUST.get()).build(consumer, new ResourceLocation(MODID, "zinc_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 100).unlockedBy("has_zinc_ore", has(ItemTags.ORES_ZINC)).save(consumer,   new ResourceLocation(MODID, "zinc_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 200).unlockedBy("has_zinc_ore", has(ItemTags.ORES_ZINC)).save(consumer,   new ResourceLocation(MODID, "zinc_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 100).unlockedBy("has_zinc_dust", has(ItemTags.DUSTS_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_ZINC), Registering.ZINC_INGOT.get(), 0.7F, 200).unlockedBy("has_zinc_dust", has(ItemTags.DUSTS_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.ZINC_INGOT.get()).define('#', ItemTags.NUGGETS_ZINC).pattern("###").pattern("###").pattern("###").unlockedBy("has_zinc_nugget", has(ItemTags.NUGGETS_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.ZINC_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_ZINC).pattern("###").pattern("###").pattern("###").unlockedBy("has_zinc_ingot", has(ItemTags.INGOTS_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.ZINC_INGOT.get(), 9).requires(ItemTags.STORAGE_ZINC).unlockedBy("has_zinc_block", has(ItemTags.STORAGE_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.ZINC_NUGGET.get(), 9).requires(ItemTags.INGOTS_ZINC).unlockedBy("has_zinc_ingot", has(ItemTags.INGOTS_ZINC)).save(consumer, new ResourceLocation(MODID, "zinc_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "magnetite_dust_from_ore"));
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_MAGNETITE), Registering.MAGNETITE_DUST.get()).build(consumer, new ResourceLocation(MODID, "magnetite_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 100).unlockedBy("has_magnetite_ore", has(ItemTags.ORES_MAGNETITE)).save(consumer,   new ResourceLocation(MODID, "magnetite_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.ORES_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 200).unlockedBy("has_magnetite_ore", has(ItemTags.ORES_MAGNETITE)).save(consumer,   new ResourceLocation(MODID, "magnetite_ingot_from_smelting_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 100).unlockedBy("has_magnetite_dust", has(ItemTags.DUSTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_MAGNETITE), Registering.MAGNETITE_INGOT.get(), 0.7F, 200).unlockedBy("has_magnetite_dust", has(ItemTags.DUSTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.MAGNETITE_INGOT.get()).define('#', ItemTags.NUGGETS_MAGNETITE).pattern("###").pattern("###").pattern("###").unlockedBy("has_magnetite_nugget", has(ItemTags.NUGGETS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.MAGNETITE_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_MAGNETITE).pattern("###").pattern("###").pattern("###").unlockedBy("has_magnetite_ingot", has(ItemTags.INGOTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.MAGNETITE_INGOT.get(), 9).requires(ItemTags.STORAGE_MAGNETITE).unlockedBy("has_magnetite_block", has(ItemTags.STORAGE_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.MAGNETITE_NUGGET.get(), 9).requires(ItemTags.INGOTS_MAGNETITE).unlockedBy("has_magnetite_ingot", has(ItemTags.INGOTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_nugget_from_ingot"));
        ShapedRecipeBuilder.shaped(Registering.MAGNETITE_GEAR.get()).define('#', ItemTags.INGOTS_MAGNETITE).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_magnetite_ingot", has(ItemTags.INGOTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_gear_from_ingots"));

        ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_RUTHENIUM), Registering.RUTHENIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "ruthenium_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_RUTHENIUM), Registering.RUTHENIUM_INGOT.get(), 0.7F, 100).unlockedBy("has_ruthenium_dust", has(ItemTags.DUSTS_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_RUTHENIUM), Registering.RUTHENIUM_INGOT.get(), 0.7F, 200).unlockedBy("has_ruthenium_dust", has(ItemTags.DUSTS_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.RUTHENIUM_INGOT.get()).define('#', ItemTags.NUGGETS_RUTHENIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_ruthenium_nugget", has(ItemTags.NUGGETS_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.RUTHENIUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_RUTHENIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_ruthenium_ingot", has(ItemTags.INGOTS_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.RUTHENIUM_INGOT.get(), 9).requires(ItemTags.STORAGE_RUTHENIUM).unlockedBy("has_ruthenium_block", has(ItemTags.STORAGE_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.RUTHENIUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_RUTHENIUM).unlockedBy("has_ruthenium_ingot", has(ItemTags.INGOTS_RUTHENIUM)).save(consumer, new ResourceLocation(MODID, "ruthenium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_ZIRCONIUM), Registering.ZIRCONIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "zirconium_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_ZIRCONIUM), Registering.ZIRCONIUM_INGOT.get(), 0.7F, 100).unlockedBy("has_zirconium_dust", has(ItemTags.DUSTS_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_ZIRCONIUM), Registering.ZIRCONIUM_INGOT.get(), 0.7F, 200).unlockedBy("has_zirconium_dust", has(ItemTags.DUSTS_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.ZIRCONIUM_INGOT.get()).define('#', ItemTags.NUGGETS_ZIRCONIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_zirconium_nugget", has(ItemTags.NUGGETS_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.ZIRCONIUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_ZIRCONIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_zirconium_ingot", has(ItemTags.INGOTS_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.ZIRCONIUM_INGOT.get(), 9).requires(ItemTags.STORAGE_ZIRCONIUM).unlockedBy("has_zirconium_block", has(ItemTags.STORAGE_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.ZIRCONIUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_ZIRCONIUM).unlockedBy("has_zirconium_ingot", has(ItemTags.INGOTS_ZIRCONIUM)).save(consumer, new ResourceLocation(MODID, "zirconium_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_IRIDIUM), Registering.IRIDIUM_DUST.get()).build(consumer, new ResourceLocation(MODID, "iridium_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_IRIDIUM), Registering.IRIDIUM_INGOT.get(), 0.7F, 100).unlockedBy("has_iridium_dust", has(ItemTags.DUSTS_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_IRIDIUM), Registering.IRIDIUM_INGOT.get(), 0.7F, 200).unlockedBy("has_iridium_dust", has(ItemTags.DUSTS_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.IRIDIUM_INGOT.get()).define('#', ItemTags.NUGGETS_IRIDIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_iridium_nugget", has(ItemTags.NUGGETS_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.IRIDIUM_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_IRIDIUM).pattern("###").pattern("###").pattern("###").unlockedBy("has_iridium_ingot", has(ItemTags.INGOTS_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.IRIDIUM_INGOT.get(), 9).requires(ItemTags.STORAGE_IRIDIUM).unlockedBy("has_iridium_block", has(ItemTags.STORAGE_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.IRIDIUM_NUGGET.get(), 9).requires(ItemTags.INGOTS_IRIDIUM).unlockedBy("has_iridium_ingot", has(ItemTags.INGOTS_IRIDIUM)).save(consumer, new ResourceLocation(MODID, "iridium_nugget_from_ingot"));


        ////////////////////////////////////////////////////////////////// ALLOYS

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_BRASS), Registering.BRASS_DUST.get()).build(consumer, new ResourceLocation(MODID, "brass_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_BRASS), Registering.BRASS_INGOT.get(), 0.7F, 100).unlockedBy("has_brass_dust", has(ItemTags.DUSTS_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_BRASS), Registering.BRASS_INGOT.get(), 0.7F, 200).unlockedBy("has_brass_dust", has(ItemTags.DUSTS_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.BRASS_INGOT.get()).define('#', ItemTags.NUGGETS_BRASS).pattern("###").pattern("###").pattern("###").unlockedBy("has_brass_nugget", has(ItemTags.NUGGETS_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.BRASS_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_BRASS).pattern("###").pattern("###").pattern("###").unlockedBy("has_brass_ingot", has(ItemTags.INGOTS_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.BRASS_INGOT.get(), 9).requires(ItemTags.STORAGE_BRASS).unlockedBy("has_brass_block", has(ItemTags.STORAGE_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.BRASS_NUGGET.get(), 9).requires(ItemTags.INGOTS_BRASS).unlockedBy("has_brass_ingot", has(ItemTags.INGOTS_BRASS)).save(consumer, new ResourceLocation(MODID, "brass_nugget_from_ingot"));

        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ItemTags.INGOTS_BRONZE), Registering.BRONZE_DUST.get()).build(consumer, new ResourceLocation(MODID, "bronze_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ItemTags.DUSTS_BRONZE), Registering.BRONZE_INGOT.get(), 0.7F, 100).unlockedBy("has_bronze_dust", has(ItemTags.DUSTS_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.DUSTS_BRONZE), Registering.BRONZE_INGOT.get(), 0.7F, 200).unlockedBy("has_bronze_dust", has(ItemTags.DUSTS_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(Registering.BRONZE_INGOT.get()).define('#', ItemTags.NUGGETS_BRONZE).pattern("###").pattern("###").pattern("###").unlockedBy("has_bronze_nugget", has(ItemTags.NUGGETS_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(Registering.BRONZE_BLOCK.ITEM.get()).define('#', ItemTags.INGOTS_BRONZE).pattern("###").pattern("###").pattern("###").unlockedBy("has_bronze_ingot", has(ItemTags.INGOTS_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(Registering.BRONZE_INGOT.get(), 9).requires(ItemTags.STORAGE_BRONZE).unlockedBy("has_bronze_block", has(ItemTags.STORAGE_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(Registering.BRONZE_NUGGET.get(), 9).requires(ItemTags.INGOTS_BRONZE).unlockedBy("has_bronze_ingot", has(ItemTags.INGOTS_BRONZE)).save(consumer, new ResourceLocation(MODID, "bronze_nugget_from_ingot"));

    }

    private void registerAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        AlloyRecipeBuilder.alloyRecipe(Registering.BRONZE_INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 4).addIngredient(Ingredient.of(ItemTags.INGOTS_TIN)).build(consumer);
        AlloyRecipeBuilder.alloyRecipe(Registering.BRASS_INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 3).addIngredient(Ingredient.of(ItemTags.INGOTS_ZINC), 2).build(consumer);
    }

    private void registerMagnetizerRecipes(Consumer<FinishedRecipe> consumer) {
        SingleInputOutputRecipeBuilder.magnetizerRecipe(Ingredient.of(Tags.Items.INGOTS_IRON), Registering.MAGNETITE_INGOT.get()).build(consumer, new ResourceLocation(MODID, "magnetite_from_iron_ingot"));
    }

    private void registerMagneticSeparatorRecipes(Consumer<FinishedRecipe> consumer) {
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.of(ItemTags.GRAVEL_ORES_PLATINUM), Items.GRAVEL).secondOutput(Registering.PLATINUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "platinum_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.of(ItemTags.GRAVEL_ORES_IRIDIUM), Items.GRAVEL).secondOutput(Registering.IRIDIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "iridium_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.of(ItemTags.GRAVEL_ORES_RUTHENIUM), Items.GRAVEL).secondOutput(Registering.RUTHENIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "ruthenium_dust_from_gravel_ore"));
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.of(ItemTags.GRAVEL_ORES_ZIRCONIUM), Items.GRAVEL).secondOutput(Registering.ZIRCONIUM_DUST.get(), 2).build(consumer, new ResourceLocation(MODID, "zirconium_dust_from_gravel_ore"));
    }
}
