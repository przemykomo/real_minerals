package xyz.przemyk.real_minerals.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.blockentity.*;
import xyz.przemyk.real_minerals.blocks.*;
import xyz.przemyk.real_minerals.cables.CableBlock;
import xyz.przemyk.real_minerals.cables.CableBlockEntity;
import xyz.przemyk.real_minerals.containers.*;
import xyz.przemyk.real_minerals.items.TankItem;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_TAB;

public class MachinesRegistry {
    //<editor-fold desc="Basic machines">
    public static final BlockRegistryObject CRUSHER_BLOCK = Registering.BLOCKS_ITEMS.register("crusher", CrusherBlock::new, ITEM_TAB);
    public static final RegistryObject<BlockEntityType<CrusherBlockEntity>> CRUSHER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("crusher", () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<CrusherContainer>> CRUSHER_CONTAINER = Registering.CONTAINERS.register("crusher", () -> new MenuType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("alloy_furnace", AlloyFurnaceBlock::new, ITEM_TAB);
    public static final RegistryObject<BlockEntityType<AlloyFurnaceBlockEntity>> ALLOY_FURNACE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("alloy_furnace", () -> BlockEntityType.Builder.of(AlloyFurnaceBlockEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = Registering.CONTAINERS.register("alloy_furnace", () -> new MenuType<>(AlloyFurnaceContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric generators">
    public static final BlockRegistryObject BURNING_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("burning_generator", () -> new MachineBlock(SolidGeneratorBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<SolidGeneratorBlockEntity>> BURNING_GENERATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("burning_generator", () -> BlockEntityType.Builder.of(SolidGeneratorBlockEntity::new, BURNING_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<SolidGeneratorContainer>> BURNING_GENERATOR_CONTAINER = Registering.CONTAINERS.register("burning_generator", () -> new MenuType<>(SolidGeneratorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_GENERATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_generator", () -> new MachineBlock(GasGeneratorBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<GasGeneratorBlockEntity>> GAS_GENERATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_generator", () -> BlockEntityType.Builder.of(GasGeneratorBlockEntity::new, GAS_GENERATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasGeneratorContainer>> GAS_GENERATOR_CONTAINER = Registering.CONTAINERS.register("gas_generator", () -> IForgeContainerType.create(GasGeneratorContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Electric machines">
    public static final BlockRegistryObject BATTERY_BLOCK = Registering.BLOCKS_ITEMS.register("battery", () -> new BatteryBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL)), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<BatteryBlockEntity>> BATTERY_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("battery", () -> BlockEntityType.Builder.of(BatteryBlockEntity::new, BATTERY_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<BatteryContainer>> BATTERY_CONTAINER = Registering.CONTAINERS.register("battery", () -> new MenuType<>(BatteryContainer::getClientContainer));

    public static final BlockRegistryObject ELECTRIC_FURNACE_BLOCK = Registering.BLOCKS_ITEMS.register("electric_furnace", () -> new MachineBlock(ElectricFurnaceBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("electric_furnace", () -> BlockEntityType.Builder.of(ElectricFurnaceBlockEntity::new, ELECTRIC_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ElectricFurnaceContainer>> ELECTRIC_FURNACE_CONTAINER = Registering.CONTAINERS.register("electric_furnace", () -> new MenuType<>(ElectricFurnaceContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIZER_BLOCK = Registering.BLOCKS_ITEMS.register("magnetizer", () -> new MachineBlock(MagnetizerBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<MagnetizerBlockEntity>> MAGNETIZER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("magnetizer", () -> BlockEntityType.Builder.of(MagnetizerBlockEntity::new, MAGNETIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagnetizerContainer>> MAGNETIZER_CONTAINER = Registering.CONTAINERS.register("magnetizer", () -> new MenuType<>(MagnetizerContainer::getClientContainer));

    public static final BlockRegistryObject MAGNETIC_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("magnetic_separator", () -> new MachineBlock(MagneticSeparatorBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<MagneticSeparatorBlockEntity>> MAGNETIC_SEPARATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("magnetic_separator", () -> BlockEntityType.Builder.of(MagneticSeparatorBlockEntity::new, MAGNETIC_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MagneticSeparatorContainer>> MAGNETIC_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("magnetic_separator", () -> new MenuType<>(MagneticSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_SEPARATOR_BLOCK = Registering.BLOCKS_ITEMS.register("gas_separator", () -> new MachineBlock(GasSeparatorBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<GasSeparatorBlockEntity>> GAS_SEPARATOR_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_separator", () -> BlockEntityType.Builder.of(GasSeparatorBlockEntity::new, GAS_SEPARATOR_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasSeparatorContainer>> GAS_SEPARATOR_CONTAINER = Registering.CONTAINERS.register("gas_separator", () -> IForgeContainerType.create(GasSeparatorContainer::getClientContainer));

    public static final BlockRegistryObject GAS_ENRICHER_BLOCK = Registering.BLOCKS_ITEMS.register("gas_enricher", () -> new MachineBlock(GasEnricherBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<GasEnricherBlockEntity>> GAS_ENRICHER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("gas_enricher", () -> BlockEntityType.Builder.of(GasEnricherBlockEntity::new, GAS_ENRICHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<GasEnricherContainer>> GAS_ENRICHER_CONTAINER = Registering.CONTAINERS.register("gas_enricher", () -> IForgeContainerType.create(GasEnricherContainer::getClientContainer));

    public static final BlockRegistryObject OXIDIZER_BLOCK = Registering.BLOCKS_ITEMS.register("oxidizer", () -> new MachineBlock(OxidizerBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<OxidizerBlockEntity>> OXIDIZER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("oxidizer", () -> BlockEntityType.Builder.of(OxidizerBlockEntity::new, OXIDIZER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<OxidizerContainer>> OXIDIZER_CONTAINER = Registering.CONTAINERS.register("oxidizer", () -> IForgeContainerType.create(OxidizerContainer::getClientContainer));

    public static final BlockRegistryObject MIXER_BLOCK = Registering.BLOCKS_ITEMS.register("mixer", () -> new MachineBlock(MixerBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<MixerBlockEntity>> MIXER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("mixer", () -> BlockEntityType.Builder.of(MixerBlockEntity::new, MIXER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<MixerContainer>> MIXER_CONTAINER = Registering.CONTAINERS.register("mixer", () -> IForgeContainerType.create(MixerContainer::getClientContainer));

    public static final BlockRegistryObject CHEMICAL_WASHER_BLOCK = Registering.BLOCKS_ITEMS.register("chemical_washer", () -> new MachineBlock(ChemicalWasherBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<ChemicalWasherBlockEntity>> CHEMICAL_WASHER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("chemical_washer", () -> BlockEntityType.Builder.of(ChemicalWasherBlockEntity::new, CHEMICAL_WASHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ChemicalWasherContainer>> CHEMICAL_WASHER_CONTAINER = Registering.CONTAINERS.register("chemical_washer", () -> IForgeContainerType.create(ChemicalWasherContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Multiblocks">
    public static final BlockRegistryObject EVAPORATION_PLANT_CONTROLLER_BLOCK = Registering.BLOCKS_ITEMS.register("evaporation_plant_controller", () -> new MachineBlock(EvaporationPlantControllerBlockEntity::new), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<EvaporationPlantControllerBlockEntity>> EVAPORATION_PLANT_CONTROLLER_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("evaporation_plant_controller", () -> BlockEntityType.Builder.of(EvaporationPlantControllerBlockEntity::new, EVAPORATION_PLANT_CONTROLLER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<MenuType<EvaporationPlantContainer>> EVAPORATION_PLANT_CONTAINER = Registering.CONTAINERS.register("evaporation_plant", () -> IForgeContainerType.create(EvaporationPlantContainer::getClientContainer));
    //</editor-fold>

    //<editor-fold desc="Other">
    public static final BlockRegistryObject CABLE_BLOCK = Registering.BLOCKS_ITEMS.register("cable", () -> new CableBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(1.5F).sound(SoundType.METAL).isRedstoneConductor((p1, p2, p3) -> false)), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("cable", () -> BlockEntityType.Builder.of(CableBlockEntity::new, CABLE_BLOCK.BLOCK.get()).build(null));

    public static final RegistryObject<Block> TANK_BLOCK = Registering.BLOCKS.register("tank", TankBlock::new);
    public static final RegistryObject<Item> TANK_ITEM = Registering.ITEMS.register("tank", () -> new TankItem(TANK_BLOCK.get(), new Item.Properties().tab(ITEM_TAB).stacksTo(1)));
    public static final RegistryObject<BlockEntityType<TankBlockEntity>> TANK_BLOCK_ENTITY_TYPE = Registering.BLOCK_ENTITIES.register("tank", () -> BlockEntityType.Builder.of(TankBlockEntity::new, TANK_BLOCK.get()).build(null));
    //</editor-fold>

    static void init() {}
}