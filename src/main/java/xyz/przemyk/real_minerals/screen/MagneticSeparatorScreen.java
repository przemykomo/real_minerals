package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xyz.przemyk.real_minerals.blockentity.GasSeparatorBlockEntity;
import xyz.przemyk.real_minerals.containers.MagneticSeparatorContainer;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.blockentity.MagneticSeparatorBlockEntity;
import xyz.przemyk.real_minerals.screen.modules.ProgressArrowModule;

public class MagneticSeparatorScreen extends MachineScreen<MagneticSeparatorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/magnetic_separator.png");

    public MagneticSeparatorScreen(MagneticSeparatorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
        screenModules.add(new ProgressArrowModule(72, 26, this, () -> menu.machineData.get(0), MagneticSeparatorBlockEntity.WORKING_TIME_TOTAL));
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);

        if (menu.machineData.get(0) > 0) {
            blit(matrixStack, leftPos + 46, topPos + 46 + 12 - 13, 176, 12 - 13, 14, 13 + 1);
        }
    }
}
