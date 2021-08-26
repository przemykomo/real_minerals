package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.*;

import javax.annotation.Nullable;

public class BlockTags extends BlockTagsProvider {
    public BlockTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    //<editor-fold desc="Metals with stone ores">
    public static final Tags.IOptionalNamedTag<Block> STORAGE_COPPER = tag("storage_blocks/copper");
    public static final Tags.IOptionalNamedTag<Block> ORES_COPPER = tag("ores/copper");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_LEAD = tag("storage_blocks/lead");
    public static final Tags.IOptionalNamedTag<Block> ORES_LEAD = tag("ores/lead");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_MAGNESIUM = tag("storage_blocks/magnesium");
    public static final Tags.IOptionalNamedTag<Block> ORES_MAGNESIUM = tag("ores/magnesium");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_NICKEL = tag("storage_blocks/nickel");
    public static final Tags.IOptionalNamedTag<Block> ORES_NICKEL = tag("ores/nickel");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_SILVER = tag("storage_blocks/silver");
    public static final Tags.IOptionalNamedTag<Block> ORES_SILVER = tag("ores/silver");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_TIN = tag("storage_blocks/tin");
    public static final Tags.IOptionalNamedTag<Block> ORES_TIN = tag("ores/tin");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_ALUMINUM = tag("storage_blocks/aluminum");
    public static final Tags.IOptionalNamedTag<Block> ORES_ALUMINUM = tag("ores/aluminum");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_ZINC = tag("storage_blocks/zinc");
    public static final Tags.IOptionalNamedTag<Block> ORES_ZINC = tag("ores/zinc");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_MAGNETITE = tag("storage_blocks/magnetite");
    public static final Tags.IOptionalNamedTag<Block> ORES_MAGNETITE = tag("ores/magnetite");

    public static final Tags.IOptionalNamedTag<Block> ORES_SULFUR = tag("ores/sulfur");
    //</editor-fold>

    //<editor-fold desc="Metals with gravel ores">
    public static final Tags.IOptionalNamedTag<Block> GRAVEL_ORES_GOLD = tag("gravel_ores/gold");
    public static final Tags.IOptionalNamedTag<Block> GRAVEL_ORES_PLATINUM = tag("gravel_ores/platinum");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_RUTHENIUM = tag("storage_blocks/ruthenium");
    public static final Tags.IOptionalNamedTag<Block> GRAVEL_ORES_RUTHENIUM = tag("gravel_ores/ruthenium");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_ZIRCONIUM = tag("storage_blocks/zirconium");
    public static final Tags.IOptionalNamedTag<Block> GRAVEL_ORES_ZIRCONIUM = tag("gravel_ores/zirconium");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_IRIDIUM = tag("storage_blocks/iridium");
    public static final Tags.IOptionalNamedTag<Block> GRAVEL_ORES_IRIDIUM = tag("gravel_ores/iridium");
    //</editor-fold>

    //<editor-fold desc="Metals with obsidian ores">
    public static final Tags.IOptionalNamedTag<Block> STORAGE_TUNGSTEN = tag("storage_blocks/tungsten");
    public static final Tags.IOptionalNamedTag<Block> OBSIDIAN_ORES_TUNGSTEN = tag("obsidian_ores/tungsten");

    public static final Tags.IOptionalNamedTag<Block> STORAGE_RHENIUM = tag("storage_blocks/rhenium");
    public static final Tags.IOptionalNamedTag<Block> OBSIDIAN_ORES_RHENIUM = tag("obsidian_ores/rhenium");
    //</editor-fold>

    //<editor-fold desc="Alloys">
    public static final Tags.IOptionalNamedTag<Block> STORAGE_BRASS = tag("storage_blocks/brass");
    public static final Tags.IOptionalNamedTag<Block> STORAGE_BRONZE = tag("storage_blocks/bronze");
    //</editor-fold>

