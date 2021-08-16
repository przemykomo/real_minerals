package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;

public class GravelMinerals {
    public static final BlockRegistryObject GOLD_GRAVEL_ORE = gravelOre("gold_gravel_ore");
    public static final BlockRegistryObject PLATINUM_GRAVEL_ORE = gravelOre("platinum_gravel_ore");

    public static final BlockRegistryObject RUTHENIUM_BLOCK = Registering.storageBlock("ruthenium_block", MaterialColor.COLOR_YELLOW);
    public static final RegistryMetalSet RUTHENIUM_ITEMS = new RegistryMetalSet("ruthenium");
    public static final BlockRegistryObject RUTHENIUM_GRAVEL_ORE = gravelOre("ruthenium_gravel_ore");

    public static final BlockRegistryObject ZIRCONIUM_BLOCK = Registering.storageBlock("zirconium_block", MaterialColor.COLOR_YELLOW);
    public static final RegistryMetalSet ZIRCONIUM_ITEMS = new RegistryMetalSet("zirconium");
    public static final BlockRegistryObject ZIRCONIUM_GRAVEL_ORE = gravelOre("zirconium_gravel_ore");

    public static final BlockRegistryObject IRIDIUM_BLOCK = Registering.storageBlock("iridium_block", MaterialColor.COLOR_YELLOW);
    public static final RegistryMetalSet IRIDIUM_ITEMS = new RegistryMetalSet("iridium");
    public static final BlockRegistryObject IRIDIUM_GRAVEL_ORE = gravelOre("iridium_gravel_ore");

    static void init() {}

    static BlockRegistryObject gravelOre(String name) {
        return Registering.BLOCKS_ITEMS.register(name, () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);
    }
}