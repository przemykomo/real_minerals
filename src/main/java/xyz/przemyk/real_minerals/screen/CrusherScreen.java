package xyz.przemyk.real_minerals.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.CrusherBlockEntity;
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;

public class CrusherScreen extends MachineScreen<CrusherContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.machineData.get(0), CrusherBlockEntity.WORKING_TIME_TOTAL));
    }
}
