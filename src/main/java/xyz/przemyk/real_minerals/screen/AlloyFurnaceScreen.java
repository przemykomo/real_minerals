package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.RealMinerals;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        ContainerData alloyFurnaceData = menu.machineData;
        if (alloyFurnaceData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, i + 38, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int crushTime = alloyFurnaceData.get(1);
        int l =  crushTime != 0 ? crushTime * 24 / alloyFurnaceData.get(2) : 0;
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = menu.machineData.get(3);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return menu.machineData.get(0) * 13 / burnTimeTotal;
    }
}
