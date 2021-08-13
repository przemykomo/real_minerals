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
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    public static void init(IEventBus eventBus) {
        BLOCKS_ITEMS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
        FEATURES.register(eventBus);
        FLUIDS.register(eventBus);
        StoneMinerals.init();
        GravelMinerals.init();
        MachinesRegistry.init();
    }

    //<editor-fold desc="Alloys">
    public static final BlockRegistryObject BRASS_BLOCK = storageBlock("brass_block", MaterialColor.GOLD);
    public static final RegistryMetalSet BRASS_ITEMS = new RegistryMetalSet("brass");

    public static final BlockRegistryObject BRONZE_BLOCK = storageBlock("bronze_block", MaterialColor.COLOR_ORANGE);
    public static final RegistryMetalSet BRONZE_ITEMS = new RegistryMetalSet("bronze");
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

    static RegistryObject<Item> simpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    }

    static BlockRegistryObject storageBlock(String name, MaterialColor materialColor) {
        return BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.METAL, materialColor).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    }
}
