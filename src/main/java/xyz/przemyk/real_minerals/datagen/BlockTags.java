package xyz.przemyk.real_minerals.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    public static final Tags.IOptionalNamedTag<Block> STORAGE_COPPER = tag("storage_blocks/copper");
    public static final Tags.IOptionalNamedTag<Block> ORES_COPPER = tag("ores/copper");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_LEAD = tag("storage_blocks/lead");
    public static final Tags.IOptionalNamedTag<Block> ORES_LEAD = tag("ores/lead");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_MAGNESIUM = tag("storage_blocks/magnesium");
    public static final Tags.IOptionalNamedTag<Block> ORES_MAGNESIUM = tag("ores/magnesium");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_NICKEL = tag("storage_blocks/nickel");
    public static final Tags.IOptionalNamedTag<Block> ORES_NICKEL = tag("ores/nickel");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_PLATINUM = tag("storage_blocks/platinum");
    public static final Tags.IOptionalNamedTag<Block> ORES_PLATINUM = tag("ores/platinum");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_SILVER = tag("storage_blocks/silver");
    public static final Tags.IOptionalNamedTag<Block> ORES_SILVER = tag("ores/silver");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_TIN = tag("storage_blocks/tin");
    public static final Tags.IOptionalNamedTag<Block> ORES_TIN = tag("ores/tin");

    @Override
    protected void registerTags() {
        getOrCreateBuilder(STORAGE_COPPER).add(RealMinerals.COPPER_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_COPPER).add(RealMinerals.COPPER_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_LEAD).add(RealMinerals.LEAD_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_LEAD).add(RealMinerals.LEAD_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_MAGNESIUM).add(RealMinerals.MAGNESIUM_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_MAGNESIUM).add(RealMinerals.MAGNESIUM_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_NICKEL).add(RealMinerals.NICKEL_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_NICKEL).add(RealMinerals.NICKEL_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_PLATINUM).add(RealMinerals.PLATINUM_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_PLATINUM).add(RealMinerals.PLATINUM_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_SILVER).add(RealMinerals.SILVER_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_SILVER).add(RealMinerals.SILVER_ORE.BLOCK.get());

        getOrCreateBuilder(STORAGE_TIN).add(RealMinerals.TIN_BLOCK.BLOCK.get());
        getOrCreateBuilder(ORES_TIN).add(RealMinerals.TIN_ORE.BLOCK.get());

        getOrCreateBuilder(net.minecraft.tags.BlockTags.BEACON_BASE_BLOCKS).add(RealMinerals.COPPER_BLOCK.BLOCK.get(), RealMinerals.LEAD_BLOCK.BLOCK.get(), RealMinerals.MAGNESIUM_BLOCK.BLOCK.get(), RealMinerals.NICKEL_BLOCK.BLOCK.get(), RealMinerals.PLATINUM_BLOCK.BLOCK.get(), RealMinerals.SILVER_BLOCK.BLOCK.get(), RealMinerals.TIN_BLOCK.BLOCK.get());
    }

    private static Tags.IOptionalNamedTag<Block> tag(String name) {
        return net.minecraft.tags.BlockTags.createOptional(new ResourceLocation("forge", name));
    }

    @Override
    public String getName() {
        return modId + " block tags";
    }
}
