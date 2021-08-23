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
import net.minecraft.world.level.material.Fluids;
import xyz.przemyk.real_minerals.blockentity.TankBlockEntity;

public class TankRenderer implements BlockEntityRenderer<TankBlockEntity> {

    @SuppressWarnings("unused")
    public TankRenderer(BlockEntityRendererProvider.Context ctx) {}

    @SuppressWarnings("deprecation")
    @Override
    public void render(TankBlockEntity tankBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        float min = 0.0625f;
        float max = 0.9375f;

        if (tankBlockEntity.fluidTank.isEmpty()) {
            return;
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.translucent());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(tankBlockEntity.fluidTank.getFluid().getFluid().getAttributes().getStillTexture(tankBlockEntity.fluidTank.getFluid()));
        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float u1 = sprite.getU1();
        float v1 = sprite.getV1();
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();

        float height = (float) tankBlockEntity.fluidTank.getFluidAmount() * max / tankBlockEntity.fluidTank.getCapacity();
        float vHeight = sprite.getV(height * 16.0f);

        //top
        vertexConsumer.vertex(matrix, min, height, min).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, min, height, max).color(1f, 1f, 1f, 1f).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, height, max).color(1f, 1f, 1f, 1f).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, height, min).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();

        //west
        vertexConsumer.vertex(matrix, min, height, min).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, min, min, min).color(1f, 1f, 1f, 1f).uv(u0, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, min, min, max).color(1f, 1f, 1f, 1f).uv(u1, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, min, height, max).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();

        //north
        vertexConsumer.vertex(matrix, max, height, min).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, max, min, min).color(1f, 1f, 1f, 1f).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, min, min, min).color(1f, 1f, 1f, 1f).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
        vertexConsumer.vertex(matrix, min, height, min).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();

        //east
        vertexConsumer.vertex(matrix, max, height, max).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, min, max).color(1f, 1f, 1f, 1f).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, min, min).color(1f, 1f, 1f, 1f).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, height, min).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();

        //south
        vertexConsumer.vertex(matrix, min, height, max).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, min, min, max).color(1f, 1f, 1f, 1f).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, max, min, max).color(1f, 1f, 1f, 1f).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
        vertexConsumer.vertex(matrix, max, height, max).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();

        //bottom
        vertexConsumer.vertex(matrix, min, min, max).color(1f, 1f, 1f, 1f).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, min, min, min).color(1f, 1f, 1f, 1f).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, min, min).color(1f, 1f, 1f, 1f).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
        vertexConsumer.vertex(matrix, max, min, max).color(1f, 1f, 1f, 1f).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
    }
}
