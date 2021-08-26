package xyz.przemyk.real_minerals.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_TAB;

public class StoneMinerals {
    public static final BlockRegistryObject LEAD_BLOCK = Registering.storageBlock("lead_block", MaterialColor.TERRACOTTA_BLUE);
    public static final RegistryMetalSet LEAD_ITEMS = new RegistryMetalSet("lead");
    public static final BlockRegistryObject LEAD_ORE = stoneOre("lead_ore");

    public static final BlockRegistryObject MAGNESIUM_BLOCK = Registering.storageBlock("magnesium_block", MaterialColor.COLOR_PINK);
    public static final RegistryMetalSet MAGNESIUM_ITEMS = new RegistryMetalSet("magnesium");
    public static final BlockRegistryObject MAGNESIUM_ORE = stoneOre("magnesium_ore");

    public static final BlockRegistryObject NICKEL_BLOCK = Registering.storageBlock("nickel_block", MaterialColor.GOLD);
    public static final RegistryMetalSet NICKEL_ITEMS = new RegistryMetalSet("nickel");
    public static final BlockRegistryObject NICKEL_ORE = stoneOre("nickel_ore");

    public static final BlockRegistryObject SILVER_BLOCK = Registering.storageBlock("silver_block", MaterialColor.METAL);
    public static final RegistryMetalSet SILVER_ITEMS = new RegistryMetalSet("silver");
    public static final BlockRegistryObject SILVER_ORE = stoneOre("silver_ore");

    public static final BlockRegistryObject TIN_BLOCK = Registering.storageBlock("tin_block", MaterialColor.METAL);
    public static final RegistryMetalSet TIN_ITEMS = new RegistryMetalSet("tin");
    public static final BlockRegistryObject TIN_ORE = stoneOre("tin_ore");

    public static final BlockRegistryObject ALUMINUM_BLOCK = Registering.storageBlock("aluminum_block", MaterialColor.METAL);
    public static final RegistryMetalSet ALUMINUM_ITEMS = new RegistryMetalSet("aluminum");
    public static final BlockRegistryObject ALUMINUM_ORE = stoneOre("aluminum_ore");

    public static final BlockRegistryObject ZINC_BLOCK = Registering.storageBlock("zinc_block", MaterialColor.COLOR_YELLOW);
    public static final RegistryMetalSet ZINC_ITEMS = new RegistryMetalSet("zinc");
    public static final BlockRegistryObject ZINC_ORE = stoneOre("zinc_ore");

    public static final BlockRegistryObject MAGNETITE_BLOCK = Registering.storageBlock("magnetite_block", MaterialColor.METAL);
    public static final RegistryMetalSet MAGNETITE_ITEMS = new RegistryMetalSet("magnetite");
    public static final BlockRegistryObject MAGNETITE_ORE = stoneOre("magnetite_ore");
    public static final RegistryObject<Item> MAGNETITE_GEAR = Registering.simpleItem("magnetite_gear");

    public static final BlockRegistryObject SULFUR_ORE = stoneOre("sulfur_ore");
    public static final RegistryObject<Item> SULFUR = Registering.simpleItem("sulfur");

    static void init() {}

    static BlockRegistryObject stoneOre(String name) {
        return Registering.BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_TAB);
    }
}
