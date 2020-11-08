package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nullable;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    public static final Tags.IOptionalNamedTag<Item> ORES_COPPER = tag("ores/copper");

    @Override
    protected void registerTags() {
        copy(BlockTags.STORAGE_COPPER, tag("storage_blocks/copper"));
        copy(BlockTags.ORES_COPPER, ORES_COPPER);
        getOrCreateBuilder(tag("ingots/copper")).add(RealMinerals.COPPER_INGOT.get());
        getOrCreateBuilder(tag("muggets/copper")).add(RealMinerals.COPPER_NUGGET.get());
        getOrCreateBuilder(tag("dusts/copper")).add(RealMinerals.COPPER_DUST.get());

        copy(BlockTags.STORAGE_LEAD, tag("storage_blocks/lead"));
        copy(BlockTags.ORES_LEAD, tag("ores/lead"));
        getOrCreateBuilder(tag("ingots/lead")).add(RealMinerals.LEAD_INGOT.get());
        getOrCreateBuilder(tag("muggets/lead")).add(RealMinerals.LEAD_NUGGET.get());
        getOrCreateBuilder(tag("dusts/lead")).add(RealMinerals.LEAD_DUST.get());

        copy(BlockTags.STORAGE_MAGNESIUM, tag("storage_blocks/magnesium"));
        copy(BlockTags.ORES_MAGNESIUM, tag("ores/magnesium"));
        getOrCreateBuilder(tag("ingots/magnesium")).add(RealMinerals.MAGNESIUM_INGOT.get());
        getOrCreateBuilder(tag("muggets/magnesium")).add(RealMinerals.MAGNESIUM_NUGGET.get());
        getOrCreateBuilder(tag("dusts/magnesium")).add(RealMinerals.MAGNESIUM_DUST.get());

        copy(BlockTags.STORAGE_NICKEL, tag("storage_blocks/nickel"));
        copy(BlockTags.ORES_NICKEL, tag("ores/nickel"));
        getOrCreateBuilder(tag("ingots/nickel")).add(RealMinerals.NICKEL_INGOT.get());
        getOrCreateBuilder(tag("muggets/nickel")).add(RealMinerals.NICKEL_NUGGET.get());
        getOrCreateBuilder(tag("dusts/nickel")).add(RealMinerals.NICKEL_DUST.get());

        copy(BlockTags.STORAGE_PLATINUM, tag("storage_blocks/platinum"));
        copy(BlockTags.ORES_PLATINUM, tag("ores/platinum"));
        getOrCreateBuilder(tag("ingots/platinum")).add(RealMinerals.PLATINUM_INGOT.get());
        getOrCreateBuilder(tag("muggets/platinum")).add(RealMinerals.PLATINUM_NUGGET.get());
        getOrCreateBuilder(tag("dusts/platinum")).add(RealMinerals.PLATINUM_DUST.get());

        copy(BlockTags.STORAGE_SILVER, tag("storage_blocks/silver"));
        copy(BlockTags.ORES_SILVER, tag("ores/silver"));
        getOrCreateBuilder(tag("ingots/silver")).add(RealMinerals.SILVER_INGOT.get());
        getOrCreateBuilder(tag("muggets/silver")).add(RealMinerals.SILVER_NUGGET.get());
        getOrCreateBuilder(tag("dusts/silver")).add(RealMinerals.SILVER_DUST.get());

        copy(BlockTags.STORAGE_TIN, tag("storage_blocks/tin"));
        copy(BlockTags.ORES_TIN, tag("ores/tin"));
        getOrCreateBuilder(tag("ingots/tin")).add(RealMinerals.TIN_INGOT.get());
        getOrCreateBuilder(tag("muggets/tin")).add(RealMinerals.TIN_NUGGET.get());
        getOrCreateBuilder(tag("dusts/tin")).add(RealMinerals.TIN_DUST.get());
    }

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return net.minecraft.tags.ItemTags.createOptional(new ResourceLocation("forge", name));
    }

    @Override
    public String getName() {
        return modId + " item tags";
    }
}
