package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;

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

    public static final Tags.IOptionalNamedTag<Block> STORAGE_PLATINUM = tag("storage_blocks/platinum");
    public static final Tags.IOptionalNamedTag<Block> ORES_PLATINUM = tag("ores/platinum");

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

    //<editor-fold desc="Alloys">
    public static final Tags.IOptionalNamedTag<Block> STORAGE_BRASS = tag("storage_blocks/brass");
    public static final Tags.IOptionalNamedTag<Block> STORAGE_BRONZE = tag("storage_blocks/bronze");
    //</editor-fold>

    @Override
    protected void addTags() {

        //<editor-fold desc="Metals with stone ores">
        tag(STORAGE_COPPER).add(Registering.COPPER_BLOCK.BLOCK.get());
        tag(ORES_COPPER).add(Registering.COPPER_ORE.BLOCK.get());

        tag(STORAGE_LEAD).add(Registering.LEAD_BLOCK.BLOCK.get());
        tag(ORES_LEAD).add(Registering.LEAD_ORE.BLOCK.get());

        tag(STORAGE_MAGNESIUM).add(Registering.MAGNESIUM_BLOCK.BLOCK.get());
        tag(ORES_MAGNESIUM).add(Registering.MAGNESIUM_ORE.BLOCK.get());

        tag(STORAGE_NICKEL).add(Registering.NICKEL_BLOCK.BLOCK.get());
        tag(ORES_NICKEL).add(Registering.NICKEL_ORE.BLOCK.get());

        tag(STORAGE_PLATINUM).add(Registering.PLATINUM_BLOCK.BLOCK.get());
        tag(ORES_PLATINUM).add(Registering.PLATINUM_ORE.BLOCK.get());

        tag(STORAGE_SILVER).add(Registering.SILVER_BLOCK.BLOCK.get());
        tag(ORES_SILVER).add(Registering.SILVER_ORE.BLOCK.get());

        tag(STORAGE_TIN).add(Registering.TIN_BLOCK.BLOCK.get());
        tag(ORES_TIN).add(Registering.TIN_ORE.BLOCK.get());

        tag(STORAGE_ALUMINUM).add(Registering.ALUMINUM_BLOCK.BLOCK.get());
        tag(ORES_ALUMINUM).add(Registering.ALUMINUM_ORE.BLOCK.get());

        tag(STORAGE_ZINC).add(Registering.ZINC_BLOCK.BLOCK.get());
        tag(ORES_ZINC).add(Registering.ZINC_ORE.BLOCK.get());

        tag(STORAGE_MAGNETITE).add(Registering.MAGNETITE_BLOCK.BLOCK.get());
        tag(ORES_MAGNETITE).add(Registering.MAGNETITE_ORE.BLOCK.get());
        //</editor-fold>

        //<editor-fold desc="Metals with gravel ores">
        tag(GRAVEL_ORES_GOLD).add(Registering.GOLD_GRAVEL_ORE.BLOCK.get());
        tag(GRAVEL_ORES_PLATINUM).add(Registering.PLATINUM_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_RUTHENIUM).add(Registering.RUTHENIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_RUTHENIUM).add(Registering.RUTHENIUM_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_ZIRCONIUM).add(Registering.ZIRCONIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_ZIRCONIUM).add(Registering.ZIRCONIUM_GRAVEL_ORE.BLOCK.get());

        tag(STORAGE_IRIDIUM).add(Registering.IRIDIUM_BLOCK.BLOCK.get());
        tag(GRAVEL_ORES_IRIDIUM).add(Registering.IRIDIUM_GRAVEL_ORE.BLOCK.get());
        //</editor-fold>

        //<editor-fold desc="Alloys">
        tag(STORAGE_BRASS).add(Registering.BRASS_BLOCK.BLOCK.get());
        tag(STORAGE_BRONZE).add(Registering.BRONZE_BLOCK.BLOCK.get());
        //</editor-fold>

        tag(net.minecraft.tags.BlockTags.BEACON_BASE_BLOCKS).add(Registering.COPPER_BLOCK.BLOCK.get(),
                Registering.LEAD_BLOCK.BLOCK.get(), Registering.MAGNESIUM_BLOCK.BLOCK.get(),
                Registering.NICKEL_BLOCK.BLOCK.get(), Registering.PLATINUM_BLOCK.BLOCK.get(),
                Registering.SILVER_BLOCK.BLOCK.get(), Registering.TIN_BLOCK.BLOCK.get(),
                Registering.ALUMINUM_BLOCK.BLOCK.get(), Registering.ZINC_BLOCK.BLOCK.get(),
                Registering.BRASS_BLOCK.BLOCK.get(), Registering.BRONZE_BLOCK.BLOCK.get(),
                Registering.RUTHENIUM_BLOCK.BLOCK.get(), Registering.ZIRCONIUM_BLOCK.BLOCK.get(),
                Registering.IRIDIUM_BLOCK.BLOCK.get()
        );

        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            if (blockRegistryObject.BLOCK.getId().getPath().contains("gravel")) {
                tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_SHOVEL).add(blockRegistryObject.BLOCK.get());
            } else {
                tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE).add(blockRegistryObject.BLOCK.get());
            }
        }

        tag(net.minecraft.tags.BlockTags.NEEDS_STONE_TOOL).add(
                Registering.COPPER_BLOCK.BLOCK.get(), Registering.COPPER_ORE.BLOCK.get(),
                Registering.TIN_BLOCK.BLOCK.get(), Registering.TIN_ORE.BLOCK.get(),
                Registering.ALUMINUM_BLOCK.BLOCK.get(), Registering.ALUMINUM_ORE.BLOCK.get(),
                Registering.ZINC_BLOCK.BLOCK.get(), Registering.ZINC_ORE.BLOCK.get(),
                Registering.MAGNETITE_BLOCK.BLOCK.get(), Registering.MAGNETITE_ORE.BLOCK.get());

        tag(net.minecraft.tags.BlockTags.NEEDS_IRON_TOOL).add(
                Registering.LEAD_BLOCK.BLOCK.get(), Registering.LEAD_ORE.BLOCK.get(),
                Registering.NICKEL_BLOCK.BLOCK.get(), Registering.NICKEL_ORE.BLOCK.get(),
                Registering.MAGNESIUM_BLOCK.BLOCK.get(), Registering.MAGNESIUM_ORE.BLOCK.get(),
                Registering.PLATINUM_BLOCK.BLOCK.get(), Registering.PLATINUM_ORE.BLOCK.get(),
                Registering.SILVER_BLOCK.BLOCK.get(), Registering.SILVER_ORE.BLOCK.get()
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
