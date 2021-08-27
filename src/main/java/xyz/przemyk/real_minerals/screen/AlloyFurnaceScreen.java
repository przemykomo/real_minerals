package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.AlloyFurnaceBlockEntity;
import xyz.przemyk.real_minerals.containers.AlloyFurnaceContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;

public class AlloyFurnaceScreen extends MachineScreen<AlloyFurnaceContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/alloy_furnace.png");

    public AlloyFurnaceScreen(AlloyFurnaceContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(79, 34, this, () -> menu.machineData.get(0), AlloyFurnaceBlockEntity.WORKING_TIME_TOTAL));
    }


    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);

        if (menu.machineData.get(0) > 0) {
            blit(matrixStack, leftPos + 38, topPos + 45 + 12 - 13, 176, 12 - 13, 14, 13 + 1);
        }
    }
}
