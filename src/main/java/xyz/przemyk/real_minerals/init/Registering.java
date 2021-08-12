package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.cables.CableBlock;
import xyz.przemyk.real_minerals.cables.CableTileEntity;
import xyz.przemyk.real_minerals.fluid.GasBucketItem;
import xyz.przemyk.real_minerals.fluid.ShaleGasFluid;
import xyz.przemyk.real_minerals.blocks.MachineBlock;
import xyz.przemyk.real_minerals.blocks.AlloyFurnaceBlock;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.tileentity.AlloyFurnaceTileEntity;
import xyz.przemyk.real_minerals.blocks.CrusherBlock;
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.tileentity.CrusherTileEntity;
import xyz.przemyk.real_minerals.blocks.BatteryBlock;
import xyz.przemyk.real_minerals.containers.BatteryContainer;
import xyz.przemyk.real_minerals.tileentity.BatteryTileEntity;
import xyz.przemyk.real_minerals.containers.ElectricFurnaceContainer;
import xyz.przemyk.real_minerals.tileentity.ElectricFurnaceTileEntity;
import xyz.przemyk.real_minerals.containers.GasSeparatorContainer;
import xyz.przemyk.real_minerals.tileentity.GasSeparatorTileEntity;
import xyz.przemyk.real_minerals.containers.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.tileentity.MagneticSeparatorTileEntity;
import xyz.przemyk.real_minerals.containers.MagnetizerContainer;
import xyz.przemyk.real_minerals.tileentity.MagnetizerTileEntity;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.tileentity.GasGeneratorTileEntity;
import xyz.przemyk.real_minerals.containers.SolidGeneratorContainer;
import xyz.przemyk.real_minerals.tileentity.SolidGeneratorTileEntity;
import xyz.przemyk.real_minerals.worldgen.MeteoriteFeature;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;
import static xyz.przemyk.real_minerals.RealMinerals.MODID;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Registering {

    private Registering() {}

    public static final BlockDeferredRegister BLOCKS_ITEMS = new BlockDeferredRegister(MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS_ITEMS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
        BLOCKS.register(eventBus);
        FEATURES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    //<editor-fold desc="Basic machines">
    public static final BlockRegistryObject CRUSHER_BLOCK = BLOCKS_ITEMS.register("crusher", () -> new CrusherBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("crusher", () -> BlockEntityType.Builder.of(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> new MenuType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = BLOCKS_ITEMS.register("alloy_furnace", () -> new AlloyFurnaceBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceTileEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> new MenuType<>(AlloyFurnaceContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric generators">
    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = BLOCKS_ITEMS.register("burning_generator", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), SolidGeneratorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<SolidGeneratorTileEntity>> BURNING_GENERATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("burning_generator", () -> BlockEntityType.Builder.of(SolidGeneratorTileEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SolidGeneratorContainer>> BURNING_GENERATOR_CONTAINER = CONTAINERS.register("burning_generator", () -> new MenuType<>(SolidGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_GENERATOR_BLOCK = BLOCKS_ITEMS.register("gas_generator", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), GasGeneratorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasGeneratorTileEntity>> GAS_GENERATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("gas_generator", () -> BlockEntityType.Builder.of(GasGeneratorTileEntity::new, GAS_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasGeneratorContainer>> GAS_GENERATOR_CONTAINER = CONTAINERS.register("gas_generator", () -> IForgeContainerType.create(GasGeneratorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric machines">
    public static final BlockRegistryObject BATTERY_BLOCK = BLOCKS_ITEMS.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<BatteryTileEntity>> BATTERY_TILE_ENTITY_TYPE = TILE_ENTITIES.register("battery", () -> BlockEntityType.Builder.of(BatteryTileEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BatteryContainer>> BATTERY_CONTAINER = CONTAINERS.register("battery", () -> new MenuType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = BLOCKS_ITEMS.register("electric_furnace", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), ElectricFurnaceTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceTileEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = CONTAINERS.register("electric_furnace", () -> new MenuType<>(ElectricFurnaceContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIZER_BLOCK = BLOCKS_ITEMS.register("magnetizer", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), MagnetizerTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagnetizerTileEntity>> MAGNETIZER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("magnetizer", () -> BlockEntityType.Builder.of(MagnetizerTileEntity::new, MAGNETIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagnetizerContainer>> MAGNETIZER_CONTAINER = CONTAINERS.register("magnetizer", () -> new MenuType<>(MagnetizerContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIC_SEPARATOR_BLOCK = BLOCKS_ITEMS.register("magnetic_separator", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), MagneticSeparatorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagneticSeparatorTileEntity>> MAGNETIC_SEPARATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("magnetic_separator", () -> BlockEntityType.Builder.of(MagneticSeparatorTileEntity::new, MAGNETIC_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagneticSeparatorContainer>> MAGNETIC_SEPARATOR_CONTAINER = CONTAINERS.register("magnetic_separator", () -> new MenuType<>(MagneticSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_SEPARATOR_BLOCK = BLOCKS_ITEMS.register("gas_separator", () -> new MachineBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL), GasSeparatorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasSeparatorTileEntity>> GAS_SEPARATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("gas_separator", () -> BlockEntityType.Builder.of(GasSeparatorTileEntity::new, GAS_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasSeparatorContainer>> GAS_SEPARATOR_CONTAINER = CONTAINERS.register("gas_separator", () -> new MenuType<>(GasSeparatorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric cables">
    public static final BlockRegistryObject CABLE_BLOCK = BLOCKS_ITEMS.register("cable", () -> new CableBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL).isRedstoneConductor((p1, p2, p3) -> false)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CableTileEntity>> CABLE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("cable", () -> BlockEntityType.Builder.of(CableTileEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));
    //</editor-fold>

    //<editor-fold desc="Metals with stone ores">
    public static final BlockRegistryObject  COPPER_BLOCK = BLOCKS_ITEMS.register("copper_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> COPPER_DUST = simpleItem("copper_dust");
    public static final RegistryObject<Item> COPPER_INGOT = simpleItem("copper_ingot");
    public static final RegistryObject<Item> COPPER_NUGGET = simpleItem("copper_nugget");
    public static final BlockRegistryObject  COPPER_ORE = BLOCKS_ITEMS.register("copper_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  LEAD_BLOCK = BLOCKS_ITEMS.register("lead_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> LEAD_DUST = simpleItem("lead_dust");
    public static final RegistryObject<Item> LEAD_INGOT = simpleItem("lead_ingot");
    public static final RegistryObject<Item> LEAD_NUGGET = simpleItem("lead_nugget");
    public static final BlockRegistryObject  LEAD_ORE = BLOCKS_ITEMS.register("lead_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNESIUM_BLOCK = BLOCKS_ITEMS.register("magnesium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNESIUM_DUST = simpleItem("magnesium_dust");
    public static final RegistryObject<Item> MAGNESIUM_INGOT = simpleItem("magnesium_ingot");
    public static final RegistryObject<Item> MAGNESIUM_NUGGET = simpleItem("magnesium_nugget");
    public static final BlockRegistryObject  MAGNESIUM_ORE = BLOCKS_ITEMS.register("magnesium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  NICKEL_BLOCK = BLOCKS_ITEMS.register("nickel_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> NICKEL_DUST = simpleItem("nickel_dust");
    public static final RegistryObject<Item> NICKEL_INGOT = simpleItem("nickel_ingot");
    public static final RegistryObject<Item> NICKEL_NUGGET = simpleItem("nickel_nugget");
    public static final BlockRegistryObject  NICKEL_ORE = BLOCKS_ITEMS.register("nickel_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  PLATINUM_BLOCK = BLOCKS_ITEMS.register("platinum_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> PLATINUM_DUST = simpleItem("platinum_dust");
    public static final RegistryObject<Item> PLATINUM_INGOT = simpleItem("platinum_ingot");
    public static final RegistryObject<Item> PLATINUM_NUGGET = simpleItem("platinum_nugget");
    public static final BlockRegistryObject  PLATINUM_ORE = BLOCKS_ITEMS.register(  "platinum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  SILVER_BLOCK = BLOCKS_ITEMS.register("silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> SILVER_DUST = simpleItem("silver_dust");
    public static final RegistryObject<Item> SILVER_INGOT = simpleItem("silver_ingot");
    public static final RegistryObject<Item> SILVER_NUGGET = simpleItem("silver_nugget");
    public static final BlockRegistryObject  SILVER_ORE = BLOCKS_ITEMS.register("silver_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  TIN_BLOCK = BLOCKS_ITEMS.register("tin_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> TIN_DUST = simpleItem("tin_dust");
    public static final RegistryObject<Item> TIN_INGOT = simpleItem("tin_ingot");
    public static final RegistryObject<Item> TIN_NUGGET = simpleItem("tin_nugget");
    public static final BlockRegistryObject  TIN_ORE = BLOCKS_ITEMS.register("tin_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ALUMINUM_BLOCK = BLOCKS_ITEMS.register("aluminum_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ALUMINUM_DUST = simpleItem("aluminum_dust");
    public static final RegistryObject<Item> ALUMINUM_INGOT = simpleItem("aluminum_ingot");
    public static final RegistryObject<Item> ALUMINUM_NUGGET = simpleItem("aluminum_nugget");
    public static final BlockRegistryObject  ALUMINUM_ORE = BLOCKS_ITEMS.register("aluminum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ZINC_BLOCK = BLOCKS_ITEMS.register("zinc_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZINC_DUST = simpleItem("zinc_dust");
    public static final RegistryObject<Item> ZINC_INGOT = simpleItem("zinc_ingot");
    public static final RegistryObject<Item> ZINC_NUGGET = simpleItem("zinc_nugget");
    public static final BlockRegistryObject  ZINC_ORE = BLOCKS_ITEMS.register("zinc_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNETITE_BLOCK = BLOCKS_ITEMS.register("magnetite_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_DUST = simpleItem("magnetite_dust");
    public static final RegistryObject<Item> MAGNETITE_INGOT = simpleItem("magnetite_ingot");
    public static final RegistryObject<Item> MAGNETITE_NUGGET = simpleItem("magnetite_nugget");
    public static final BlockRegistryObject  MAGNETITE_ORE = BLOCKS_ITEMS.register("magnetite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_GEAR = simpleItem( "magnetite_gear");
    //</editor-fold>

    //<editor-fold desc="Metals with gravel ores">
    public static final BlockRegistryObject  GOLD_GRAVEL_ORE = BLOCKS_ITEMS.register("gold_gravel_ore", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);
    public static final BlockRegistryObject  PLATINUM_GRAVEL_ORE = BLOCKS_ITEMS.register("platinum_gravel_ore", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  RUTHENIUM_BLOCK = BLOCKS_ITEMS.register("ruthenium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> RUTHENIUM_DUST = simpleItem("ruthenium_dust");
    public static final RegistryObject<Item> RUTHENIUM_INGOT = simpleItem("ruthenium_ingot");
    public static final RegistryObject<Item> RUTHENIUM_NUGGET = simpleItem("ruthenium_nugget");
    public static final BlockRegistryObject  RUTHENIUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "ruthenium_gravel_ore", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  ZIRCONIUM_BLOCK = BLOCKS_ITEMS.register("zirconium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZIRCONIUM_DUST = simpleItem("zirconium_dust");
    public static final RegistryObject<Item> ZIRCONIUM_INGOT = simpleItem("zirconium_ingot");
    public static final RegistryObject<Item> ZIRCONIUM_NUGGET = simpleItem("zirconium_nugget");
    public static final BlockRegistryObject  ZIRCONIUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "zirconium_gravel_ore", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  IRIDIUM_BLOCK = BLOCKS_ITEMS.register("iridium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_YELLOW).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> IRIDIUM_DUST = simpleItem("iridium_dust");
    public static final RegistryObject<Item> IRIDIUM_INGOT = simpleItem("iridium_ingot");
    public static final RegistryObject<Item> IRIDIUM_NUGGET = simpleItem("iridium_nugget");
    public static final BlockRegistryObject  IRIDIUM_GRAVEL_ORE = BLOCKS_ITEMS.register("iridium_gravel_ore", () -> new GravelBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL)), ITEM_GROUP);
    //</editor-fold>

    //<editor-fold desc="Alloys">
    public static final BlockRegistryObject  BRASS_BLOCK = BLOCKS_ITEMS.register("brass_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRASS_DUST = simpleItem("brass_dust");
    public static final RegistryObject<Item> BRASS_INGOT = simpleItem("brass_ingot");
    public static final RegistryObject<Item> BRASS_NUGGET = simpleItem("brass_nugget");

    public static final BlockRegistryObject  BRONZE_BLOCK = BLOCKS_ITEMS.register("bronze_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRONZE_DUST = simpleItem("bronze_dust");
    public static final RegistryObject<Item> BRONZE_INGOT = simpleItem("bronze_ingot");
    public static final RegistryObject<Item> BRONZE_NUGGET = simpleItem("bronze_nugget");
    //</editor-fold>

    //<editor-fold desc="Misc">
    public static final RegistryObject<MeteoriteFeature> METEORITE_FEATURE = FEATURES.register("meteorite", () -> new MeteoriteFeature(NoneFeatureConfiguration.CODEC));
    public static final BlockRegistryObject METEORITE = BLOCKS_ITEMS.register("meteorite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)), ITEM_GROUP);
    public static final RegistryObject<Item> METEORITE_IRON_DUST = simpleItem("meteorite_iron_dust");
    public static final RegistryObject<Item> METEORITE_IRON_INGOT = simpleItem("meteorite_iron_ingot");
    public static final RegistryObject<Item> METEORITE_IRON_NUGGET = simpleItem("meteorite_iron_nugget");

    public static final BlockRegistryObject SHALE_GAS_STONE_BLOCK = BLOCKS_ITEMS.register("shale_gas_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ITEM_GROUP);
    public static final RegistryObject<Fluid> SHALE_GAS_FLUID = FLUIDS.register("shale_gas", ShaleGasFluid::new);
    public static final RegistryObject<Item> SHALE_GAS_BUCKET = ITEMS.register("shale_gas_bucket", () -> new GasBucketItem(SHALE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_GROUP)));
    //</editor-fold>

    private static RegistryObject<Item> simpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    }
}
