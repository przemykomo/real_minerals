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
import xyz.przemyk.real_minerals.fluid.*;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_GROUP;
import static xyz.przemyk.real_minerals.RealMinerals.MODID;

@SuppressWarnings({"unused"})
public class Registering {

    private Registering() {}

    public static final BlockDeferredRegister BLOCKS_ITEMS = new BlockDeferredRegister(MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

    public static void init(IEventBus eventBus) {
        BLOCKS_ITEMS.register(eventBus);
        ITEMS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
        FLUIDS.register(eventBus);
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
    //</editor-fold>

    //<editor-fold desc="Misc">
    public static final BlockRegistryObject METEORITE = BLOCKS_ITEMS.register("meteorite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)), ITEM_GROUP);
    public static final RegistryMetalSet METEORITE_IRON_ITEMS = new RegistryMetalSet("meteorite_iron");

    public static final BlockRegistryObject SHALE_GAS_STONE = BLOCKS_ITEMS.register("shale_gas_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ITEM_GROUP);
    public static final RegistryObject<Fluid> SHALE_GAS_FLUID = FLUIDS.register("shale_gas", ShaleGasFluid::new);
    public static final RegistryObject<GasBucketItem> SHALE_GAS_BUCKET = ITEMS.register("shale_gas_bucket", () -> new GasBucketItem(SHALE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_GROUP)));

    public static final RegistryObject<EnrichedShaleGasFluid> ENRICHED_SHALE_GAS_FLUID = FLUIDS.register("enriched_shale_gas", EnrichedShaleGasFluid::new);
    public static final RegistryObject<GasBucketItem> ENRICHED_SHALE_GAS_BUCKET = ITEMS.register("enriched_shale_gas_bucket", () -> new GasBucketItem(ENRICHED_SHALE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_GROUP)));

    public static final RegistryObject<SulfurDioxideGasFluid> SULFUR_DIOXIDE_GAS_FLUID = FLUIDS.register("sulfur_dioxide_gas", SulfurDioxideGasFluid::new);
    public static final RegistryObject<GasBucketItem> SULFUR_DIOXIDE_GAS_BUCKET = ITEMS.register("sulfur_dioxide_gas_bucket", () -> new GasBucketItem(SULFUR_DIOXIDE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_GROUP)));

    public static final RegistryObject<SulfurTrioxideGasFluid> SULFUR_TRIOXIDE_GAS_FLUID = FLUIDS.register("sulfur_trioxide_gas", SulfurTrioxideGasFluid::new);
    public static final RegistryObject<GasBucketItem> SULFUR_TRIOXIDE_GAS_BUCKET = ITEMS.register("sulfur_trioxide_gas_bucket", () -> new GasBucketItem(SULFUR_TRIOXIDE_GAS_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ITEM_GROUP)));
    //</editor-fold>

    static RegistryObject<Item> simpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    }

    static BlockRegistryObject storageBlock(String name, MaterialColor materialColor) {
        return BLOCKS_ITEMS.register(name, () -> new Block(BlockBehaviour.Properties.of(Material.METAL, materialColor).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    }
}
