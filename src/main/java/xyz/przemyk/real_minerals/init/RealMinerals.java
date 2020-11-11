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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.machines.MachineRecipe;
import xyz.przemyk.real_minerals.machines.alloy_furnace.*;
import xyz.przemyk.real_minerals.machines.crusher.*;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

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

        eventBus.addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);
    }

    public static final IRecipeType<CrusherRecipe> CRUSHER_RECIPE_TYPE = new CrusherRecipeType();
    public static final IRecipeType<AlloyRecipe> ALLOY_RECIPE_TYPE = new AlloyRecipeType();

    private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CRUSHER_RECIPE_TYPE.toString()), CRUSHER_RECIPE_TYPE);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ALLOY_RECIPE_TYPE.toString()), ALLOY_RECIPE_TYPE);
        event.getRegistry().registerAll(CrusherRecipe.SERIALIZER, AlloyRecipe.SERIALIZER);
    }

    @Nullable
    public static MachineRecipe getRecipe(NonNullList<ItemStack> input, World world, IRecipeType<?> recipeType) {
        Set<MachineRecipe> recipes = getAllRecipes(world, recipeType);
        for (MachineRecipe recipe : recipes){
            if (recipe.isValidInput(input)) {
                return recipe;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static<T extends IRecipe<?>> Set<T> getAllRecipes(World world, IRecipeType<?> recipeType) {
        return ((Set<T>) world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == recipeType).collect(Collectors.toSet()));
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(COPPER_ORE.ITEM.get());
        }
    };

    public static final BlockRegistryObject CRUSHER_BLOCK = BLOCKS.register("crusher", () -> new CrusherBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TILE_ENTITY_TYPE = TILE_ENTITIES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, CRUSHER_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<CrusherContainer>> CRUSHER_CONTAINER = CONTAINERS.register("crusher", () -> new ContainerType<>(CrusherContainer::getClientContainer));

    public static final BlockRegistryObject ALLOY_FURNACE_BLOCK = BLOCKS.register("alloy_furnace", () -> new AlloyFurnaceBlock(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F).sound(SoundType.METAL)), ITEM_GROUP);
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<TileEntityType<AlloyFurnaceTileEntity>> ALLOY_FURNACE_TILE_ENTITY_TYPE = TILE_ENTITIES.register("alloy_furnace", () -> TileEntityType.Builder.create(AlloyFurnaceTileEntity::new, ALLOY_FURNACE_BLOCK.BLOCK.get()).build(null));
    public static final RegistryObject<ContainerType<AlloyFurnaceContainer>> ALLOY_FURNACE_CONTAINER = CONTAINERS.register("alloy_furnace", () -> new ContainerType<>(AlloyFurnaceContainer::getClientContainer));

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

    public static final BlockRegistryObject  BRASS_BLOCK = BLOCKS.register("brass_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GOLD).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRASS_DUST = ITEMS.register(  "brass_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register( "brass_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRASS_NUGGET = ITEMS.register("brass_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));

    public static final BlockRegistryObject  BRONZE_BLOCK = BLOCKS.register("bronze_block", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.ADOBE).harvestTool(ToolType.PICKAXE).harvestLevel(0).setRequiresTool().hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ITEM_GROUP);
    public static final RegistryObject<Item> BRONZE_DUST = ITEMS.register(  "bronze_dust", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register( "bronze_ingot", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().group(ITEM_GROUP)));
}