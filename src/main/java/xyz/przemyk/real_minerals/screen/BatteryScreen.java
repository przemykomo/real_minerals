package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.containers.BatteryContainer;
import xyz.przemyk.real_minerals.RealMinerals;

public class BatteryScreen extends ContainerScreen<BatteryContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/battery.png");

    public BatteryScreen(BatteryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isPointInRegion(80, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.energy",container.machineData.get(0)), mouseX, mouseY);
        } else {
            renderHoveredTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        int energy = container.machineData.get(0);
        if (energy > 0) {
            int k = energy * 71 / 1_000_000;
            this.blit(matrixStack, i + 80, j + 78 - k, 176, 85 - k, 16, k + 1);
        }
    }
}
