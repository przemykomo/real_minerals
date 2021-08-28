package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.EvaporationPlantControllerBlockEntity;
import xyz.przemyk.real_minerals.containers.EvaporationPlantContainer;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class EvaporationPlantScreen extends MachineScreen<EvaporationPlantContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/evaporation_plant.png");

    public EvaporationPlantScreen(EvaporationPlantContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);
        screenModules.add(new TankModule(55, 18, 18, 50, this, menu.blockEntity.fluidTank));
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.blockEntity.fluidStackInProgress.getAmount(), EvaporationPlantControllerBlockEntity.FLUID_AMOUNT_TO_RESULT) {
            @Override
            public boolean renderHovering(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
                if (screen.isHovering(x, y, 24, 16, mouseX, mouseY)) {
                    TankModule.renderHoveringFluidStackInfo(screen, matrixStack, mouseX, mouseY, menu.blockEntity.fluidStackInProgress);
                    return true;
                }
                return false;
            }
        });
    }
}
