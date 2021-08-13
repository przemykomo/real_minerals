package xyz.przemyk.real_minerals.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.blocks.AlloyFurnaceBlock;
import xyz.przemyk.real_minerals.blocks.BatteryBlock;
import xyz.przemyk.real_minerals.blocks.CrusherBlock;
import xyz.przemyk.real_minerals.blocks.MachineBlock;
import xyz.przemyk.real_minerals.cables.CableBlock;
import xyz.przemyk.real_minerals.cables.CableTileEntity;
import xyz.przemyk.real_minerals.containers.*;
import xyz.przemyk.real_minerals.tileentity.*;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;

public class MachinesRegistry {
    //<editor-fold desc="Basic machines">
    public static final BlockRegistryObject CRUSHER_BLOCK = Registering.BLOCKS_ITEMS.register("crusher", CrusherBlock::new, ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("crusher", () -> BlockEntityType.Builder.of(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrusherContainer>> CRUSHER_CONTAINER = Registering.CONTAINERS.register("crusher", () -> new MenuType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("alloy_furnace", AlloyFurnaceBlock::new, ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceTileEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = Registering.CONTAINERS.register("alloy_furnace", () -> new MenuType<>(AlloyFurnaceContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric generators">
    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("burning_generator", () -> new MachineBlock(SolidGeneratorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<SolidGeneratorTileEntity>> BURNING_GENERATOR_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("burning_generator", () -> BlockEntityType.Builder.of(SolidGeneratorTileEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SolidGeneratorContainer>> BURNING_GENERATOR_CONTAINER = Registering.CONTAINERS.register("burning_generator", () -> new MenuType<>(SolidGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_generator", () -> new MachineBlock(GasGeneratorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasGeneratorTileEntity>> GAS_GENERATOR_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("gas_generator", () -> BlockEntityType.Builder.of(GasGeneratorTileEntity::new, GAS_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasGeneratorContainer>> GAS_GENERATOR_CONTAINER = Registering.CONTAINERS.register("gas_generator", () -> IForgeContainerType.create(GasGeneratorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric machines">
    public static final BlockRegistryObject BATTERY_BLOCK = Registering.BLOCKS_ITEMS.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<BatteryTileEntity>> BATTERY_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("battery", () -> BlockEntityType.Builder.of(BatteryTileEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BatteryContainer>> BATTERY_CONTAINER = Registering.CONTAINERS.register("battery", () -> new MenuType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("electric_furnace", () -> new MachineBlock(ElectricFurnaceTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceTileEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = Registering.CONTAINERS.register("electric_furnace", () -> new MenuType<>(ElectricFurnaceContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIZER_BLOCK = Registering.BLOCKS_ITEMS.register("magnetizer", () -> new MachineBlock(MagnetizerTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagnetizerTileEntity>> MAGNETIZER_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("magnetizer", () -> BlockEntityType.Builder.of(MagnetizerTileEntity::new, MAGNETIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagnetizerContainer>> MAGNETIZER_CONTAINER = Registering.CONTAINERS.register("magnetizer", () -> new MenuType<>(MagnetizerContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIC_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("magnetic_separator", () -> new MachineBlock(MagneticSeparatorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagneticSeparatorTileEntity>> MAGNETIC_SEPARATOR_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("magnetic_separator", () -> BlockEntityType.Builder.of(MagneticSeparatorTileEntity::new, MAGNETIC_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagneticSeparatorContainer>> MAGNETIC_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("magnetic_separator", () -> new MenuType<>(MagneticSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_separator", () -> new MachineBlock(GasSeparatorTileEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasSeparatorTileEntity>> GAS_SEPARATOR_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("gas_separator", () -> BlockEntityType.Builder.of(GasSeparatorTileEntity::new, GAS_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasSeparatorContainer>> GAS_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("gas_separator", () -> IForgeContainerType.create(GasSeparatorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric cables">
    public static final BlockRegistryObject CABLE_BLOCK = Registering.BLOCKS_ITEMS.register("cable", () -> new CableBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL).isRedstoneConductor((p1, p2, p3) -> false)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CableTileEntity>> CABLE_TILE_ENTITY_TYPE = Registering.TILE_ENTITIES.register("cable", () -> BlockEntityType.Builder.of(CableTileEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));

    static void init() {}
}