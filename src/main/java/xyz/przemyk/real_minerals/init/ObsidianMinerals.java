package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;

public class ObsidianMinerals {
    public static final BlockRegistryObject CHROMIUM_OBSIDIAN_ORE = obsidianOre("chromium_obsidian_ore");

    static void init() {}

    static BlockRegistryObject obsidianOre(String name) {
        return Registering.BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)), ITEM_GROUP);
    }
}
