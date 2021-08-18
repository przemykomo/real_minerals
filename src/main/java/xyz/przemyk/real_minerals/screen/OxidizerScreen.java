package xyz.przemyk.real_minerals.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.OxidizerBlockEntity;
import xyz.przemyk.real_minerals.containers.OxidizerContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class OxidizerScreen extends MachineScreen<OxidizerContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/oxidizer.png");

    public OxidizerScreen(OxidizerContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);
        screenModules.add(new TankModule(19, 7, 18, 72, this, menu.blockEntity.doubleFluidTank.input));
        screenModules.add(new TankModule(109, 7, 18, 72, this, menu.blockEntity.doubleFluidTank.output));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(79, 35, this, () -> menu.machineData.get(0), OxidizerBlockEntity.WORKING_TIME_TOTAL));
    }
}
