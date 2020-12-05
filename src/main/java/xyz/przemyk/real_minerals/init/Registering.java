package xyz.przemyk.real_minerals.init;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.cables.CableBlock;
import xyz.przemyk.real_minerals.cables.CableTileEntity;
import xyz.przemyk.real_minerals.fluid.GasBucketItem;
import xyz.przemyk.real_minerals.fluid.ShaleGasFluid;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryBlock;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryContainer;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryTileEntity;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceBlock;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceContainer;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceTileEntity;
import xyz.przemyk.real_minerals.machines.electric.gas_separator.GasSeparatorBlock;
import xyz.przemyk.real_minerals.machines.electric.gas_separator.GasSeparatorContainer;
import xyz.przemyk.real_minerals.machines.electric.gas_separator.GasSeparatorTileEntity;
import xyz.przemyk.real_minerals.machines.generators.gas.GasGeneratorBlock;
import xyz.przemyk.real_minerals.machines.generators.gas.GasGeneratorContainer;
import xyz.przemyk.real_minerals.machines.generators.gas.GasGeneratorTileEntity;
import xyz.przemyk.real_minerals.machines.generators.solid.SolidGeneratorBlock;
import xyz.przemyk.real_minerals.machines.generators.solid.SolidGeneratorContainer;
import xyz.przemyk.real_minerals.machines.generators.solid.SolidGeneratorTileEntity;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorBlock;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.machines.electric.magnetic_separator.MagneticSeparatorTileEntity;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerBlock;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerContainer;
import xyz.przemyk.real_minerals.machines.electric.magnetizer.MagnetizerTileEntity;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceBlock;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceTileEntity;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherBlock;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherContainer;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherTileEntity;
import xyz.przemyk.real_minerals.worldgen.MeteoriteFeature;

import static xyz.przemyk.real_minerals.init.RealMinerals.ITEM_GROUP;
import static xyz.przemyk.real_minerals.init.RealMinerals.MODID;

@SuppressWarnings("ConstantConditions")
public class Registering {

    private Registering() {}

