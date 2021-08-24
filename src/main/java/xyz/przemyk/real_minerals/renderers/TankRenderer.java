package xyz.przemyk.real_minerals.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.blockentity.TankBlockEntity;
import xyz.przemyk.real_minerals.util.Utils;

public class TankRenderer implements BlockEntityRenderer<TankBlockEntity> {

    @SuppressWarnings("unused")
    public TankRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(TankBlockEntity tankBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        FluidStack fluidStack = tankBlockEntity.fluidTank.getFluid();

        if (fluidStack.isEmpty()) {
            return;
        }

        renderTankFluid(poseStack, bufferSource, combinedLight, fluidStack, tankBlockEntity.fluidTank.getCapacity());
    }

    public static final float MIN = 0.0625f;
    public static final float MAX = 0.9375f;

    @SuppressWarnings("deprecation")
    public static void renderTankFluid(PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, FluidStack fluidStack, int capacity) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.translucent());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float u1 = sprite.getU1();
        float v1 = sprite.getV1();
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        float height = (float) fluidStack.getAmount() * MAX / capacity;
        float vHeight = sprite.getV(height * 16.0f);

        int color = fluidStack.getFluid().getAttributes().getColor(fluidStack);
        int red = Utils.red(color);
        int green = Utils.green(color);
        int blue = Utils.blue(color);

        //top
        vertexConsumer.vertex(matrix, MIN, height, MIN).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MIN, height, MAX).color(red, green, blue, 255).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, height, MAX).color(red, green, blue, 255).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, height, MIN).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();

        //west
        vertexConsumer.vertex(matrix, MIN, height, MIN).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MIN, MIN, MIN).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MIN, MIN, MAX).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MIN, height, MAX).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();

        //north
        vertexConsumer.vertex(matrix, MAX, height, MIN).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MIN).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, MIN, MIN, MIN).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, MIN, height, MIN).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();

        //east
        vertexConsumer.vertex(matrix, MAX, height, MAX).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MAX).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MIN).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, height, MIN).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();

        //south
        vertexConsumer.vertex(matrix, MIN, height, MAX).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, MIN, MIN, MAX).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MAX).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, MAX, height, MAX).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();

        //bottom
        vertexConsumer.vertex(matrix, MIN, MIN, MAX).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MIN, MIN, MIN).color(red, green, blue, 255).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MIN).color(red, green, blue, 255).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, MAX, MIN, MAX).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
    }
}
