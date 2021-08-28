package xyz.przemyk.real_minerals.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.MixerBlockEntity;
import xyz.przemyk.real_minerals.containers.MixerContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class MixerScreen extends MachineScreen<MixerContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/mixer.png");

    public MixerScreen(MixerContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);
        screenModules.add(new TankModule(35, 18, 18, 50, this, menu.blockEntity.fluidTank.firstInput));
        screenModules.add(new TankModule(55, 18, 18, 50, this, menu.blockEntity.fluidTank.secondInput));
        screenModules.add(new TankModule(111, 18, 18, 50, this, menu.blockEntity.fluidTank.output));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.machineData.get(0), MixerBlockEntity.WORKING_TIME_TOTAL));
    }
}
