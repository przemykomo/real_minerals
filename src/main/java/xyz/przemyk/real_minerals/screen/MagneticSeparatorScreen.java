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
import xyz.przemyk.real_minerals.containers.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.tileentity.MagneticSeparatorTileEntity;

public class MagneticSeparatorScreen extends AbstractContainerScreen<MagneticSeparatorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/magnetic_separator.png");

    public MagneticSeparatorScreen(MagneticSeparatorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isHovering(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.energy", menu.machineData.get(1)), mouseX, mouseY);
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
        int workTime = machineData.get(0);
        if (workTime > 0) {
            this.blit(matrixStack, i + 46, j + 46 + 12 - 13, 176, 12 - 13, 14, 13 + 1);

            int l = workTime * 31 / MagneticSeparatorTileEntity.WORKING_TIME_TOTAL;
            this.blit(matrixStack, i + 66, j + 26, 176, 14, l, 33);
        }

        int energy = machineData.get(1);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, i + 153, j + 78 - k, 176, 118 - k, 16, k + 1);
        }
    }
}
