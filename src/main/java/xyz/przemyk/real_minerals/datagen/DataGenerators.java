package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import xyz.przemyk.real_minerals.init.RealMinerals;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new BlockModels(generator, RealMinerals.MODID, event.getExistingFileHelper()));
        generator.addProvider(new ItemModels(generator, RealMinerals.MODID, event.getExistingFileHelper()));
        generator.addProvider(new BlockStates(generator, RealMinerals.MODID, event.getExistingFileHelper()));
        generator.addProvider(new LootTables(generator));
    }
}
