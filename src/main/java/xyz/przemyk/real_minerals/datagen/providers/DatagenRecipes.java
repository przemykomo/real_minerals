package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import xyz.przemyk.real_minerals.datagen.recipe_builders.AlloyRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.MagneticSeparatorRecipeBuilder;
import xyz.przemyk.real_minerals.datagen.recipe_builders.SingleInputOutputRecipeBuilder;
import xyz.przemyk.real_minerals.init.*;

import java.util.function.Consumer;

import static xyz.przemyk.real_minerals.RealMinerals.MODID;

public class DatagenRecipes extends RecipeProvider {

    public DatagenRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ////////////////////////////////////////////////////////////////// METALS WITH STONE ORES
        registerMineralRecipes(consumer, ItemTags.ORES_LEAD, StoneMinerals.LEAD_ITEMS, ItemTags.NUGGETS_LEAD, ItemTags.INGOTS_LEAD, StoneMinerals.LEAD_BLOCK, ItemTags.STORAGE_LEAD, ItemTags.DUSTS_LEAD, "lead");
        registerMineralRecipes(consumer, ItemTags.ORES_MAGNESIUM, StoneMinerals.MAGNESIUM_ITEMS, ItemTags.NUGGETS_MAGNESIUM, ItemTags.INGOTS_MAGNESIUM, StoneMinerals.MAGNESIUM_BLOCK, ItemTags.STORAGE_MAGNESIUM, ItemTags.DUSTS_MAGNESIUM, "magnesium");
        registerMineralRecipes(consumer, ItemTags.ORES_NICKEL, StoneMinerals.NICKEL_ITEMS, ItemTags.NUGGETS_NICKEL, ItemTags.INGOTS_NICKEL, StoneMinerals.NICKEL_BLOCK, ItemTags.STORAGE_NICKEL, ItemTags.DUSTS_NICKEL, "nickel");
        registerMineralRecipes(consumer, ItemTags.ORES_SILVER, StoneMinerals.SILVER_ITEMS, ItemTags.NUGGETS_SILVER, ItemTags.INGOTS_SILVER, StoneMinerals.SILVER_BLOCK, ItemTags.STORAGE_SILVER, ItemTags.DUSTS_SILVER, "silver");
        registerMineralRecipes(consumer, ItemTags.ORES_TIN, StoneMinerals.TIN_ITEMS, ItemTags.NUGGETS_TIN, ItemTags.INGOTS_TIN, StoneMinerals.TIN_BLOCK, ItemTags.STORAGE_TIN, ItemTags.DUSTS_TIN, "tin");
        registerMineralRecipes(consumer, ItemTags.ORES_ALUMINUM, StoneMinerals.ALUMINUM_ITEMS, ItemTags.NUGGETS_ALUMINUM, ItemTags.INGOTS_ALUMINUM, StoneMinerals.ALUMINUM_BLOCK, ItemTags.STORAGE_ALUMINUM, ItemTags.DUSTS_ALUMINUM, "aluminum");
        registerMineralRecipes(consumer, ItemTags.ORES_ZINC, StoneMinerals.ZINC_ITEMS, ItemTags.NUGGETS_ZINC, ItemTags.INGOTS_ZINC, StoneMinerals.ZINC_BLOCK, ItemTags.STORAGE_ZINC, ItemTags.DUSTS_ZINC, "zinc");

        SingleInputOutputRecipeBuilder.magnetizerRecipe(Ingredient.of(Tags.Items.INGOTS_IRON), StoneMinerals.MAGNETITE_ITEMS.INGOT.get()).build(consumer, new ResourceLocation(MODID, "magnetite_from_iron_ingot"));
        registerMineralRecipes(consumer, ItemTags.ORES_MAGNETITE, StoneMinerals.MAGNETITE_ITEMS, ItemTags.NUGGETS_MAGNETITE, ItemTags.INGOTS_MAGNETITE, StoneMinerals.MAGNETITE_BLOCK, ItemTags.STORAGE_MAGNETITE, ItemTags.DUSTS_MAGNETITE, "magnetite");
        ShapedRecipeBuilder.shaped(StoneMinerals.MAGNETITE_GEAR.get()).define('#', ItemTags.INGOTS_MAGNETITE).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_magnetite_ingot", has(ItemTags.INGOTS_MAGNETITE)).save(consumer, new ResourceLocation(MODID, "magnetite_gear_from_ingots"));

        ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES
        registerGravelMineralRecipes(consumer, GravelMinerals.RUTHENIUM_ITEMS, ItemTags.NUGGETS_RUTHENIUM, ItemTags.INGOTS_RUTHENIUM, GravelMinerals.RUTHENIUM_BLOCK, ItemTags.STORAGE_RUTHENIUM, ItemTags.DUSTS_RUTHENIUM, ItemTags.GRAVEL_ORES_RUTHENIUM, "ruthenium");
        registerGravelMineralRecipes(consumer, GravelMinerals.ZIRCONIUM_ITEMS, ItemTags.NUGGETS_ZIRCONIUM, ItemTags.INGOTS_ZIRCONIUM, GravelMinerals.ZIRCONIUM_BLOCK, ItemTags.STORAGE_ZIRCONIUM, ItemTags.DUSTS_ZIRCONIUM, ItemTags.GRAVEL_ORES_ZIRCONIUM, "zirconium");
        registerGravelMineralRecipes(consumer, GravelMinerals.IRIDIUM_ITEMS, ItemTags.NUGGETS_IRIDIUM, ItemTags.INGOTS_IRIDIUM, GravelMinerals.IRIDIUM_BLOCK, ItemTags.STORAGE_IRIDIUM, ItemTags.DUSTS_IRIDIUM, ItemTags.GRAVEL_ORES_IRIDIUM, "iridium");

