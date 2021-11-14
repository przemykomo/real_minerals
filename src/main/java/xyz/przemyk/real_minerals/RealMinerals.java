package xyz.przemyk.real_minerals;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.przemyk.real_minerals.datapack.DatapackEvents;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.network.RealMineralsNetworking;

@SuppressWarnings("unused")
@Mod(RealMinerals.MODID)
public class RealMinerals {
    public static final String MODID = "real_minerals";

    public RealMinerals() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registering.init(eventBus);
        Recipes.init(eventBus);
        DatapackEvents.init();
        RealMineralsNetworking.init();
    }

    public static final CreativeModeTab ITEM_TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MachinesRegistry.CRUSHER_BLOCK.ITEM.get());
        }
    };
}