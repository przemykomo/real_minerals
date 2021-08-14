package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.przemyk.real_minerals.containers.MagnetizerContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.blockentity.MagnetizerBlockEntity;

public class MagnetizerScreen extends MachineScreen<MagnetizerContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/magnetizer.png");

    public MagnetizerScreen(MagnetizerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);

        int workTime = menu.machineData.get(0);
        if (workTime > 0) {
            this.blit(matrixStack, this.leftPos + 57, this.topPos + 54 + 12 - 13, 176, 12 - 13, 14, 15);

            int l = workTime * 24 / MagnetizerBlockEntity.WORKING_TIME_TOTAL;
            this.blit(matrixStack, this.leftPos + 79, this.topPos + 34, 176, 14, l + 1, 16);
        }
    }
}
