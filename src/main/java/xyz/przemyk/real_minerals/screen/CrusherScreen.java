package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.przemyk.real_minerals.containers.CrusherContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;

public class CrusherScreen extends MachineScreen<CrusherContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.machineData.get(1), menu.machineData.get(2)));
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);
        if (menu.machineData.get(0) > 0) {
            int pixels = getBurnLeftScaled();
            this.blit(matrixStack, leftPos + 56, topPos + 36 + 12 - pixels, 176, 12 - pixels, 14, pixels + 1);
        }
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = menu.machineData.get(3);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return menu.machineData.get(0) * 13 / burnTimeTotal;
    }
}
