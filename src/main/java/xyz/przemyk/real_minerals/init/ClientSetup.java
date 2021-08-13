package xyz.przemyk.real_minerals.init;

import net.minecraft.client.gui.screens.MenuScreens;
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
        MenuScreens.register(MachinesRegistry.BURNING_GENERATOR_CONTAINER.get(), SolidGeneratorScreen::new);
        MenuScreens.register(MachinesRegistry.GAS_GENERATOR_CONTAINER.get(), GasGeneratorScreen::new);

        MenuScreens.register(MachinesRegistry.CRUSHER_CONTAINER.get(), CrusherScreen::new);
        MenuScreens.register(MachinesRegistry.ALLOY_FURNACE_CONTAINER.get(), AlloyFurnaceScreen::new);
        MenuScreens.register(MachinesRegistry.BATTERY_CONTAINER.get(), BatteryScreen::new);
        MenuScreens.register(MachinesRegistry.ELECTRIC_FURNACE_CONTAINER.get(), ElectricFurnaceScreen::new);
        MenuScreens.register(MachinesRegistry.MAGNETIZER_CONTAINER.get(), MagnetizerScreen::new);
        MenuScreens.register(MachinesRegistry.MAGNETIC_SEPARATOR_CONTAINER.get(), MagneticSeparatorScreen::new);
        MenuScreens.register(MachinesRegistry.GAS_SEPARATOR_CONTAINER.get(), GasSeparatorScreen::new);
    }
}
