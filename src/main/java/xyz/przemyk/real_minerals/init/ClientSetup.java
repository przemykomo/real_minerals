package xyz.przemyk.real_minerals.init;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.przemyk.real_minerals.machines.alloy_furnace.AlloyFurnaceScreen;
import xyz.przemyk.real_minerals.machines.crusher.CrusherScreen;

@Mod.EventBusSubscriber(modid = RealMinerals.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registering.CRUSHER_CONTAINER.get(), CrusherScreen::new);
        ScreenManager.registerFactory(Registering.ALLOY_FURNACE_CONTAINER.get(), AlloyFurnaceScreen::new);
    }
}
