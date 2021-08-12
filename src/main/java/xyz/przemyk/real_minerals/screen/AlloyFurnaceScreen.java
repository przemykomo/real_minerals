package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.RealMinerals;

public class AlloyFurnaceScreen extends ContainerScreen<AlloyFurnaceContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        IIntArray alloyFurnaceData = container.machineData;
        if (alloyFurnaceData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, i + 38, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int crushTime = alloyFurnaceData.get(1);
        int l =  crushTime != 0 ? crushTime * 24 / alloyFurnaceData.get(2) : 0;
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = container.machineData.get(3);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return container.machineData.get(0) * 13 / burnTimeTotal;
    }
}
