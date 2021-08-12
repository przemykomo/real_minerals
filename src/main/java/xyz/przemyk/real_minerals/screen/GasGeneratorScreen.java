package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.tileentity.GasGeneratorTileEntity;

public class GasGeneratorScreen extends ContainerScreen<GasGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_generator.png");

    public GasGeneratorScreen(GasGeneratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (this.xSize - this.font.getStringPropertyWidth(this.title)) / 2;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isPointInRegion(153, 7, 16, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.energy", container.machineData.get(2)), mouseX, mouseY);
        } else if (isPointInRegion(7, 7, 18, 72, mouseX, mouseY)) {
            renderTooltip(matrixStack, new TranslationTextComponent(RealMinerals.MODID + ".gui.gas", container.tileEntity.fluidTank.getFluid().getFluid().getAttributes().getDisplayName(container.tileEntity.fluidTank.getFluid()), container.machineData.get(3)), mouseX, mouseY);
        } else {
            renderHoveredTooltip(matrixStack, mouseX, mouseY);
        }
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        if (container.machineData.get(0) > 0) {
            int k = getBurnLeftScaled();
            this.blit(matrixStack, guiLeft + 80, guiTop + 75 - k, 176, 12 - k, 14, k + 1);
        }

        int energy = container.machineData.get(2);
        if (energy > 0) {
            int k = energy * 71 / 10000;
            this.blit(matrixStack, guiLeft + 153, guiTop + 78 - k, 176, 85 - k, 16, k + 1);
        }

        int gas = container.machineData.get(3);
        if (gas > 0) {
            int fluidHeight = gas * 68 / GasGeneratorTileEntity.TANK_VOLUME;

            FluidStack fluidStack = container.tileEntity.fluidTank.getFluid();
            if (!fluidStack.isEmpty()) {
                TextureAtlasSprite fluidSprite = minecraft.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
                setColorRGBA(fluidStack.getFluid().getAttributes().getColor(fluidStack));
                renderTiledTextureAtlas(matrixStack, this, fluidSprite, 9, 77 - fluidHeight, 14, fluidHeight, 71, fluidStack.getFluid().getAttributes().isGaseous(fluidStack));
            }
        }

        this.minecraft.getTextureManager().bindTexture(GUI);
        blit(matrixStack, guiLeft + 9, guiTop + 9, 192, 0, 14, 71);
    }

    public static void renderTiledTextureAtlas(MatrixStack matrices, ContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth, boolean upsideDown) {
        screen.getMinecraft().getTextureManager().bindTexture(sprite.getAtlasTexture().getTextureLocation());
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + screen.getGuiLeft();
        int startY = y + screen.getGuiTop();

        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getInterpolatedV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = matrices.getLast().getMatrix();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getInterpolatedU((16f * renderWidth) / spriteWidth);
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
        builder.finishDrawing();
        RenderSystem.enableAlphaTest();
        WorldVertexBufferUploader.draw(builder);
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.pos(matrix, x1, y2, z).tex(u1, v2).endVertex();
        builder.pos(matrix, x2, y2, z).tex(u2, v2).endVertex();
        builder.pos(matrix, x2, y1, z).tex(u2, v1).endVertex();
        builder.pos(matrix, x1, y1, z).tex(u1, v1).endVertex();
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.color4f(r, g, b, a);
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
