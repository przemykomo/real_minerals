package xyz.przemyk.real_minerals.init;

import net.minecraft.world.item.*;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.items.FireSwordItem;
import xyz.przemyk.real_minerals.loot.AutosmeltLootModifier;

import static xyz.przemyk.real_minerals.RealMinerals.ITEM_TAB;

@SuppressWarnings("unused")
public class ToolsRegistry {

    static void init() {}

    public static final RegistryObject<Item> MAGNESIUM_PICKAXE = Registering.ITEMS.register("magnesium_pickaxe", () -> new PickaxeItem(Tiers.DIAMOND, 1, -2.8F, new Item.Properties().tab(ITEM_TAB)));
    public static final RegistryObject<Item> MAGNESIUM_SHOVEL = Registering.ITEMS.register("magnesium_shovel", () -> new ShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties().tab(ITEM_TAB)));
    public static final RegistryObject<Item> MAGNESIUM_AXE = Registering.ITEMS.register("magnesium_axe", () -> new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties().tab(ITEM_TAB)));
    public static final RegistryObject<Item> MAGNESIUM_SWORD = Registering.ITEMS.register("magnesium_sword", () -> new FireSwordItem(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().tab(ITEM_TAB)));
    public static final RegistryObject<Item> MAGNESIUM_HOE = Registering.ITEMS.register("magnesium_hoe", () -> new HoeItem(Tiers.DIAMOND, -3, 0.0F, new Item.Properties().tab(ITEM_TAB)));
    public static final RegistryObject<AutosmeltLootModifier.Serializer> AUTOSMELT = Registering.GLOBAL_LOOT_MODIFIERS.register("autosmelt", AutosmeltLootModifier.Serializer::new);
}
