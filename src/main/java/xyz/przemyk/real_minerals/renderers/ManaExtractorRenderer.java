package xyz.przemyk.real_minerals.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.sun.jna.platform.win32.OpenGL32;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.ManaExtractorBlockEntity;
import xyz.przemyk.real_minerals.util.Utils;

public class ManaExtractorRenderer implements BlockEntityRenderer<ManaExtractorBlockEntity> {

    @SuppressWarnings("unused")
    public ManaExtractorRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(ManaExtractorBlockEntity manaExtractorBlockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        double stage = (double) manaExtractorBlockEntity.workingTime  / ManaExtractorBlockEntity.WORKING_TIME_TOTAL;
        ModelManager modelManager = Minecraft.getInstance().getModelManager();
        BakedModel bakedModel = modelManager.getModel(new ResourceLocation(RealMinerals.MODID,"block/mana_extractor_piston"));
        BlockRenderDispatcher blockRenderDispatcher = Minecraft.getInstance().getBlockRenderer();
        poseStack.pushPose();
        poseStack.translate(0, 0.5-Math.sin(stage*Math.PI*(0.746255*stage*stage+stage*0.253745))*0.3125,0);
        blockRenderDispatcher.getModelRenderer().renderModel(poseStack.last(),bufferSource.getBuffer(Sheets.solidBlockSheet()),null, bakedModel,1.0F,1.0F,1.0F, combinedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
