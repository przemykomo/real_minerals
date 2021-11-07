package xyz.przemyk.real_minerals.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.ManaExtractorBlockEntity;
import xyz.przemyk.real_minerals.containers.ManaExtractorContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class ManaExtractorScreen extends MachineScreen<ManaExtractorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/mana_extractor.png");

    public ManaExtractorScreen(ManaExtractorContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);

        screenModules.add(new TankModule(111, 18, 18, 50, this, menu.blockEntity.fluidTank));
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.machineData.get(0), ManaExtractorBlockEntity.WORKING_TIME_TOTAL));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
    }
}
