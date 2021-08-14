package xyz.przemyk.real_minerals.datapack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;

public class DatapackEvents {

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(DatapackEvents::addReloadListener);
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new GasBurningReloadListener());
    }
}
