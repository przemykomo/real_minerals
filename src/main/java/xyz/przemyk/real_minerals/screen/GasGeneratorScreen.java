package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.containers.GasGeneratorContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.ScreenModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

import java.util.ArrayList;
import java.util.List;

public class GasGeneratorScreen extends MachineScreen<GasGeneratorContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_generator.png");

    public GasGeneratorScreen(GasGeneratorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);
        screenModules.add(new TankModule(7, 7, 18, 72, this, menu.tileEntity.fluidTank));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(2), 10_000, 153, 7, this));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);

        if (menu.machineData.get(0) > 0) {
            int burnLeftScaled = getBurnLeftScaled();
            blit(matrixStack, leftPos + 80, topPos + 75 - burnLeftScaled, 176, 12 - burnLeftScaled, 14, burnLeftScaled + 1);
        }
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
