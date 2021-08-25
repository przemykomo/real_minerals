package xyz.przemyk.real_minerals.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.blockentity.EvaporationPlantControllerBlockEntity;
import xyz.przemyk.real_minerals.util.Utils;

public class EvaporationPlantRenderer implements BlockEntityRenderer<EvaporationPlantControllerBlockEntity> {

    @SuppressWarnings("unused")
    public EvaporationPlantRenderer(BlockEntityRendererProvider.Context ctx) {}

    @SuppressWarnings("deprecation")
    @Override
    public void render(EvaporationPlantControllerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        FluidStack fluidStack = blockEntity.fluidTank.getFluid();
        int capacity = blockEntity.fluidTank.getCapacity();

        if (fluidStack.isEmpty() || capacity == 0 || blockEntity.innerCuboid == null) {
            return;
        }

        BlockPos pos = blockEntity.getBlockPos();
        BlockPos minPos = blockEntity.innerCuboid.begin();
        BlockPos maxPos = blockEntity.innerCuboid.end().offset(-1, 0, -1);

        poseStack.pushPose();
        poseStack.translate(minPos.getX() - pos.getX(), 0, minPos.getZ() - pos.getZ());

        combinedLight = LevelRenderer.getLightColor(blockEntity.getLevel(), minPos);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.translucent());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
        float u0 = sprite.getU0();
        float v0 = sprite.getV0();
        float u1 = sprite.getU1();
        float v1 = sprite.getV1();

        float height = (float) fluidStack.getAmount() * TankRenderer.MAX / capacity;
        float vHeight = sprite.getV(height * 16.0f);

        int color = fluidStack.getFluid().getAttributes().getColor(fluidStack);
        int red = Utils.red(color);
        int green = Utils.green(color);
        int blue = Utils.blue(color);

        for (int x = minPos.getX(); x <= maxPos.getX(); x++, poseStack.translate(1, 0, 0)) {
            poseStack.pushPose();
            for (int z = minPos.getZ(); z <= maxPos.getZ(); z++, poseStack.translate(0, 0, 1)) {
                Matrix4f matrix = poseStack.last().pose();
                Matrix3f normal = poseStack.last().normal();
                //top
                vertexConsumer.vertex(matrix, 0, height, 0).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 0, height, 1).color(red, green, blue, 255).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 1, height, 1).color(red, green, blue, 255).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 1, height, 0).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, 1f, 0f).endVertex();

                //bottom
                vertexConsumer.vertex(matrix, 0, 0, 1).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 0, 0, 0).color(red, green, blue, 255).uv(u1, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 1, 0, 0).color(red, green, blue, 255).uv(u0, v1).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();
                vertexConsumer.vertex(matrix, 1, 0, 1).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, 0f, -1f, 0f).endVertex();

                if (x == minPos.getX()) {
                    //west
                    vertexConsumer.vertex(matrix, 0, height, 0).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 0, 0, 0).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 0, 0, 1).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 0, height, 1).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight).normal(normal, -1f, 0f, 0f).endVertex();
                }

                if (z == minPos.getZ()) {
                    //north
                    vertexConsumer.vertex(matrix, 1, height, 0).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();
                    vertexConsumer.vertex(matrix, 1, 0, 0).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
                    vertexConsumer.vertex(matrix, 0, 0, 0).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, -1f).endVertex();
                    vertexConsumer.vertex(matrix, 0, height, 0).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, -1f).endVertex();
                }

                if (x == maxPos.getX()) {
                    //east
                    vertexConsumer.vertex(matrix, 1, height, 1).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 1, 0, 1).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 1, 0, 0).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 1f, 0f, 0f).endVertex();
                    vertexConsumer.vertex(matrix, 1, height, 0).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 1f, 0f, 0f).endVertex();
                }

                if (z == maxPos.getZ()) {
                    //south
                    vertexConsumer.vertex(matrix, 0, height, 1).color(red, green, blue, 255).uv(u0, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();
                    vertexConsumer.vertex(matrix, 0, 0, 1).color(red, green, blue, 255).uv(u0, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
                    vertexConsumer.vertex(matrix, 1, 0, 1).color(red, green, blue, 255).uv(u1, vHeight).uv2(combinedLight)   .normal(normal, 0f, 0f, 1f).endVertex();
                    vertexConsumer.vertex(matrix, 1, height, 1).color(red, green, blue, 255).uv(u1, v0).uv2(combinedLight)     .normal(normal, 0f, 0f, 1f).endVertex();
                }
            }
            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
