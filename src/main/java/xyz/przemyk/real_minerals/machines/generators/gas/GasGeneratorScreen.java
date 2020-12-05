package xyz.przemyk.real_minerals.machines.generators.gas;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class GasGeneratorScreen extends ContainerScreen<GasGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_generator.png");

    public GasGeneratorScreen(GasGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isPointInRegion(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.energy", container.machineData.get(2)), mouseX, mouseY);
        } else if (isPointInRegion(7, 7, 18, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.gas", container.machineData.get(3)), mouseX, mouseY);
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
        if (machineData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, i + 80, j + 75 - k, 176, 12 - k, 14, k + 1);
        }

        int energy = machineData.get(2);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, i + 153, j + 78 - k, 176, 85 - k, 16, k + 1);
        }

        int gas = machineData.get(3);
        if (gas > 0) {
            int k = gas * 71 / GasGeneratorTileEntity.TANK_VOLUME;
            this.blit(matrixStack, i + 7, j + 78 - k, 192, 71 - k, 18, k + 1);
        }
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = container.machineData.get(1);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return container.machineData.get(0) * 13 / burnTimeTotal;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_243248_b(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
        // don't render "Inventory" text
    }
}
