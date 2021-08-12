package xyz.przemyk.real_minerals.init;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.BatteryScreen;
import xyz.przemyk.real_minerals.screen.ElectricFurnaceScreen;
import xyz.przemyk.real_minerals.screen.GasSeparatorScreen;
import xyz.przemyk.real_minerals.screen.GasGeneratorScreen;
import xyz.przemyk.real_minerals.screen.SolidGeneratorScreen;
import xyz.przemyk.real_minerals.screen.MagneticSeparatorScreen;
import xyz.przemyk.real_minerals.screen.MagnetizerScreen;
import xyz.przemyk.real_minerals.screen.AlloyFurnaceScreen;
import xyz.przemyk.real_minerals.screen.CrusherScreen;

@Mod.EventBusSubscriber(modid = RealMinerals.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registering.BURNING_GENERATOR_CONTAINER.get(), SolidGeneratorScreen::new);
        ScreenManager.registerFactory(Registering.GAS_GENERATOR_CONTAINER.get(), GasGeneratorScreen::new);

        ScreenManager.registerFactory(Registering.CRUSHER_CONTAINER.get(), CrusherScreen::new);
        ScreenManager.registerFactory(Registering.ALLOY_FURNACE_CONTAINER.get(), AlloyFurnaceScreen::new);
        ScreenManager.registerFactory(Registering.BATTERY_CONTAINER.get(), BatteryScreen::new);
        ScreenManager.registerFactory(Registering.ELECTRIC_FURNACE_CONTAINER.get(), ElectricFurnaceScreen::new);
        ScreenManager.registerFactory(Registering.MAGNETIZER_CONTAINER.get(), MagnetizerScreen::new);
        ScreenManager.registerFactory(Registering.MAGNETIC_SEPARATOR_CONTAINER.get(), MagneticSeparatorScreen::new);
        ScreenManager.registerFactory(Registering.GAS_SEPARATOR_CONTAINER.get(), GasSeparatorScreen::new);
    }
}
