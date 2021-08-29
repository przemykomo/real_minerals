package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.*;

import javax.annotation.Nullable;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    public static class StoneMetalTagSet extends MetalTagSet {
        public final Tags.IOptionalNamedTag<Item> ORES;
        public final Tags.IOptionalNamedTag<Item> RAW_ORES;

        public StoneMetalTagSet(String name) {
            super(name);
            this.ORES = tag("ores/" + name);
            this.RAW_ORES = tag("raw_ores/" + name);
        }
    }

    public static class MetalTagSet {
        public final Tags.IOptionalNamedTag<Item> STORAGE;
        public final Tags.IOptionalNamedTag<Item> INGOTS;
        public final Tags.IOptionalNamedTag<Item> NUGGETS;
        public final Tags.IOptionalNamedTag<Item> DUSTS;

        public MetalTagSet(String name) {
            this.STORAGE = tag("storage_blocks/" + name);
            this.INGOTS = tag("ingots/" + name);
            this.NUGGETS = tag("nuggets/" + name);
            this.DUSTS = tag("dusts/" + name);
        }
    }

    //<editor-fold desc="Metals with stone ores">
    //todo: remove copper tags when forge adds them
    public static final Tags.IOptionalNamedTag<Item> STORAGE_COPPER = tag("storage_blocks/copper");
    public static final Tags.IOptionalNamedTag<Item> ORES_COPPER = tag("ores/copper");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_COPPER = tag("ingots/copper");

    public static final Tags.IOptionalNamedTag<Item> DUSTS_COPPER = tag("dusts/copper");
    public static final Tags.IOptionalNamedTag<Item> RAW_ORES_COPPER = tag("raw_ores/copper");

    public static final Tags.IOptionalNamedTag<Item> DUSTS_IRON = tag("dusts/iron");
    public static final Tags.IOptionalNamedTag<Item> RAW_ORES_IRON = tag("raw_ores/iron");

    public static final Tags.IOptionalNamedTag<Item> DUSTS_GOLD = tag("dusts/gold");
    public static final Tags.IOptionalNamedTag<Item> RAW_ORES_GOLD = tag("raw_ores/gold");

    public static final StoneMetalTagSet LEAD = new StoneMetalTagSet("lead");
    public static final StoneMetalTagSet MAGNESIUM = new StoneMetalTagSet("magnesium");
    public static final StoneMetalTagSet NICKEL = new StoneMetalTagSet("nickel");
    public static final StoneMetalTagSet SILVER = new StoneMetalTagSet("silver");
    public static final StoneMetalTagSet TIN = new StoneMetalTagSet("tin");
    public static final StoneMetalTagSet ALUMINUM = new StoneMetalTagSet("aluminum");
    public static final StoneMetalTagSet ZINC = new StoneMetalTagSet("zinc");
    public static final StoneMetalTagSet MAGNETITE = new StoneMetalTagSet("magnetite");
    public static final Tags.IOptionalNamedTag<Item> GEAR_MAGNETITE = tag("gear/magnetite");

    public static final Tags.IOptionalNamedTag<Item> ORES_SULFUR = tag("ores/sulfur");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_SULFUR = tag("dusts/sulfur");
    //</editor-fold>

    //<editor-fold desc="Metals with gravel ores">
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_GOLD = tag("gravel_ores/gold");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_PLATINUM = tag("gravel_ores/platinum");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_RUTHENIUM = tag("storage_blocks/ruthenium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_RUTHENIUM = tag("gravel_ores/ruthenium");
    public static final MetalTagSet RUTHENIUM = new MetalTagSet("ruthenium");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_ZIRCONIUM = tag("storage_blocks/zirconium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_ZIRCONIUM = tag("gravel_ores/zirconium");
    public static final MetalTagSet ZIRCONIUM = new MetalTagSet("zirconium");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_IRIDIUM = tag("storage_blocks/iridium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_IRIDIUM = tag("gravel_ores/iridium");
    public static final MetalTagSet IRIDIUM = new MetalTagSet("iridium");
    //</editor-fold>

    //<editor-fold desc="Metals with obsidian ores">
    public static final Tags.IOptionalNamedTag<Item> STORAGE_TUNGSTEN = tag("storage_blocks/tungsten");
    public static final Tags.IOptionalNamedTag<Item> OBSIDIAN_ORES_TUNGSTEN = tag("obsidian_ores/tungsten");
    public static final MetalTagSet TUNGSTEN = new MetalTagSet("tungsten");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_RHENIUM = tag("storage_blocks/rhenium");
    public static final Tags.IOptionalNamedTag<Item> OBSIDIAN_ORES_RHENIUM = tag("obsidian_ores/rhenium");
    public static final MetalTagSet RHENIUM = new MetalTagSet("rhenium");
    //</editor-fold>

    //<editor-fold desc="Alloys">
    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRASS = tag("storage_blocks/brass");
    public static final MetalTagSet BRASS = new MetalTagSet("brass");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRONZE = tag("storage_blocks/bronze");
    public static final MetalTagSet BRONZE = new MetalTagSet("bronze");
    //</editor-fold>

    protected void addStoneMetalItemTags(StoneMetalTagSet stoneMetalTagSet, StoneMinerals.RegistryStoneMetalSet registryStoneMetalSet) {
        addMetalItemTags(stoneMetalTagSet, registryStoneMetalSet);
        tag(stoneMetalTagSet.RAW_ORES).add(registryStoneMetalSet.RAW_ORE.get());
    }

    protected void addMetalItemTags(MetalTagSet metalTagSet, RegistryMetalSet registryMetalSet) {
        tag(metalTagSet.INGOTS).add(registryMetalSet.INGOT.get());
        tag(metalTagSet.NUGGETS).add(registryMetalSet.NUGGET.get());
        tag(metalTagSet.DUSTS).add(registryMetalSet.DUST.get());
    }

    @Override
    protected void addTags() {

        //<editor-fold desc="Metals with stone ores">
        copy(BlockTags.STORAGE_COPPER, STORAGE_COPPER);
        copy(BlockTags.ORES_COPPER, ORES_COPPER);
        tag(INGOTS_COPPER).add(Items.COPPER_INGOT);
        tag(DUSTS_COPPER).add(StoneMinerals.COPPER_DUST.get());
        tag(RAW_ORES_COPPER).add(Items.RAW_COPPER);

        tag(DUSTS_IRON).add(StoneMinerals.IRON_DUST.get());
        tag(RAW_ORES_IRON).add(Items.RAW_IRON);

        tag(DUSTS_GOLD).add(StoneMinerals.GOLD_DUST.get());
        tag(RAW_ORES_GOLD).add(Items.RAW_GOLD);

        copy(BlockTags.STORAGE_LEAD, LEAD.STORAGE);
        copy(BlockTags.ORES_LEAD, LEAD.ORES);
        addStoneMetalItemTags(LEAD, StoneMinerals.LEAD);

        copy(BlockTags.STORAGE_MAGNESIUM, MAGNESIUM.STORAGE);
        copy(BlockTags.ORES_MAGNESIUM, MAGNESIUM.ORES);
        addStoneMetalItemTags(MAGNESIUM, StoneMinerals.MAGNESIUM);

        copy(BlockTags.STORAGE_NICKEL, NICKEL.STORAGE);
        copy(BlockTags.ORES_NICKEL, NICKEL.ORES);
        addStoneMetalItemTags(NICKEL, StoneMinerals.NICKEL);

        copy(BlockTags.STORAGE_SILVER, SILVER.STORAGE);
        copy(BlockTags.ORES_SILVER, SILVER.ORES);
        addStoneMetalItemTags(SILVER, StoneMinerals.SILVER);

        copy(BlockTags.STORAGE_TIN, TIN.STORAGE);
        copy(BlockTags.ORES_TIN, TIN.ORES);
        addStoneMetalItemTags(TIN, StoneMinerals.TIN);

        copy(BlockTags.STORAGE_ALUMINUM, ALUMINUM.STORAGE);
        copy(BlockTags.ORES_ALUMINUM, ALUMINUM.ORES);
        addStoneMetalItemTags(ALUMINUM, StoneMinerals.ALUMINUM);

        copy(BlockTags.STORAGE_ZINC, ZINC.STORAGE);
        copy(BlockTags.ORES_ZINC, ZINC.ORES);
        addStoneMetalItemTags(ZINC, StoneMinerals.ZINC);

        copy(BlockTags.STORAGE_MAGNETITE, MAGNETITE.STORAGE);
        copy(BlockTags.ORES_MAGNETITE, MAGNETITE.ORES);
        addStoneMetalItemTags(MAGNETITE, StoneMinerals.MAGNETITE);
        tag(GEAR_MAGNETITE).add(StoneMinerals.MAGNETITE_GEAR.get());

        copy(BlockTags.ORES_SULFUR, ORES_SULFUR);
        tag(DUSTS_SULFUR).add(StoneMinerals.SULFUR.get());
        //</editor-fold>

        //<editor-fold desc="Metals with gravel ores">
        copy(BlockTags.GRAVEL_ORES_GOLD, GRAVEL_ORES_GOLD);
        copy(BlockTags.GRAVEL_ORES_PLATINUM, GRAVEL_ORES_PLATINUM);

        copy(BlockTags.STORAGE_RUTHENIUM, STORAGE_RUTHENIUM);
        copy(BlockTags.GRAVEL_ORES_RUTHENIUM, GRAVEL_ORES_RUTHENIUM);
        addMetalItemTags(RUTHENIUM, GravelMinerals.RUTHENIUM_ITEMS);

        copy(BlockTags.STORAGE_ZIRCONIUM, STORAGE_ZIRCONIUM);
        copy(BlockTags.GRAVEL_ORES_ZIRCONIUM, GRAVEL_ORES_ZIRCONIUM);
        addMetalItemTags(ZIRCONIUM, GravelMinerals.ZIRCONIUM_ITEMS);

        copy(BlockTags.STORAGE_IRIDIUM, STORAGE_IRIDIUM);
        copy(BlockTags.GRAVEL_ORES_IRIDIUM, GRAVEL_ORES_IRIDIUM);
        addMetalItemTags(IRIDIUM, GravelMinerals.IRIDIUM_ITEMS);
        //</editor-fold>

        //<editor-fold desc="Metals with obsidian ores">
        copy(BlockTags.STORAGE_TUNGSTEN, STORAGE_TUNGSTEN);
        copy(BlockTags.OBSIDIAN_ORES_TUNGSTEN, OBSIDIAN_ORES_TUNGSTEN);
        addMetalItemTags(TUNGSTEN, ObsidianMinerals.TUNGSTEN_ITEMS);

        copy(BlockTags.STORAGE_RHENIUM, STORAGE_RHENIUM);
        copy(BlockTags.OBSIDIAN_ORES_RHENIUM, OBSIDIAN_ORES_RHENIUM);
        addMetalItemTags(RHENIUM, ObsidianMinerals.RHENIUM_ITEMS);
        //</editor-fold>

        //<editor-fold desc="Alloys">
        copy(BlockTags.STORAGE_BRASS, STORAGE_BRASS);
        addMetalItemTags(BRASS, Registering.BRASS_ITEMS);

        copy(BlockTags.STORAGE_BRONZE, STORAGE_BRONZE);
        addMetalItemTags(BRONZE, Registering.BRONZE_ITEMS);
        //</editor-fold>
    }

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", name));
    }

    @Override
    public String getName() {
        return modId + " item tags";
    }
}
