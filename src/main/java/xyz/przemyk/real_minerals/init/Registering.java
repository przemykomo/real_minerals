package xyz.przemyk.real_minerals.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.blockentity.MagnetBlockEntity;
import xyz.przemyk.real_minerals.blocks.MagnetBlock;
import xyz.przemyk.real_minerals.fluid.*;
import xyz.przemyk.real_minerals.fluid.tank.EnrichedShaleGasFluid;
import xyz.przemyk.real_minerals.items.MagnetItem;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_TAB;
import static xyz.przemyk.real_minerals.RealMinerals.MODID;

@SuppressWarnings({"unused"})
public class Registering {

    private Registering() {}

    public static final BlockDeferredRegister BLOCKS_ITEMS = new BlockDeferredRegister(MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static void init(IEventBus eventBus) {
        BLOCKS_ITEMS.register(eventBus);
        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
        FLUIDS.register(eventBus);
        BLOCKS.register(eventBus);
        MachinesRegistry.init();
        StoneMinerals.init();
        GravelMinerals.init();
        ObsidianMinerals.init();
        WorldGenRegistry.init(eventBus);
    }

    //<editor-fold desc="Alloys">
    public static final BlockRegistryObject BRASS_BLOCK = storageBlock("brass_block", MaterialColor.GOLD);
    public static final RegistryMetalSet BRASS_ITEMS = new RegistryMetalSet("brass");

    public static final BlockRegistryObject BRONZE_BLOCK = storageBlock("bronze_block", MaterialColor.COLOR_ORANGE);
    public static final RegistryMetalSet BRONZE_ITEMS = new RegistryMetalSet("bronze");

    public static final BlockRegistryObject ALNICO_BLOCK = storageBlock("alnico_block", MaterialColor.GOLD);
    public static final RegistryMetalSet ALNICO_ITEMS = new RegistryMetalSet("alnico");

    public static final BlockRegistryObject MAGNETIZED_ALNICO_BLOCK = BLOCKS_ITEMS.register("magnetized_alnico_block", () -> new MagnetBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_TAB);
    public static final RegistryObject<BlockEntityType<MagnetBlockEntity>> MAGNET_BLOCK_ENTITY_TYPE = BLOCK_ENTITIES.register("magnet", () -> BlockEntityType.Builder.of(MagnetBlockEntity::new, MAGNETIZED_ALNICO_BLOCK.BLOCK.get()).build(null));
    public static final RegistryMetalSet MAGNETIZED_ALNICO_ITEMS = new RegistryMetalSet("magnetized_alnico");
    public static final RegistryObject<Item> MAGNETIZED_ALNICO_GEAR = Registering.simpleItem("magnetized_alnico_gear");
    //</editor-fold>

    //<editor-fold desc="Misc">
    public static final RegistryObject<MagnetItem> MAGNET = ITEMS.register("magnet", () -> new MagnetItem(new Item.Properties().tab(ITEM_TAB)));

    public static final BlockRegistryObject METEORITE = BLOCKS_ITEMS.register("meteorite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)), ITEM_TAB);
    public static final RegistryMetalSet METEORITE_IRON_ITEMS = new RegistryMetalSet("meteorite_iron");

    //TODO: maybe remove all buckets and just use tank?
    public static final BlockRegistryObject SHALE_GAS_STONE = BLOCKS_ITEMS.register("shale_gas_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ITEM_TAB);
    public static final RegistryObject<Fluid> SHALE_GAS_FLUID = FLUIDS.register("shale_gas", ShaleGasFluid::new);
    public static final RegistryObject<GasBucketItem> SHALE_GAS_BUCKET = ITEMS.register("shale_gas_bucket", () -> new GasBucketItem(SHALE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_TAB)));

    public static final RegistryObject<EnrichedShaleGasFluid> ENRICHED_SHALE_GAS_FLUID = FLUIDS.register("enriched_shale_gas", EnrichedShaleGasFluid::new);
    public static final RegistryObject<GasBucketItem> ENRICHED_SHALE_GAS_BUCKET = ITEMS.register("enriched_shale_gas_bucket", () -> new GasBucketItem(ENRICHED_SHALE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_TAB)));

    public static final RegistryObject<SulfurDioxideGasFluid> SULFUR_DIOXIDE_GAS_FLUID = FLUIDS.register("sulfur_dioxide_gas", SulfurDioxideGasFluid::new);
    public static final RegistryObject<GasBucketItem> SULFUR_DIOXIDE_GAS_BUCKET = ITEMS.register("sulfur_dioxide_gas_bucket", () -> new GasBucketItem(SULFUR_DIOXIDE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_TAB)));

    public static final RegistryObject<SulfurTrioxideGasFluid> SULFUR_TRIOXIDE_GAS_FLUID = FLUIDS.register("sulfur_trioxide_gas", SulfurTrioxideGasFluid::new);
    public static final RegistryObject<GasBucketItem> SULFUR_TRIOXIDE_GAS_BUCKET = ITEMS.register("sulfur_trioxide_gas_bucket", () -> new GasBucketItem(SULFUR_TRIOXIDE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_TAB)));

    public static final RegistryObject<DummyFluid> ACID_FLUID = FLUIDS.register("acid", () -> new DummyFluid(false));
    //</editor-fold>

    static RegistryObject<Item> simpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(ITEM_TAB)));
    }

    static BlockRegistryObject storageBlock(String name, MaterialColor materialColor) {
        return BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.METAL, materialColor).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_TAB);
    }
}
