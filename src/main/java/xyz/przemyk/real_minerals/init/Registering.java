package xyz.przemyk.real_minerals.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.cables.CableBlock;
import xyz.przemyk.real_minerals.cables.CableTileEntity;
import xyz.przemyk.real_minerals.cables.ConnectorCableBlock;
import xyz.przemyk.real_minerals.cables.ConnectorCableTileEntity;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryBlock;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryContainer;
import xyz.przemyk.real_minerals.machines.electric.battery.BatteryTileEntity;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceBlock;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceContainer;
import xyz.przemyk.real_minerals.machines.electric.furnace.ElectricFurnaceTileEntity;
import xyz.przemyk.real_minerals.machines.electric.generator.BurningGeneratorBlock;
import xyz.przemyk.real_minerals.machines.electric.generator.BurningGeneratorContainer;
import xyz.przemyk.real_minerals.machines.electric.generator.BurningGeneratorTileEntity;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceBlock;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.machines.not_electric.alloy_furnace.AlloyFurnaceTileEntity;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherBlock;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherContainer;
import xyz.przemyk.real_minerals.machines.not_electric.crusher.CrusherTileEntity;

import static xyz.przemyk.real_minerals.init.RealMinerals.ITEM_GROUP;
import static xyz.przemyk.real_minerals.init.RealMinerals.MODID;

@SuppressWarnings("ConstantConditions")
public class Registering {

