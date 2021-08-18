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
import xyz.przemyk.real_minerals.cables.CableBlockEntity;
import xyz.przemyk.real_minerals.containers.*;
import xyz.przemyk.real_minerals.blockentity.*;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;

public class MachinesRegistry {
    //<editor-fold desc="Basic machines">
    public static final BlockRegistryObject CRUSHER_BLOCK = Registering.BLOCKS_ITEMS.register("crusher", CrusherBlock::new, ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("crusher", () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrusherContainer>> CRUSHER_CONTAINER = Registering.CONTAINERS.register("crusher", () -> new MenuType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("alloy_furnace", AlloyFurnaceBlock::new, ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = Registering.CONTAINERS.register("alloy_furnace", () -> new MenuType<>(AlloyFurnaceContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric generators">
    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("burning_generator", () -> new MachineBlock(SolidGeneratorBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<SolidGeneratorBlockEntity>> BURNING_GENERATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("burning_generator", () -> BlockEntityType.Builder.of(SolidGeneratorBlockEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SolidGeneratorContainer>> BURNING_GENERATOR_CONTAINER = Registering.CONTAINERS.register("burning_generator", () -> new MenuType<>(SolidGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_generator", () -> new MachineBlock(GasGeneratorBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasGeneratorBlockEntity>> GAS_GENERATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_generator", () -> BlockEntityType.Builder.of(GasGeneratorBlockEntity::new, GAS_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasGeneratorContainer>> GAS_GENERATOR_CONTAINER = Registering.CONTAINERS.register("gas_generator", () -> IForgeContainerType.create(GasGeneratorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric machines">
    public static final BlockRegistryObject BATTERY_BLOCK = Registering.BLOCKS_ITEMS.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<BatteryBlockEntity>> BATTERY_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("battery", () -> BlockEntityType.Builder.of(BatteryBlockEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BatteryContainer>> BATTERY_CONTAINER = Registering.CONTAINERS.register("battery", () -> new MenuType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("electric_furnace", () -> new MachineBlock(ElectricFurnaceBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = Registering.CONTAINERS.register("electric_furnace", () -> new MenuType<>(ElectricFurnaceContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIZER_BLOCK = Registering.BLOCKS_ITEMS.register("magnetizer", () -> new MachineBlock(MagnetizerBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagnetizerBlockEntity>> MAGNETIZER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("magnetizer", () -> BlockEntityType.Builder.of(MagnetizerBlockEntity::new, MAGNETIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagnetizerContainer>> MAGNETIZER_CONTAINER = Registering.CONTAINERS.register("magnetizer", () -> new MenuType<>(MagnetizerContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIC_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("magnetic_separator", () -> new MachineBlock(MagneticSeparatorBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<MagneticSeparatorBlockEntity>> MAGNETIC_SEPARATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("magnetic_separator", () -> BlockEntityType.Builder.of(MagneticSeparatorBlockEntity::new, MAGNETIC_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagneticSeparatorContainer>> MAGNETIC_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("magnetic_separator", () -> new MenuType<>(MagneticSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_separator", () -> new MachineBlock(GasSeparatorBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasSeparatorBlockEntity>> GAS_SEPARATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_separator", () -> BlockEntityType.Builder.of(GasSeparatorBlockEntity::new, GAS_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasSeparatorContainer>> GAS_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("gas_separator", () -> IForgeContainerType.create(GasSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_ENRICHER_BLOCK = Registering.BLOCKS_ITEMS.register("gas_enricher", () -> new MachineBlock(GasEnricherBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<GasEnricherBlockEntity>> GAS_ENRICHER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_enricher", () -> BlockEntityType.Builder.of(GasEnricherBlockEntity::new, GAS_ENRICHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasEnricherContainer>> GAS_ENRICHER_CONTAINER = Registering.CONTAINERS.register("gas_enricher", () -> IForgeContainerType.create(GasEnricherContainer::getClientContainer));

    public static final BlockRegistryObject OXIDIZER_BLOCK = Registering.BLOCKS_ITEMS.register("oxidizer", () -> new MachineBlock(OxidizerBlockEntity::new), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<OxidizerBlockEntity>> OXIDIZER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("oxidizer", () -> BlockEntityType.Builder.of(OxidizerBlockEntity::new, OXIDIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<OxidizerContainer>> OXIDIZER_CONTAINER = Registering.CONTAINERS.register("oxidizer", () -> IForgeContainerType.create(OxidizerContainer::getClientContainer));

    //</editor-fold>

    //<editor-fold desc="Electric cables">
    public static final BlockRegistryObject CABLE_BLOCK = Registering.BLOCKS_ITEMS.register("cable", () -> new CableBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL).isRedstoneConductor((p1, p2, p3) -> false)), ITEM_GROUP);
    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("cable", () -> BlockEntityType.Builder.of(CableBlockEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));
    //</editor-fold>

    static void init() {}
}