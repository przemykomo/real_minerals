package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import xyz.przemyk.real_minerals.containers.SolidGeneratorContainer;
import xyz.przemyk.real_minerals.RealMinerals;

public class SolidGeneratorScreen extends AbstractContainerScreen<SolidGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/burning_generator.png");

    public SolidGeneratorScreen(SolidGeneratorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isHovering(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.energy",menu.machineData.get(2)), mouseX, mouseY);
        } else {
            renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        ContainerData machineData = menu.machineData;
        if (machineData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, i + 80, j + 57 - k, 176, 12 - k, 14, k + 1);
        }

        int energy = machineData.get(2);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, i + 153, j + 78 - k, 176, 85 - k, 16, k + 1);
        }
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = menu.machineData.get(1);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return menu.machineData.get(0) * 13 / burnTimeTotal;
    }
}
