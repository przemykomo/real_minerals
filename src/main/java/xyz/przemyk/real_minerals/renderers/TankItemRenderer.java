package xyz.przemyk.real_minerals.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class TankItemRenderer extends BlockEntityWithoutLevelRenderer {

    public static final TankItemRenderer INSTANCE = new TankItemRenderer();

    public TankItemRenderer() {
        super(null, null);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {}

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidHandler -> {
//            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//            BakedModel bakedModel = itemRenderer.getModel(itemStack, null, null, 0);
//            Transformation transformation = TransformationHelper.toTransformation(bakedModel.getTransforms().getTransform(transformType));
//            if (!transformation.isIdentity()) {
//                transformation.push(poseStack);
//            }

//            itemRenderer.renderModelLists(bakedModel, itemStack, combinedLight, combinedOverlay, poseStack, ItemRenderer.getFoilBufferDirect(bufferSource, ItemBlockRenderTypes.getRenderType(itemStack, true), true, false));
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(MachinesRegistry.TANK_BLOCK.get().defaultBlockState(), poseStack, bufferSource, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
            FluidStack fluidStack = fluidHandler.getFluidInTank(0);
            if (!fluidStack.isEmpty()) {
                TankRenderer.renderTankFluid(poseStack, bufferSource, combinedLight, fluidStack, fluidHandler.getTankCapacity(0));
            }
        });
    }
}
