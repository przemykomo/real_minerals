package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.tileentity.GasGeneratorTileEntity;

public class GasGeneratorScreen extends AbstractContainerScreen<GasGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_generator.png");

    public GasGeneratorScreen(GasGeneratorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
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
        if (isHovering(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.energy", menu.machineData.get(2)), mouseX, mouseY);
        } else if (isHovering(7, 7, 18, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.gas", menu.tileEntity.fluidTank.getFluid().getFluid().getAttributes().getDisplayName(menu.tileEntity.fluidTank.getFluid()), menu.machineData.get(3)), mouseX, mouseY);
        } else {
            renderTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        if (menu.machineData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, leftPos + 80, topPos + 75 - k, 176, 12 - k, 14, k + 1);
        }

        int energy = menu.machineData.get(2);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, leftPos + 153, topPos + 78 - k, 176, 85 - k, 16, k + 1);
        }

        int gas = menu.machineData.get(3);
        if (gas > 0) {
            int fluidHeight = gas * 68 / GasGeneratorTileEntity.TANK_VOLUME;

            FluidStack fluidStack = menu.tileEntity.fluidTank.getFluid();
            if (!fluidStack.isEmpty()) {
                TextureAtlasSprite fluidSprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
                setColorRGBA(fluidStack.getFluid().getAttributes().getColor(fluidStack));
                renderTiledTextureAtlas(matrixStack, this, fluidSprite, 9, 77 - fluidHeight, 14, fluidHeight, 71, fluidStack.getFluid().getAttributes().isGaseous(fluidStack));
            }
        }

        RenderSystem.setShaderTexture(0, GUI);
        blit(matrixStack, leftPos + 9, topPos + 9, 192, 0, 14, 71);
    }

    public static void renderTiledTextureAtlas(PoseStack matrices, AbstractContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        RenderSystem.setShaderTexture(0, sprite.atlas().location());
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + screen.getGuiLeft();
        int startY = y + screen.getGuiTop();

        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = matrices.last().pose();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getU((16f * renderWidth) / spriteWidth);
                if(upsideDown) {
                    // FIXME: I think this causes tiling errors, look into it
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v2, v1);
                } else {
                    buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
                }
                x2 += renderWidth;
            } while(widthLeft > 0);

            startY += renderHeight;
        } while(height > 0);

        // finish drawing sprites
        builder.end();
        BufferUploader.end(builder);
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).uv(u1, v2).endVertex();
        builder.vertex(matrix, x2, y2, z).uv(u2, v2).endVertex();
        builder.vertex(matrix, x2, y1, z).uv(u2, v1).endVertex();
        builder.vertex(matrix, x1, y1, z).uv(u1, v1).endVertex();
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.setShaderColor(r, g, b, a);
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    private int getBurnLeftScaled() {
        int burnTimeTotal = menu.machineData.get(1);
        if (burnTimeTotal == 0) {
            burnTimeTotal = 200;
        }

        return menu.machineData.get(0) * 13 / burnTimeTotal;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        // don't render "Inventory" text
    }
}