    private Registering() {}

    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
    }

    ////////////////////////////////////////////////////////////////// MACHINES
    /////////////////////////////////////// NOT ELECTRIC MACHINES

    public static final BlockRegistryObject CRUSHER_BLOCK = BLOCKS.register("crusher", () -> new CrusherBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> new ContainerType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> new ContainerType<>(AlloyFurnaceContainer::getClientContainer));

    /////////////////////////////////////// ELECTRIC MACHINES

    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = BLOCKS.register("burning_generator", () -> new BurningGeneratorBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<BurningGeneratorTileEntity>> BURNING_GENERATOR_TILE_ENTITY_TYPE = TILE_ENTITIES.register("burning_generator", () -> TileEntityType.Builder.create(BurningGeneratorTileEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<BurningGeneratorContainer>> BURNING_GENERATOR_CONTAINER = CONTAINERS.register("burning_generator", () -> new ContainerType<>(BurningGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject BATTERY_BLOCK = BLOCKS.register("battery", () -> new BatteryBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<BatteryTileEntity>> BATTERY_TILE_ENTITY_TYPE = TILE_ENTITIES.register("battery", () -> TileEntityType.Builder.create(BatteryTileEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<BatteryContainer>> BATTERY_CONTAINER = CONTAINERS.register("battery", () -> new ContainerType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = BLOCKS.register("electric_furnace", () -> new ElectricFurnaceBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("electric_furnace", () -> TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = CONTAINERS.register("electric_furnace", () -> new ContainerType<>(ElectricFurnaceContainer::getClientContainer));

    /////////////////////////////////////// ELECTRIC CABLES

    public static final BlockRegistryObject CABLE_BLOCK = BLOCKS.register("cable", () -> new CableBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<CableTileEntity>> CABLE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("cable", () -> TileEntityType.Builder.create(CableTileEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));

    public static final BlockRegistryObject CONNECTOR_CABLE_BLOCK = BLOCKS.register("connector_cable", () -> new ConnectorCableBlock(AbstractBlock.Properties.from(CABLE_BLOCK.BLOCK.get())), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<ConnectorCableTileEntity>> CONNECTOR_CABLE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("connector_cable", () -> TileEntityType.Builder.create(ConnectorCableTileEntity::new, CONNECTOR_CABLE_BLOCK.BLOCK.get()).build(null));

    ////////////////////////////////////////////////////////////////// METALS WITH STONE ORES

    public static final BlockRegistryObject  COPPER_BLOCK = BLOCKS.register("copper_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  COPPER_ORE = BLOCKS.register("copper_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE_TERRACOTTA).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  LEAD_ORE = BLOCKS.register("lead_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNESIUM_BLOCK = BLOCKS.register("magnesium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.PINK).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNESIUM_DUST = ITEMS.register(  "magnesium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_INGOT = ITEMS.register( "magnesium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_NUGGET = ITEMS.register("magnesium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  MAGNESIUM_ORE = BLOCKS.register(  "magnesium_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  NICKEL_BLOCK = BLOCKS.register("nickel_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> NICKEL_DUST = ITEMS.register(  "nickel_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register( "nickel_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_NUGGET = ITEMS.register("nickel_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  NICKEL_ORE = BLOCKS.register(  "nickel_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  PLATINUM_BLOCK = BLOCKS.register("platinum_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> PLATINUM_DUST = ITEMS.register(  "platinum_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register( "platinum_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  PLATINUM_ORE = BLOCKS.register(  "platinum_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register(  "silver_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register( "silver_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  SILVER_ORE = BLOCKS.register(  "silver_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> TIN_DUST = ITEMS.register(  "tin_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register( "tin_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  TIN_ORE = BLOCKS.register(  "tin_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ALUMINUM_DUST = ITEMS.register(  "aluminum_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register( "aluminum_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINUM_NUGGET = ITEMS.register("aluminum_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ALUMINUM_ORE = BLOCKS.register(  "aluminum_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  ZINC_BLOCK = BLOCKS.register("zinc_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZINC_DUST = ITEMS.register(  "zinc_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZINC_INGOT = ITEMS.register( "zinc_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZINC_NUGGET = ITEMS.register("zinc_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ZINC_ORE = BLOCKS.register(  "zinc_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNETITE_BLOCK = BLOCKS.register("magnetite_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_DUST = ITEMS.register(  "magnetite_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNETITE_INGOT = ITEMS.register( "magnetite_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNETITE_NUGGET = ITEMS.register("magnetite_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  MAGNETITE_ORE = BLOCKS.register(  "magnetite_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNETITE_GEAR = ITEMS.register( "magnetite_gear", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    ////////////////////////////////////////////////////////////////// METALS WITH GRAVEL ORES

    public static final BlockRegistryObject  GOLD_GRAVEL_ORE = BLOCKS.register(  "gold_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);
    public static final BlockRegistryObject  PLATINUM_GRAVEL_ORE = BLOCKS.register(  "platinum_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  RUTHENIUM_BLOCK = BLOCKS.register("ruthenium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> RUTHENIUM_DUST = ITEMS.register(  "ruthenium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> RUTHENIUM_INGOT = ITEMS.register( "ruthenium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> RUTHENIUM_NUGGET = ITEMS.register("ruthenium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  RUTHENIUM_GRAVEL_ORE = BLOCKS.register(  "ruthenium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  ZIRCONIUM_BLOCK = BLOCKS.register("zirconium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> ZIRCONIUM_DUST = ITEMS.register(  "zirconium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZIRCONIUM_INGOT = ITEMS.register( "zirconium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> ZIRCONIUM_NUGGET = ITEMS.register("zirconium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  ZIRCONIUM_GRAVEL_ORE = BLOCKS.register(  "zirconium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    public static final BlockRegistryObject  IRIDIUM_BLOCK = BLOCKS.register("iridium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> IRIDIUM_DUST = ITEMS.register(  "iridium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> IRIDIUM_INGOT = ITEMS.register( "iridium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> IRIDIUM_NUGGET = ITEMS.register("iridium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  IRIDIUM_GRAVEL_ORE = BLOCKS.register(  "iridium_gravel_ore", () -> new GravelBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.STONE).hardnessAndResistance(0.6F).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL)), ITEM_GROUP);

    ////////////////////////////////////////////////////////////////// ALLOYS

    public static final BlockRegistryObject  BRASS_BLOCK = BLOCKS.register("brass_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRASS_DUST = ITEMS.register(  "brass_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register( "brass_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_NUGGET = ITEMS.register("brass_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final BlockRegistryObject  BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register(  "bronze_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register( "bronze_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
}
