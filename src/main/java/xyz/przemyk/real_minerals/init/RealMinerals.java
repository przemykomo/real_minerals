package xyz.przemyk.real_minerals.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.machines.CrusherBlock;
import xyz.przemyk.real_minerals.machines.CrusherContainer;
import xyz.przemyk.real_minerals.machines.CrusherTileEntity;

@SuppressWarnings("unused")
@Mod(RealMinerals.MODID)
public class RealMinerals {
    public static final String MODID = "real_minerals";
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MODID);
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MODID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public RealMinerals() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(COPPER_ORE.ITEM.get());
        }
    };

    public static final BlockRegistryObject CRUSHER_BLOCK = BLOCKS.register("crusher", () -> new CrusherBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> IForgeContainerType.create(((windowId, inv, data) -> new CrusherContainer(windowId, inv.player.getEntityWorld(), data.readBlockPos(), inv, inv.player))));

    public static final BlockRegistryObject  COPPER_BLOCK = BLOCKS.register("copper_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  COPPER_ORE = BLOCKS.register("copper_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  LEAD_ORE = BLOCKS.register("lead_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  MAGNESIUM_BLOCK = BLOCKS.register("magnesium_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> MAGNESIUM_DUST = ITEMS.register(  "magnesium_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_INGOT = ITEMS.register( "magnesium_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_NUGGET = ITEMS.register("magnesium_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  MAGNESIUM_ORE = BLOCKS.register(  "magnesium_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  NICKEL_BLOCK = BLOCKS.register("nickel_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(1).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> NICKEL_DUST = ITEMS.register(  "nickel_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register( "nickel_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> NICKEL_NUGGET = ITEMS.register("nickel_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  NICKEL_ORE = BLOCKS.register(  "nickel_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  PLATINUM_BLOCK = BLOCKS.register("platinum_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> PLATINUM_DUST = ITEMS.register(  "platinum_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_INGOT = ITEMS.register( "platinum_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> PLATINUM_NUGGET = ITEMS.register("platinum_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  PLATINUM_ORE = BLOCKS.register(  "platinum_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(2).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register(  "silver_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register( "silver_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  SILVER_ORE = BLOCKS.register(  "silver_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

    public static final BlockRegistryObject  TIN_BLOCK = BLOCKS.register("tin_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> TIN_DUST = ITEMS.register(  "tin_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register( "tin_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final BlockRegistryObject  TIN_ORE = BLOCKS.register(  "tin_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(3.0F, 3.0F)), ITEM_GROUP);

}