    @Override
    protected void addTags() {

        //<editor-fold desc="Metals with stone ores">
        tag(STORAGE_COPPER).add(Blocks.COPPER_BLOCK);
        tag(ORES_COPPER).add(Blocks.COPPER_ORE);

        tag(STORAGE_LEAD).add(StoneMinerals.LEAD_BLOCK.BLOCK.get());
        tag(ORES_LEAD).add(StoneMinerals.LEAD_ORE.BLOCK.get());

        tag(STORAGE_MAGNESIUM).add(StoneMinerals.MAGNESIUM_BLOCK.BLOCK.get());
        tag(ORES_MAGNESIUM).add(StoneMinerals.MAGNESIUM_ORE.BLOCK.get());

        tag(STORAGE_NICKEL).add(StoneMinerals.NICKEL_BLOCK.BLOCK.get());
        tag(ORES_NICKEL).add(StoneMinerals.NICKEL_ORE.BLOCK.get());

        tag(STORAGE_SILVER).add(StoneMinerals.SILVER_BLOCK.BLOCK.get());
        tag(ORES_SILVER).add(StoneMinerals.SILVER_ORE.BLOCK.get());

        tag(STORAGE_TIN).add(StoneMinerals.TIN_BLOCK.BLOCK.get());
        tag(ORES_TIN).add(StoneMinerals.TIN_ORE.BLOCK.get());

        tag(STORAGE_ALUMINUM).add(StoneMinerals.ALUMINUM_BLOCK.BLOCK.get());
        tag(ORES_ALUMINUM).add(StoneMinerals.ALUMINUM_ORE.BLOCK.get());

        tag(STORAGE_ZINC).add(StoneMinerals.ZINC_BLOCK.BLOCK.get());
        tag(ORES_ZINC).add(StoneMinerals.ZINC_ORE.BLOCK.get());

        tag(STORAGE_MAGNETITE).add(StoneMinerals.MAGNETITE_BLOCK.BLOCK.get());
        tag(ORES_MAGNETITE).add(StoneMinerals.MAGNETITE_ORE.BLOCK.get());

        tag(ORES_SULFUR).add(StoneMinerals.SULFUR_ORE.BLOCK.get());
        //</editor-fold>

        //<editor-fold desc="Metals with gravel ores">
        tag(GRAVEL_ORES_GOLD).add(GravelMinerals.GOLD_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_RUTHENIUM).add(GravelMinerals.RUTHENIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_RUTHENIUM).add(GravelMinerals.RUTHENIUM_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_ZIRCONIUM).add(GravelMinerals.ZIRCONIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_ZIRCONIUM).add(GravelMinerals.ZIRCONIUM_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_IRIDIUM).add(GravelMinerals.IRIDIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_IRIDIUM).add(GravelMinerals.IRIDIUM_GRAVEL_ORE.BLOCK.get());
        //</editor-fold>

        //<editor-fold desc="Metals with obsidian ores">
        tag(STORAGE_TUNGSTEN).add(ObsidianMinerals.TUNGSTEN_BLOCK.BLOCK.get());
        tag(OBSIDIAN_ORES_TUNGSTEN).add(ObsidianMinerals.TUNGSTEN_OBSIDIAN_ORE.BLOCK.get());

        tag(STORAGE_RHENIUM).add(ObsidianMinerals.RHENIUM_BLOCK.BLOCK.get());
        tag(OBSIDIAN_ORES_RHENIUM).add(ObsidianMinerals.RHENIUM_OBSIDIAN_ORE.BLOCK.get());
        //</editor-fold>

        //<editor-fold desc="Alloys">
        tag(STORAGE_BRASS).add(Registering.BRASS_BLOCK.BLOCK.get());
        tag(STORAGE_BRONZE).add(Registering.BRONZE_BLOCK.BLOCK.get());
        //</editor-fold>

        tag(net.minecraft.tags.BlockTags.BEACON_BASE_BLOCKS).add(
                StoneMinerals.LEAD_BLOCK.BLOCK.get(), StoneMinerals.MAGNESIUM_BLOCK.BLOCK.get(),
                StoneMinerals.NICKEL_BLOCK.BLOCK.get(),
                StoneMinerals.SILVER_BLOCK.BLOCK.get(), StoneMinerals.TIN_BLOCK.BLOCK.get(),
                StoneMinerals.ALUMINUM_BLOCK.BLOCK.get(), StoneMinerals.ZINC_BLOCK.BLOCK.get(),
                Registering.BRASS_BLOCK.BLOCK.get(), Registering.BRONZE_BLOCK.BLOCK.get(),
                GravelMinerals.RUTHENIUM_BLOCK.BLOCK.get(), GravelMinerals.ZIRCONIUM_BLOCK.BLOCK.get(),
                GravelMinerals.IRIDIUM_BLOCK.BLOCK.get()
        );

        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            if (blockRegistryObject.BLOCK.getId().getPath().contains("gravel")) {
                tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL).add(blockRegistryObject.BLOCK.get());
            } else {
                tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(blockRegistryObject.BLOCK.get());
            }
        }

        tag(net.minecraft.tags.BlockTags.NEEDS_STONE_TOOL).add(
                StoneMinerals.TIN_BLOCK.BLOCK.get(), StoneMinerals.TIN_ORE.BLOCK.get(),
                StoneMinerals.ALUMINUM_BLOCK.BLOCK.get(), StoneMinerals.ALUMINUM_ORE.BLOCK.get(),
                StoneMinerals.ZINC_BLOCK.BLOCK.get(), StoneMinerals.ZINC_ORE.BLOCK.get(),
                StoneMinerals.MAGNETITE_BLOCK.BLOCK.get(), StoneMinerals.MAGNETITE_ORE.BLOCK.get(),
                StoneMinerals.SULFUR_ORE.BLOCK.get());

        tag(net.minecraft.tags.BlockTags.NEEDS_IRON_TOOL).add(
                StoneMinerals.LEAD_BLOCK.BLOCK.get(), StoneMinerals.LEAD_ORE.BLOCK.get(),
                StoneMinerals.NICKEL_BLOCK.BLOCK.get(), StoneMinerals.NICKEL_ORE.BLOCK.get(),
                StoneMinerals.MAGNESIUM_BLOCK.BLOCK.get(), StoneMinerals.MAGNESIUM_ORE.BLOCK.get(),
                StoneMinerals.SILVER_BLOCK.BLOCK.get(), StoneMinerals.SILVER_ORE.BLOCK.get()
        );

        tag(net.minecraft.tags.BlockTags.NEEDS_DIAMOND_TOOL).add(
            Registering.METEORITE.BLOCK.get()
        );
    }

    private static Tags.IOptionalNamedTag<Block> tag(String name) {
        return net.minecraft.tags.BlockTags.createOptional(new ResourceLocation("forge", name));
    }

    @Override
    public String getName() {
        return modId + " block tags";
    }
}
