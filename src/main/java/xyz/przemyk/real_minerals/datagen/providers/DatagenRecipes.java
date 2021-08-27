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
        registerMineralRecipes(consumer, StoneMinerals.LEAD, "lead", ItemTags.LEAD);
        registerMineralRecipes(consumer, StoneMinerals.MAGNESIUM, "magnesium", ItemTags.MAGNESIUM);
        registerMineralRecipes(consumer, StoneMinerals.NICKEL, "nickel", ItemTags.NICKEL);
        registerMineralRecipes(consumer, StoneMinerals.SILVER, "silver", ItemTags.SILVER);
        registerMineralRecipes(consumer, StoneMinerals.TIN, "tin", ItemTags.TIN);
        registerMineralRecipes(consumer, StoneMinerals.ALUMINUM, "aluminum", ItemTags.ALUMINUM);
        registerMineralRecipes(consumer, StoneMinerals.ZINC, "zinc", ItemTags.ZINC);

        SingleInputOutputRecipeBuilder.magnetizerRecipe(Ingredient.of(Tags.Items.INGOTS_IRON), StoneMinerals.MAGNETITE.INGOT.get()).build(consumer, new ResourceLocation(MODID, "magnetite_from_iron_ingot"));
        registerMineralRecipes(consumer, StoneMinerals.MAGNETITE, "magnetite", ItemTags.MAGNETITE);
        ShapedRecipeBuilder.shaped(StoneMinerals.MAGNETITE_GEAR.get()).define('#', ItemTags.MAGNETITE.INGOTS).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_magnetite_ingot", has(ItemTags.MAGNETITE.INGOTS)).save(consumer, new ResourceLocation(MODID, "magnetite_gear_from_ingots"));

        ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES
        registerGravelMineralRecipes(consumer, GravelMinerals.RUTHENIUM_ITEMS, ItemTags.RUTHENIUM.NUGGETS, ItemTags.RUTHENIUM.INGOTS, GravelMinerals.RUTHENIUM_BLOCK, ItemTags.RUTHENIUM.STORAGE, ItemTags.RUTHENIUM.DUSTS, ItemTags.GRAVEL_ORES_RUTHENIUM, "ruthenium");
        registerGravelMineralRecipes(consumer, GravelMinerals.ZIRCONIUM_ITEMS, ItemTags.ZIRCONIUM.NUGGETS, ItemTags.ZIRCONIUM.INGOTS, GravelMinerals.ZIRCONIUM_BLOCK, ItemTags.ZIRCONIUM.STORAGE, ItemTags.ZIRCONIUM.DUSTS, ItemTags.GRAVEL_ORES_ZIRCONIUM, "zirconium");
        registerGravelMineralRecipes(consumer, GravelMinerals.IRIDIUM_ITEMS, ItemTags.IRIDIUM.NUGGETS, ItemTags.IRIDIUM.INGOTS, GravelMinerals.IRIDIUM_BLOCK, ItemTags.IRIDIUM.STORAGE, ItemTags.IRIDIUM.DUSTS, ItemTags.GRAVEL_ORES_IRIDIUM, "iridium");

        ////////////////////////////////////////////////////////////////// ALLOYS
        AlloyRecipeBuilder.alloyRecipe(Registering.BRASS_ITEMS.INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 3).addIngredient(Ingredient.of(ItemTags.ZINC.INGOTS), 2).build(consumer);
        registerCommonMineralsRecipes(consumer, Registering.BRASS_ITEMS, ItemTags.BRASS.NUGGETS, ItemTags.BRASS.INGOTS, Registering.BRASS_BLOCK, ItemTags.BRASS.STORAGE, ItemTags.BRASS.DUSTS, "brass");

        AlloyRecipeBuilder.alloyRecipe(Registering.BRONZE_ITEMS.INGOT.get(), 5).addIngredient(Ingredient.of(ItemTags.INGOTS_COPPER), 4).addIngredient(Ingredient.of(ItemTags.TIN.INGOTS)).build(consumer);
        registerCommonMineralsRecipes(consumer, Registering.BRONZE_ITEMS, ItemTags.BRONZE.NUGGETS, ItemTags.BRONZE.INGOTS, Registering.BRONZE_BLOCK, ItemTags.BRONZE.STORAGE, ItemTags.BRONZE.DUSTS, "bronze");
    }

    private void registerMineralRecipes(Consumer<FinishedRecipe> consumer, StoneMinerals.RegistryStoneMetalSet registryMetalSet, String name, ItemTags.StoneMetalTagSet stoneMetalTagSet) {
        SingleInputOutputRecipeBuilder.crushingRecipe(Ingredient.of(stoneMetalTagSet.RAW_ORES), registryMetalSet.DUST.get(), 2).build(consumer, new ResourceLocation(MODID, name + "_dust_from_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(stoneMetalTagSet.ORES), registryMetalSet.INGOT.get(), 0.7F, 100).unlockedBy("has_" + name + "_ore", has(stoneMetalTagSet.ORES)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_blasting_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(stoneMetalTagSet.ORES), registryMetalSet.INGOT.get(), 0.7F, 200).unlockedBy("has_" + name + "_ore", has(stoneMetalTagSet.ORES)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_smelting_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(stoneMetalTagSet.RAW_ORES), registryMetalSet.INGOT.get(), 0.7F, 100).unlockedBy("has_raw_" + name, has(stoneMetalTagSet.RAW_ORES)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_blasting_raw_ore"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(stoneMetalTagSet.RAW_ORES), registryMetalSet.INGOT.get(), 0.7F, 200).unlockedBy("has_raw_" + name, has(stoneMetalTagSet.RAW_ORES)).save(consumer, new ResourceLocation(MODID, name + "_ingot_from_smelting_raw_ore"));

        registerCommonMineralsRecipes(consumer, registryMetalSet, stoneMetalTagSet.NUGGETS, stoneMetalTagSet.INGOTS, registryMetalSet.BLOCK, stoneMetalTagSet.STORAGE, stoneMetalTagSet.DUSTS, name);
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
