package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import xyz.przemyk.real_minerals.screen.modules.ScreenModule;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public final ResourceLocation backgroundTexture;
    protected final List<ScreenModule> screenModules = new ArrayList<>();

    public MachineScreen(T screenContainer, Inventory inventory, Component title, ResourceLocation backgroundTexture) {
        super(screenContainer, inventory, title);
        this.backgroundTexture = backgroundTexture;
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        for (ScreenModule screenModule : screenModules) {
            if (screenModule.renderHovering(matrixStack, mouseX, mouseY, partialTicks)) {
                return;
            }
        }
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return super.isHovering(x, y, width, height, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, backgroundTexture);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        for (ScreenModule screenModule : screenModules) {
            screenModule.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        }
    }
}
