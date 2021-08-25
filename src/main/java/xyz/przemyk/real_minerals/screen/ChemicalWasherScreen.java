package xyz.przemyk.real_minerals.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.ChemicalWasherBlockEntity;
import xyz.przemyk.real_minerals.containers.ChemicalWasherContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class ChemicalWasherScreen extends MachineScreen<ChemicalWasherContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/chemical_washer.png");

    public ChemicalWasherScreen(ChemicalWasherContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);
        screenModules.add(new TankModule(35, 18, 18, 51, this, menu.blockEntity.doubleFluidTank.input));
        screenModules.add(new TankModule(109, 18, 18, 51, this, menu.blockEntity.doubleFluidTank.output));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(79, 35, this, () -> menu.machineData.get(0), ChemicalWasherBlockEntity.WORKING_TIME_TOTAL));
    }
}
