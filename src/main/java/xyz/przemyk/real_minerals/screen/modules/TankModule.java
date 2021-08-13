package xyz.przemyk.real_minerals.screen.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.GasGeneratorScreen;
import xyz.przemyk.real_minerals.screen.MachineScreen;
import xyz.przemyk.real_minerals.tileentity.GasGeneratorTileEntity;

public class TankModule extends ScreenModule {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/tank_module.png");

    public final int width, height;
    public final FluidTank fluidTank;

    public TankModule(int x, int y, int width, int height, MachineScreen<?> screen, FluidTank fluidTank) {
        super(x, y, screen);
        this.width = width;
        this.height = height;
        this.fluidTank = fluidTank;
    }

    @Override
    public boolean renderHovering(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (screen.isHovering(x, y, width, height, mouseX, mouseY)) {
            FluidStack fluidStack = fluidTank.getFluid();
            screen.renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.fluid", fluidStack.getFluid().getAttributes().getDisplayName(fluidStack), fluidStack.getAmount()), mouseX, mouseY);
            return true;
        }
        return false;
    }

    @Override
    public void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        int amount = fluidTank.getFluidAmount();
        if (amount > 0) {
            int fluidHeight = amount * (height - 4) / fluidTank.getCapacity();

            FluidStack fluidStack = fluidTank.getFluid();
            if (!fluidStack.isEmpty()) {
                TextureAtlasSprite fluidSprite = screen.getMinecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
                setColorRGBA(fluidStack.getFluid().getAttributes().getColor(fluidStack));
                renderTiledTextureAtlas(matrixStack, screen, fluidSprite, x + 2, y + height - 2 - fluidHeight, width - 4, fluidHeight, height - 1, fluidStack.getFluid().getAttributes().isGaseous(fluidStack));
            }
        }

        RenderSystem.setShaderTexture(0, GUI);
        screen.blit(matrixStack, screen.getGuiLeft() + x, screen.getGuiTop() + y, 0, 0, 6, 6);
        screen.blit(matrixStack, screen.getGuiLeft() + x + width - 8, screen.getGuiTop() + y + height - 8, 0, 7, 6, 6);
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
}