        ////////////////////////////////////////////////////////////////// ALLOYS
        AlloyRecipeBuilder.alloyRecipe(Registering.BRASS_ITEMS.INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 3).addIngredient(Ingredient.of(ItemTags.INGOTS_ZINC), 2).build(consumer);
        registerCommonMineralsRecipes(consumer, Registering.BRASS_ITEMS, ItemTags.NUGGETS_BRASS, ItemTags.INGOTS_BRASS, Registering.BRASS_BLOCK, ItemTags.STORAGE_BRASS, ItemTags.DUSTS_BRASS, "brass");

        AlloyRecipeBuilder.alloyRecipe(Registering.BRONZE_ITEMS.INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 4).addIngredient(Ingredient.of(ItemTags.INGOTS_TIN)).build(consumer);
        registerCommonMineralsRecipes(consumer, Registering.BRONZE_ITEMS, ItemTags.NUGGETS_BRONZE, ItemTags.INGOTS_BRONZE, Registering.BRONZE_BLOCK, ItemTags.STORAGE_BRONZE, ItemTags.DUSTS_BRONZE, "bronze");
    }

    private void registerMineralRecipes(Consumer<FinishedRecipe> consumer, Tags.IOptionalNamedTag<Item> oresItemTag, RegistryMetalSet registryMetalSet, Tags.IOptionalNamedTag<Item> nuggetsItemTag, Tags.IOptionalNamedTag<Item> ingotsTag, BlockRegistryObject storageBlock, Tags.IOptionalNamedTag<Item> storageTag, Tags.IOptionalNamedTag<Item> dustsItemTag, String name) {
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(oresItemTag), registryMetalSet.DUST.get(), 2).build(consumer, new ResourceLocation(MODID, name + "_dust_from_ore"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(oresItemTag), registryMetalSet.INGOT.get(), 0.7F, 100).unlockedBy("has_" + name + "_ore", has(oresItemTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(oresItemTag), registryMetalSet.INGOT.get(), 0.7F, 200).unlockedBy("has_" + name + "_ore", has(oresItemTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_smelting_ore"));
        registerCommonMineralsRecipes(consumer, registryMetalSet, nuggetsItemTag, ingotsTag, storageBlock, storageTag, dustsItemTag, name);
    }

    private void registerGravelMineralRecipes(Consumer<FinishedRecipe> consumer, RegistryMetalSet registryMetalSet, Tags.IOptionalNamedTag<Item> nuggetsItemTag, Tags.IOptionalNamedTag<Item> ingotsTag, BlockRegistryObject storageBlock, Tags.IOptionalNamedTag<Item> storageTag, Tags.IOptionalNamedTag<Item> dustsItemTag, Tags.IOptionalNamedTag<Item> tagGravelOres, String name) {
        MagneticSeparatorRecipeBuilder.magneticSeparatorRecipe(Ingredient.of(tagGravelOres), Items.GRAVEL).secondOutput(registryMetalSet.DUST.get(), 2).build(consumer, new ResourceLocation(MODID, name + "_dust_from_gravel_ore"));
        registerCommonMineralsRecipes(consumer, registryMetalSet, nuggetsItemTag, ingotsTag, storageBlock, storageTag, dustsItemTag, name);
    }

    private void registerCommonMineralsRecipes(Consumer<FinishedRecipe> consumer, RegistryMetalSet registryMetalSet, Tags.IOptionalNamedTag<Item> nuggetsItemTag, Tags.IOptionalNamedTag<Item> ingotsTag, BlockRegistryObject storageBlock, Tags.IOptionalNamedTag<Item> storageTag, Tags.IOptionalNamedTag<Item> dustsItemTag, String name) {
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(ingotsTag), registryMetalSet.DUST.get()).build(consumer, new ResourceLocation(MODID, name + "_dust_from_ingot"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(dustsItemTag), registryMetalSet.INGOT.get(), 0.7F, 100).unlockedBy("has_" + name + "_dust", has(dustsItemTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_blasting_dust"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(dustsItemTag), registryMetalSet.INGOT.get(), 0.7F, 200).unlockedBy("has_" + name + "_dust", has(dustsItemTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_smelting_dust"));
        ShapedRecipeBuilder.shaped(registryMetalSet.INGOT.get()).define('#', nuggetsItemTag).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + name + "_nugget", has(nuggetsItemTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_nuggets"));
        ShapedRecipeBuilder.shaped(storageBlock.ITEM.get()).define('#', ingotsTag).pattern("###").pattern("###").pattern("###").unlockedBy("has_" + name + "_ingot", has(ingotsTag)).save(consumer, new ResourceLocation(MODID, name + "_block_from_ingots"));
        ShapelessRecipeBuilder.shapeless(registryMetalSet.INGOT.get(), 9).requires(storageTag).unlockedBy("has_" + name + "_block", has(storageTag)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_block"));
        ShapelessRecipeBuilder.shapeless(registryMetalSet.NUGGET.get(), 9).requires(ingotsTag).unlockedBy("has_" + name + "_ingot", has(ingotsTag)).save(consumer, new ResourceLocation(MODID, name + "_nugget_from_ingot"));
    }
}
