package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.containers.MagnetizerContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.tileentity.MagnetizerTileEntity;

public class MagnetizerScreen extends ContainerScreen<MagnetizerContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/magnetizer.png");

    public MagnetizerScreen(MagnetizerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isPointInRegion(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.energy", container.machineData.get(1)), mouseX, mouseY);
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
        IIntArray machineData = container.machineData;
        int workTime = machineData.get(0);
        if (workTime > 0) {
            this.blit(matrixStack, i + 57, j + 54 + 12 - 13, 176, 12 - 13, 14, 15);

            int l = workTime * 24 / MagnetizerTileEntity.WORKING_TIME_TOTAL;
            this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
        }

        int energy = machineData.get(1);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, i + 153, j + 78 - k, 176, 102 - k, 16, k + 1);
        }
    }
}
