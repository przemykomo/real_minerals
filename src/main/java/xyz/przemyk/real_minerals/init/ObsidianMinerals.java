package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;

public class ObsidianMinerals {

    public static final BlockRegistryObject TUNGSTEN_BLOCK = Registering.storageBlock("tungsten_block", MaterialColor.TERRACOTTA_LIGHT_GREEN);
    public static final RegistryMetalSet TUNGSTEN_ITEMS = new RegistryMetalSet("tungsten");
    public static final BlockRegistryObject TUNGSTEN_OBSIDIAN_ORE = obsidianOre("tungsten_obsidian_ore");

    public static final BlockRegistryObject RHENIUM_BLOCK = Registering.storageBlock("rhenium_block", MaterialColor.COLOR_GRAY);
    public static final RegistryMetalSet RHENIUM_ITEMS = new RegistryMetalSet("rhenium");
    public static final BlockRegistryObject RHENIUM_OBSIDIAN_ORE = obsidianOre("rhenium_obsidian_ore");

    static void init() {}

    static BlockRegistryObject obsidianOre(String name) {
        return Registering.BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)), ITEM_GROUP);
    }
}
