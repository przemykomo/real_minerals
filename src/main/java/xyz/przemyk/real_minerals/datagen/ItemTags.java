package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

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

    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRASS = tag("storage_blocks/brass");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_BRASS = tag("ingots/brass");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_BRASS = tag("nuggets/brass");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_BRASS = tag("dusts/brass");

    public static final Tags.IOptionalNamedTag<Item> STORAGE_BRONZE = tag("storage_blocks/bronze");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_BRONZE = tag("ingots/bronze");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_BRONZE = tag("nuggets/bronze");
    public static final Tags.IOptionalNamedTag<Item> DUSTS_BRONZE = tag("dusts/bronze");

    @Override
    protected void registerTags() {
        copy(BlockTags.STORAGE_COPPER, STORAGE_COPPER);
        copy(BlockTags.ORES_COPPER, ORES_COPPER);
        getOrCreateBuilder(INGOTS_COPPER).add(RealMinerals.COPPER_INGOT.get());
        getOrCreateBuilder(NUGGETS_COPPER).add(RealMinerals.COPPER_NUGGET.get());
        getOrCreateBuilder(DUSTS_COPPER).add(RealMinerals.COPPER_DUST.get());

        copy(BlockTags.STORAGE_LEAD, STORAGE_LEAD);
        copy(BlockTags.ORES_LEAD, ORES_LEAD);
        getOrCreateBuilder(INGOTS_LEAD).add(RealMinerals.LEAD_INGOT.get());
        getOrCreateBuilder(NUGGETS_LEAD).add(RealMinerals.LEAD_NUGGET.get());
        getOrCreateBuilder(DUSTS_LEAD).add(RealMinerals.LEAD_DUST.get());

        copy(BlockTags.STORAGE_MAGNESIUM, STORAGE_MAGNESIUM);
        copy(BlockTags.ORES_MAGNESIUM, ORES_MAGNESIUM);
        getOrCreateBuilder(INGOTS_MAGNESIUM).add(RealMinerals.MAGNESIUM_INGOT.get());
        getOrCreateBuilder(NUGGETS_MAGNESIUM).add(RealMinerals.MAGNESIUM_NUGGET.get());
        getOrCreateBuilder(DUSTS_MAGNESIUM).add(RealMinerals.MAGNESIUM_DUST.get());

        copy(BlockTags.STORAGE_NICKEL, STORAGE_NICKEL);
        copy(BlockTags.ORES_NICKEL, ORES_NICKEL);
        getOrCreateBuilder(INGOTS_NICKEL).add(RealMinerals.NICKEL_INGOT.get());
        getOrCreateBuilder(NUGGETS_NICKEL).add(RealMinerals.NICKEL_NUGGET.get());
        getOrCreateBuilder(DUSTS_NICKEL).add(RealMinerals.NICKEL_DUST.get());

        copy(BlockTags.STORAGE_PLATINUM, STORAGE_PLATINUM);
        copy(BlockTags.ORES_PLATINUM, ORES_PLATINUM);
        getOrCreateBuilder(INGOTS_PLATINUM).add(RealMinerals.PLATINUM_INGOT.get());
        getOrCreateBuilder(NUGGETS_PLATINUM).add(RealMinerals.PLATINUM_NUGGET.get());
        getOrCreateBuilder(DUSTS_PLATINUM).add(RealMinerals.PLATINUM_DUST.get());

        copy(BlockTags.STORAGE_SILVER, STORAGE_SILVER);
        copy(BlockTags.ORES_SILVER, ORES_SILVER);
        getOrCreateBuilder(INGOTS_SILVER).add(RealMinerals.SILVER_INGOT.get());
        getOrCreateBuilder(NUGGETS_SILVER).add(RealMinerals.SILVER_NUGGET.get());
        getOrCreateBuilder(DUSTS_SILVER).add(RealMinerals.SILVER_DUST.get());

        copy(BlockTags.STORAGE_TIN, STORAGE_TIN);
        copy(BlockTags.ORES_TIN, ORES_TIN);
        getOrCreateBuilder(INGOTS_TIN).add(RealMinerals.TIN_INGOT.get());
        getOrCreateBuilder(NUGGETS_TIN).add(RealMinerals.TIN_NUGGET.get());
        getOrCreateBuilder(DUSTS_TIN).add(RealMinerals.TIN_DUST.get());

        copy(BlockTags.STORAGE_ALUMINUM, STORAGE_ALUMINUM);
        copy(BlockTags.ORES_ALUMINUM, ORES_ALUMINUM);
        getOrCreateBuilder(INGOTS_ALUMINUM).add(RealMinerals.ALUMINUM_INGOT.get());
        getOrCreateBuilder(NUGGETS_ALUMINUM).add(RealMinerals.ALUMINUM_NUGGET.get());
        getOrCreateBuilder(DUSTS_ALUMINUM).add(RealMinerals.ALUMINUM_DUST.get());

        copy(BlockTags.STORAGE_ZINC, STORAGE_ZINC);
        copy(BlockTags.ORES_ZINC, ORES_ZINC);
        getOrCreateBuilder(INGOTS_ZINC).add(RealMinerals.ZINC_INGOT.get());
        getOrCreateBuilder(NUGGETS_ZINC).add(RealMinerals.ZINC_NUGGET.get());
        getOrCreateBuilder(DUSTS_ZINC).add(RealMinerals.ZINC_DUST.get());

        copy(BlockTags.STORAGE_BRASS, STORAGE_BRASS);
        getOrCreateBuilder(INGOTS_BRASS).add(RealMinerals.BRASS_INGOT.get());
        getOrCreateBuilder(NUGGETS_BRASS).add(RealMinerals.BRASS_NUGGET.get());
        getOrCreateBuilder(DUSTS_BRASS).add(RealMinerals.BRASS_DUST.get());

        copy(BlockTags.STORAGE_BRONZE, STORAGE_BRONZE);
        getOrCreateBuilder(INGOTS_BRONZE).add(RealMinerals.BRONZE_INGOT.get());
        getOrCreateBuilder(NUGGETS_BRONZE).add(RealMinerals.BRONZE_NUGGET.get());
        getOrCreateBuilder(DUSTS_BRONZE).add(RealMinerals.BRONZE_DUST.get());

    }

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", name));
    }

    @Override
    public String getName() {
        return modId + " item tags";
    }
}
