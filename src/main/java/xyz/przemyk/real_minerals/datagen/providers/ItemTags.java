package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.GravelMinerals;
import xyz.przemyk.real_minerals.init.ObsidianMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.init.StoneMinerals;

import javax.annotation.Nullable;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    //<editor-fold desc="Metals with stone ores">
    public static final Tags.IOptionalNamedTag<Item> STORAGE_COPPER = tag("storage_blocks/copper");
    public static final Tags.IOptionalNamedTag<Item> ORES_COPPER = tag("ores/copper");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_COPPER = tag("ingots/copper");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_COPPER = tag("nuggets/copper");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_COPPER = tag("dusts/copper");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_LEAD = tag("storage_blocks/lead");
    public static final Tags.IOptionalNamedTag<Item> ORES_LEAD = tag("ores/lead");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_LEAD = tag("ingots/lead");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_LEAD = tag("nuggets/lead");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_LEAD = tag("dusts/lead");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_MAGNESIUM = tag("storage_blocks/magnesium");
    public static final Tags.IOptionalNamedTag<Item> ORES_MAGNESIUM = tag("ores/magnesium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_MAGNESIUM = tag("ingots/magnesium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_MAGNESIUM = tag("nuggets/magnesium");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_MAGNESIUM = tag("dusts/magnesium");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_NICKEL = tag("storage_blocks/nickel");
    public static final Tags.IOptionalNamedTag<Item> ORES_NICKEL = tag("ores/nickel");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_NICKEL = tag("ingots/nickel");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_NICKEL = tag("nuggets/nickel");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_NICKEL = tag("dusts/nickel");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_PLATINUM = tag("storage_blocks/platinum");
    public static final Tags.IOptionalNamedTag<Item> ORES_PLATINUM = tag("ores/platinum");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_PLATINUM = tag("ingots/platinum");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_PLATINUM = tag("nuggets/platinum");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_PLATINUM = tag("dusts/platinum");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_SILVER = tag("storage_blocks/silver");
    public static final Tags.IOptionalNamedTag<Item> ORES_SILVER = tag("ores/silver");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_SILVER = tag("ingots/silver");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_SILVER = tag("nuggets/silver");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_SILVER = tag("dusts/silver");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_TIN = tag("storage_blocks/tin");
    public static final Tags.IOptionalNamedTag<Item> ORES_TIN = tag("ores/tin");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_TIN = tag("ingots/tin");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_TIN = tag("nuggets/tin");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_TIN = tag("dusts/tin");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_ALUMINUM = tag("storage_blocks/aluminum");
    public static final Tags.IOptionalNamedTag<Item> ORES_ALUMINUM = tag("ores/aluminum");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_ALUMINUM = tag("ingots/aluminum");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_ALUMINUM = tag("nuggets/aluminum");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_ALUMINUM = tag("dusts/aluminum");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_ZINC = tag("storage_blocks/zinc");
    public static final Tags.IOptionalNamedTag<Item> ORES_ZINC = tag("ores/zinc");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_ZINC = tag("ingots/zinc");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_ZINC = tag("nuggets/zinc");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_ZINC = tag("dusts/zinc");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_MAGNETITE = tag("storage_blocks/magnetite");
    public static final Tags.IOptionalNamedTag<Item> ORES_MAGNETITE = tag("ores/magnetite");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_MAGNETITE = tag("ingots/magnetite");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_MAGNETITE = tag("nuggets/magnetite");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_MAGNETITE = tag("dusts/magnetite");
    public static final Tags.IOptionalNamedTag<Item> GEAR_MAGNETITE = tag("gear/magnetite");
    //</editor-fold>

    //<editor-fold desc="Metals with gravel ores">
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_GOLD = tag("gravel_ores/gold");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_PLATINUM = tag("gravel_ores/platinum");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_RUTHENIUM = tag("storage_blocks/ruthenium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_RUTHENIUM = tag("gravel_ores/ruthenium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_RUTHENIUM = tag("ingots/ruthenium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_RUTHENIUM = tag("nuggets/ruthenium");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_RUTHENIUM = tag("dusts/ruthenium");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_ZIRCONIUM = tag("storage_blocks/zirconium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_ZIRCONIUM = tag("gravel_ores/zirconium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_ZIRCONIUM = tag("ingots/zirconium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_ZIRCONIUM = tag("nuggets/zirconium");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_ZIRCONIUM = tag("dusts/zirconium");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_IRIDIUM = tag("storage_blocks/iridium");
    public static final Tags.IOptionalNamedTag<Item> GRAVEL_ORES_IRIDIUM = tag("gravel_ores/iridium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_IRIDIUM = tag("ingots/iridium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_IRIDIUM = tag("nuggets/iridium");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_IRIDIUM = tag("dusts/iridium");
    //</editor-fold>

    //<editor-fold desc="Metals with obsidian ores">
    public static final Tags.IOptionalNamedTag<Item> STORAGE_TUNGSTEN = tag("storage_blocks/tungsten");
    public static final Tags.IOptionalNamedTag<Item> OBSIDIAN_ORES_TUNGSTEN = tag("obsidian_ores/tungsten");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_TUNGSTEN = tag("ingots/tungsten");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_TUNGSTEN = tag("nuggets/tungsten");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_TUNGSTEN = tag("dusts/tungsten");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_RHENIUM = tag("storage_blocks/rhenium");
    public static final Tags.IOptionalNamedTag<Item> OBSIDIAN_ORES_RHENIUM = tag("obsidian_ores/rhenium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_RHENIUM = tag("ingots/rhenium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_RHENIUM = tag("nuggets/rhenium");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_RHENIUM = tag("dusts/rhenium");
    //</editor-fold>

    //<editor-fold desc="Alloys">
    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRASS = tag("storage_blocks/brass");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_BRASS = tag("ingots/brass");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_BRASS = tag("nuggets/brass");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_BRASS = tag("dusts/brass");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRONZE = tag("storage_blocks/bronze");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_BRONZE = tag("ingots/bronze");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_BRONZE = tag("nuggets/bronze");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_BRONZE = tag("dusts/bronze");
    //</editor-fold>

    @Override
    protected void addTags() {

        //<editor-fold desc="Metals with stone ores">
        copy(BlockTags.STORAGE_COPPER, STORAGE_COPPER);
        copy(BlockTags.ORES_COPPER, ORES_COPPER);
        tag(INGOTS_COPPER).add(StoneMinerals.COPPER_ITEMS.INGOT.get());
        tag(NUGGETS_COPPER).add(StoneMinerals.COPPER_ITEMS.NUGGET.get());
        tag(DUSTS_COPPER).add(StoneMinerals.COPPER_ITEMS.INGOT.get());

        copy(BlockTags.STORAGE_LEAD, STORAGE_LEAD);
        copy(BlockTags.ORES_LEAD, ORES_LEAD);
        tag(INGOTS_LEAD).add(StoneMinerals.LEAD_ITEMS.INGOT.get());
        tag(NUGGETS_LEAD).add(StoneMinerals.LEAD_ITEMS.NUGGET.get());
        tag(DUSTS_LEAD).add(StoneMinerals.LEAD_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_MAGNESIUM, STORAGE_MAGNESIUM);
        copy(BlockTags.ORES_MAGNESIUM, ORES_MAGNESIUM);
        tag(INGOTS_MAGNESIUM).add(StoneMinerals.MAGNESIUM_ITEMS.INGOT.get());
        tag(NUGGETS_MAGNESIUM).add(StoneMinerals.MAGNESIUM_ITEMS.NUGGET.get());
        tag(DUSTS_MAGNESIUM).add(StoneMinerals.MAGNESIUM_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_NICKEL, STORAGE_NICKEL);
        copy(BlockTags.ORES_NICKEL, ORES_NICKEL);
        tag(INGOTS_NICKEL).add(StoneMinerals.NICKEL_ITEMS.INGOT.get());
        tag(NUGGETS_NICKEL).add(StoneMinerals.NICKEL_ITEMS.NUGGET.get());
        tag(DUSTS_NICKEL).add(StoneMinerals.NICKEL_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_PLATINUM, STORAGE_PLATINUM);
        copy(BlockTags.ORES_PLATINUM, ORES_PLATINUM);
        tag(INGOTS_PLATINUM).add(StoneMinerals.PLATINUM_ITEMS.INGOT.get());
        tag(NUGGETS_PLATINUM).add(StoneMinerals.PLATINUM_ITEMS.NUGGET.get());
        tag(DUSTS_PLATINUM).add(StoneMinerals.PLATINUM_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_SILVER, STORAGE_SILVER);
        copy(BlockTags.ORES_SILVER, ORES_SILVER);
        tag(INGOTS_SILVER).add(StoneMinerals.SILVER_ITEMS.INGOT.get());
        tag(NUGGETS_SILVER).add(StoneMinerals.SILVER_ITEMS.NUGGET.get());
        tag(DUSTS_SILVER).add(StoneMinerals.SILVER_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_TIN, STORAGE_TIN);
        copy(BlockTags.ORES_TIN, ORES_TIN);
        tag(INGOTS_TIN).add(StoneMinerals.TIN_ITEMS.INGOT.get());
        tag(NUGGETS_TIN).add(StoneMinerals.TIN_ITEMS.NUGGET.get());
        tag(DUSTS_TIN).add(StoneMinerals.TIN_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_ALUMINUM, STORAGE_ALUMINUM);
        copy(BlockTags.ORES_ALUMINUM, ORES_ALUMINUM);
        tag(INGOTS_ALUMINUM).add(StoneMinerals.ALUMINUM_ITEMS.INGOT.get());
        tag(NUGGETS_ALUMINUM).add(StoneMinerals.ALUMINUM_ITEMS.NUGGET.get());
        tag(DUSTS_ALUMINUM).add(StoneMinerals.ALUMINUM_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_ZINC, STORAGE_ZINC);
        copy(BlockTags.ORES_ZINC, ORES_ZINC);
        tag(INGOTS_ZINC).add(StoneMinerals.ZINC_ITEMS.INGOT.get());
        tag(NUGGETS_ZINC).add(StoneMinerals.ZINC_ITEMS.NUGGET.get());
        tag(DUSTS_ZINC).add(StoneMinerals.ZINC_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_MAGNETITE, STORAGE_MAGNETITE);
        copy(BlockTags.ORES_MAGNETITE, ORES_MAGNETITE);
        tag(INGOTS_MAGNETITE).add(StoneMinerals.MAGNETITE_ITEMS.INGOT.get());
        tag(NUGGETS_MAGNETITE).add(StoneMinerals.MAGNETITE_ITEMS.NUGGET.get());
        tag(DUSTS_MAGNETITE).add(StoneMinerals.MAGNETITE_ITEMS.DUST.get());
        tag(GEAR_MAGNETITE).add(StoneMinerals.MAGNETITE_GEAR.get());
        //</editor-fold>

        //<editor-fold desc="Metals with gravel ores">
        copy(BlockTags.GRAVEL_ORES_GOLD, GRAVEL_ORES_GOLD);
        copy(BlockTags.GRAVEL_ORES_PLATINUM, GRAVEL_ORES_PLATINUM);

        copy(BlockTags.STORAGE_RUTHENIUM, STORAGE_RUTHENIUM);
        copy(BlockTags.GRAVEL_ORES_RUTHENIUM, GRAVEL_ORES_RUTHENIUM);
        tag(INGOTS_RUTHENIUM).add(GravelMinerals.RUTHENIUM_ITEMS.INGOT.get());
        tag(NUGGETS_RUTHENIUM).add(GravelMinerals.RUTHENIUM_ITEMS.NUGGET.get());
        tag(DUSTS_RUTHENIUM).add(GravelMinerals.RUTHENIUM_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_ZIRCONIUM, STORAGE_ZIRCONIUM);
        copy(BlockTags.GRAVEL_ORES_ZIRCONIUM, GRAVEL_ORES_ZIRCONIUM);
        tag(INGOTS_ZIRCONIUM).add(GravelMinerals.ZIRCONIUM_ITEMS.INGOT.get());
        tag(NUGGETS_ZIRCONIUM).add(GravelMinerals.ZIRCONIUM_ITEMS.NUGGET.get());
        tag(DUSTS_ZIRCONIUM).add(GravelMinerals.ZIRCONIUM_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_IRIDIUM, STORAGE_IRIDIUM);
        copy(BlockTags.GRAVEL_ORES_IRIDIUM, GRAVEL_ORES_IRIDIUM);
        tag(INGOTS_IRIDIUM).add(GravelMinerals.IRIDIUM_ITEMS.INGOT.get());
        tag(NUGGETS_IRIDIUM).add(GravelMinerals.IRIDIUM_ITEMS.NUGGET.get());
        tag(DUSTS_IRIDIUM).add(GravelMinerals.IRIDIUM_ITEMS.DUST.get());
        //</editor-fold>

        //<editor-fold desc="Metals with obsidian ores">
        copy(BlockTags.STORAGE_TUNGSTEN, STORAGE_TUNGSTEN);
        copy(BlockTags.OBSIDIAN_ORES_TUNGSTEN, OBSIDIAN_ORES_TUNGSTEN);
        tag(INGOTS_TUNGSTEN).add(ObsidianMinerals.TUNGSTEN_ITEMS.INGOT.get());
        tag(NUGGETS_TUNGSTEN).add(ObsidianMinerals.TUNGSTEN_ITEMS.NUGGET.get());
        tag(DUSTS_TUNGSTEN).add(ObsidianMinerals.TUNGSTEN_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_RHENIUM, STORAGE_RHENIUM);
        copy(BlockTags.OBSIDIAN_ORES_RHENIUM, OBSIDIAN_ORES_RHENIUM);
        tag(INGOTS_RHENIUM).add(ObsidianMinerals.RHENIUM_ITEMS.INGOT.get());
        tag(NUGGETS_RHENIUM).add(ObsidianMinerals.RHENIUM_ITEMS.NUGGET.get());
        tag(DUSTS_RHENIUM).add(ObsidianMinerals.RHENIUM_ITEMS.DUST.get());
        //</editor-fold>

        //<editor-fold desc="Alloys">
        copy(BlockTags.STORAGE_BRASS, STORAGE_BRASS);
        tag(INGOTS_BRASS).add(Registering.BRASS_ITEMS.INGOT.get());
        tag(NUGGETS_BRASS).add(Registering.BRASS_ITEMS.NUGGET.get());
        tag(DUSTS_BRASS).add(Registering.BRASS_ITEMS.DUST.get());

        copy(BlockTags.STORAGE_BRONZE, STORAGE_BRONZE);
        tag(INGOTS_BRONZE).add(Registering.BRONZE_ITEMS.INGOT.get());
        tag(NUGGETS_BRONZE).add(Registering.BRONZE_ITEMS.NUGGET.get());
        tag(DUSTS_BRONZE).add(Registering.BRONZE_ITEMS.DUST.get());
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