    public static final BlockDeferredRegister BLOCKS_ITEMS = new BlockDeferredRegister(MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
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

    ////////////////////////////////////////////////////////////////// MACHINES
    /////////////////////////////////////// NOT ELECTRIC MACHINES

    //<editor-fold desc="Not electric machines">
    public static final BlockRegistryObject CRUSHER_BLOCK = BLOCKS_ITEMS.register("crusher", () -> new CrusherBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> new ContainerType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = BLOCKS_ITEMS.register("alloy_furnace", () -> new AlloyFurnaceBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> new ContainerType<>(AlloyFurnaceContainer::getClientContainer));
    //</editor-fold>

    /////////////////////////////////////// ELECTRIC GENERATORS
    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = BLOCKS_ITEMS.register("burning_generator", () -> new SolidGeneratorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<SolidGeneratorTileEntity>> BURNING_GENERATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("burning_generator", () -> TileEntityType.Builder.create(SolidGeneratorTileEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<SolidGeneratorContainer>> BURNING_GENERATOR_CONTAINER = CONTAINERS.register("burning_generator", () -> new ContainerType<>(SolidGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_GENERATOR_BLOCK = BLOCKS_ITEMS.register("gas_generator", () -> new GasGeneratorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<GasGeneratorTileEntity>> GAS_GENERATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("gas_generator", () -> TileEntityType.Builder.create(GasGeneratorTileEntity::new, GAS_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<GasGeneratorContainer>> GAS_GENERATOR_CONTAINER = CONTAINERS.register("gas_generator", () -> new ContainerType<>(GasGeneratorContainer::getClientContainer));

    /////////////////////////////////////// ELECTRIC MACHINES

    //<editor-fold desc="Electric machines">
    public static final BlockRegistryObject BATTERY_BLOCK = BLOCKS_ITEMS.register("battery", () -> new BatteryBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<BatteryTileEntity>> BATTERY_TILE_ENTITY_TYPE = TILE_ENTITIES.register("battery", () -> TileEntityType.Builder.create(BatteryTileEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<BatteryContainer>> BATTERY_CONTAINER = CONTAINERS.register("battery", () -> new ContainerType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = BLOCKS_ITEMS.register("electric_furnace", () -> new ElectricFurnaceBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("electric_furnace", () -> TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = CONTAINERS.register("electric_furnace", () -> new ContainerType<>(ElectricFurnaceContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIZER_BLOCK = BLOCKS_ITEMS.register("magnetizer", () -> new MagnetizerBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<MagnetizerTileEntity>> MAGNETIZER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("magnetizer", () -> TileEntityType.Builder.create(MagnetizerTileEntity::new, MAGNETIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<MagnetizerContainer>> MAGNETIZER_CONTAINER = CONTAINERS.register("magnetizer", () -> new ContainerType<>(MagnetizerContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIC_SEPARATOR_BLOCK = BLOCKS_ITEMS.register("magnetic_separator", () -> new MagneticSeparatorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<MagneticSeparatorTileEntity>> MAGNETIC_SEPARATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("magnetic_separator", () -> TileEntityType.Builder.create(MagneticSeparatorTileEntity::new, MAGNETIC_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<MagneticSeparatorContainer>> MAGNETIC_SEPARATOR_CONTAINER = CONTAINERS.register("magnetic_separator", () -> new ContainerType<>(MagneticSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_SEPARATOR_BLOCK = BLOCKS_ITEMS.register("gas_separator", () -> new GasSeparatorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<GasSeparatorTileEntity>> GAS_SEPARATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("gas_separator", () -> TileEntityType.Builder.create(GasSeparatorTileEntity::new, GAS_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<GasSeparatorContainer>> GAS_SEPARATOR_CONTAINER = CONTAINERS.register("gas_separator", () -> new ContainerType<>(GasSeparatorContainer::getClientContainer));
    //</editor-fold>

    /////////////////////////////////////// ELECTRIC CABLES

    public static final BlockRegistryObject CABLE_BLOCK = BLOCKS_ITEMS.register("cable", () -> new CableBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL).setOpaque((p1, p2, p3) -> false)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<CableTileEntity>> CABLE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("cable", () -> TileEntityType.Builder.create(CableTileEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));

    ////////////////////////////////////////////////////////////////// METALS WITH STONE ORES

    //<editor-fold desc="Metals with stone ores">
    public static final BlockRegistryObject  COPPER_BLOCK = BLOCKS_ITEMS.register("copper_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  COPPER_ORE = BLOCKS_ITEMS.register("copper_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  LEAD_BLOCK = BLOCKS_ITEMS.register("lead_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  LEAD_ORE = BLOCKS_ITEMS.register("lead_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNESIUM_BLOCK = BLOCKS_ITEMS.register("magnesium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNESIUM_DUST = ITEMS.register(  "magnesium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_INGOT = ITEMS.register( "magnesium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_NUGGET = ITEMS.register("magnesium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  MAGNESIUM_ORE = BLOCKS_ITEMS.register(  "magnesium_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  NICKEL_BLOCK = BLOCKS_ITEMS.register("nickel_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> NICKEL_DUST = ITEMS.register(  "nickel_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register( "nickel_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_NUGGET = ITEMS.register("nickel_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  NICKEL_ORE = BLOCKS_ITEMS.register(  "nickel_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  PLATINUM_BLOCK = BLOCKS_ITEMS.register("platinum_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> PLATINUM_DUST = ITEMS.register(  "platinum_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register( "platinum_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  PLATINUM_ORE = BLOCKS_ITEMS.register(  "platinum_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  SILVER_BLOCK = BLOCKS_ITEMS.register("silver_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register(  "silver_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register( "silver_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  SILVER_ORE = BLOCKS_ITEMS.register(  "silver_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  TIN_BLOCK = BLOCKS_ITEMS.register("tin_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> TIN_DUST = ITEMS.register(  "tin_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register( "tin_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  TIN_ORE = BLOCKS_ITEMS.register(  "tin_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ALUMINUM_BLOCK = BLOCKS_ITEMS.register("aluminum_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ALUMINUM_DUST = ITEMS.register(  "aluminum_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register( "aluminum_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINUM_NUGGET = ITEMS.register("aluminum_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ALUMINUM_ORE = BLOCKS_ITEMS.register(  "aluminum_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ZINC_BLOCK = BLOCKS_ITEMS.register("zinc_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZINC_DUST = ITEMS.register(  "zinc_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register( "zinc_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZINC_NUGGET = ITEMS.register("zinc_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ZINC_ORE = BLOCKS_ITEMS.register(  "zinc_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNETITE_BLOCK = BLOCKS_ITEMS.register("magnetite_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_DUST = ITEMS.register(  "magnetite_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNETITE_INGOT = ITEMS.register( "magnetite_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNETITE_NUGGET = ITEMS.register("magnetite_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  MAGNETITE_ORE = BLOCKS_ITEMS.register(  "magnetite_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_GEAR = ITEMS.register( "magnetite_gear", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    //</editor-fold>

    ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES

    //<editor-fold desc="Metals with gravel ores">
    public static final BlockRegistryObject  GOLD_GRAVEL_ORE = BLOCKS_ITEMS.register(  "gold_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);
    public static final BlockRegistryObject  PLATINUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "platinum_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  RUTHENIUM_BLOCK = BLOCKS_ITEMS.register("ruthenium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> RUTHENIUM_DUST = ITEMS.register(  "ruthenium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> RUTHENIUM_INGOT = ITEMS.register( "ruthenium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> RUTHENIUM_NUGGET = ITEMS.register("ruthenium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  RUTHENIUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "ruthenium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  ZIRCONIUM_BLOCK = BLOCKS_ITEMS.register("zirconium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZIRCONIUM_DUST = ITEMS.register(  "zirconium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZIRCONIUM_INGOT = ITEMS.register( "zirconium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZIRCONIUM_NUGGET = ITEMS.register("zirconium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ZIRCONIUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "zirconium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  IRIDIUM_BLOCK = BLOCKS_ITEMS.register("iridium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> IRIDIUM_DUST = ITEMS.register(  "iridium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> IRIDIUM_INGOT = ITEMS.register( "iridium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> IRIDIUM_NUGGET = ITEMS.register("iridium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  IRIDIUM_GRAVEL_ORE = BLOCKS_ITEMS.register(  "iridium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);
    //</editor-fold>

    ////////////////////////////////////////////////////////////////// ALLOYS

    //<editor-fold desc="Alloys">
    public static final BlockRegistryObject  BRASS_BLOCK = BLOCKS_ITEMS.register("brass_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRASS_DUST = ITEMS.register(  "brass_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register( "brass_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_NUGGET = ITEMS.register("brass_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final BlockRegistryObject  BRONZE_BLOCK = BLOCKS_ITEMS.register("bronze_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register(  "bronze_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register( "bronze_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    //</editor-fold>

    ////////////////////////////////////////////////////////////////// SPECIAL
    public static final RegistryObject<MeteoriteFeature> METEORITE_FEATURE = FEATURES.register("meteorite", () -> new MeteoriteFeature(NoFeatureConfig.field_236558_a_));
    public static final BlockRegistryObject METEORITE = BLOCKS_ITEMS.register("meteorite", () -> new Block(AbstractBlock.Properties.from(Blocks.OBSIDIAN).harvestLevel(4)), ITEM_GROUP);
    public static final RegistryObject<Item> METEORITE_IRON_DUST = ITEMS.register(  "meteorite_iron_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> METEORITE_IRON_INGOT = ITEMS.register( "meteorite_iron_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> METEORITE_IRON_NUGGET = ITEMS.register("meteorite_iron_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final BlockRegistryObject SHALE_GAS_STONE_BLOCK = BLOCKS_ITEMS.register("shale_gas_stone", () -> new Block(AbstractBlock.Properties.from(Blocks.STONE)), ITEM_GROUP);
    public static final RegistryObject<Fluid> SHALE_GAS_FLUID = FLUIDS.register("shale_gas", ShaleGasFluid::new);
    public static final RegistryObject<Item> SHALE_GAS_BUCKET = ITEMS.register("shale_gas_bucket", () -> new GasBucketItem(SHALE_GAS_FLUID, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ITEM_GROUP)));
}
