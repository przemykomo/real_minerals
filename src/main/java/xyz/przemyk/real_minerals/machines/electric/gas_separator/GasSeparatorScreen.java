package xyz.przemyk.real_minerals.machines.electric.gas_separator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidAttributes;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class GasSeparatorScreen extends ContainerScreen<GasSeparatorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_separator.png");

    public GasSeparatorScreen(GasSeparatorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isPointInRegion(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.energy", container.machineData.get(1)), mouseX, mouseY);
        } else if (isPointInRegion(110, 7, 18, 35, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.gas", container.machineData.get(2)), mouseX, mouseY);
        } else  {
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
            this.blit(matrixStack, i + 46, j + 46 + 12 - 13, 176, 12 - 13, 14, 13 + 1);

            int l = workTime * 31 / GasSeparatorTileEntity.WORKING_TIME_TOTAL;
            this.blit(matrixStack, i + 66, j + 26, 176, 14, l, 33);
        }

        int fluidAmount = machineData.get(2);
        if (fluidAmount > 0) {
            int k = fluidAmount * 34 / FluidAttributes.BUCKET_VOLUME;
            this.blit(matrixStack, i + 110, j + 41 - k, 192, 81 - k, 18, k + 1);
        }

        int energy = machineData.get(1);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, i + 153, j + 78 - k, 176, 118 - k, 16, k + 1);
        }
    }
}
