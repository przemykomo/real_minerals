package xyz.przemyk.real_minerals.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_TAB;

public class StoneMinerals {
    public static final RegistryStoneMetalSet LEAD = new RegistryStoneMetalSet("lead", MaterialColor.TERRACOTTA_BLUE);
    public static final RegistryStoneMetalSet MAGNESIUM = new RegistryStoneMetalSet("magnesium", MaterialColor.COLOR_PINK);
    public static final RegistryStoneMetalSet NICKEL = new RegistryStoneMetalSet("nickel", MaterialColor.GOLD);
    public static final RegistryStoneMetalSet SILVER = new RegistryStoneMetalSet("silver", MaterialColor.METAL);
    public static final RegistryStoneMetalSet TIN = new RegistryStoneMetalSet("tin", MaterialColor.METAL);
    public static final RegistryStoneMetalSet ALUMINUM = new RegistryStoneMetalSet("aluminum", MaterialColor.METAL);
    public static final RegistryStoneMetalSet ZINC = new RegistryStoneMetalSet("zinc", MaterialColor.COLOR_YELLOW);
    public static final RegistryStoneMetalSet MAGNETITE = new RegistryStoneMetalSet("magnetite", MaterialColor.METAL);
    public static final RegistryObject<Item> MAGNETITE_GEAR = Registering.simpleItem("magnetite_gear");

    public static final BlockRegistryObject SULFUR_ORE = stoneOre("sulfur_ore");
    public static final RegistryObject<Item> SULFUR = Registering.simpleItem("sulfur");

    public static final RegistryObject<Item> IRON_DUST = Registering.simpleItem("iron_dust");
    public static final RegistryObject<Item> GOLD_DUST = Registering.simpleItem("gold_dust");
    public static final RegistryObject<Item> COPPER_DUST = Registering.simpleItem("copper_dust");

    static void init() {}

    static BlockRegistryObject stoneOre(String name) {
        return Registering.BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_TAB);
    }

    public static class RegistryStoneMetalSet extends RegistryMetalSet {
        public final BlockRegistryObject BLOCK;
        public final BlockRegistryObject ORE;
        public final RegistryObject<Item> RAW_ORE;

        public RegistryStoneMetalSet(String name, MaterialColor storageColor) {
            super(name);
            this.BLOCK = Registering.storageBlock(name + "_block", storageColor);
            this.ORE = stoneOre(name + "_ore");
            this.RAW_ORE = Registering.simpleItem("raw_" + name);
        }
    }
}
