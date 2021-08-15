package xyz.przemyk.real_minerals.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.GasEnricherBlockEntity;
import xyz.przemyk.real_minerals.containers.GasEnricherContainer;
import xyz.przemyk.real_minerals.screen.modules.EnergyModule;
import xyz.przemyk.real_minerals.screen.modules.TankModule;

public class GasEnricherScreen extends MachineScreen<GasEnricherContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/gas_enricher.png");

    public GasEnricherScreen(GasEnricherContainer screenContainer, Inventory inventory, Component title) {
        super(screenContainer, inventory, title, GUI);
        screenModules.add(new TankModule(55, 7, 18, 72, this, menu.blockEntity.doubleFluidTank.input));
        screenModules.add(new TankModule(109, 7, 18, 72, this, menu.blockEntity.doubleFluidTank.output));
        screenModules.add(new EnergyModule(() -> menu.machineData.get(1), 10_000, 153, 7, this));
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        RenderSystem.setShaderTexture(0, GUI);

        int workTime = menu.machineData.get(0);
        int l = workTime != 0 ? workTime * 24 / GasEnricherBlockEntity.WORKING_TIME_TOTAL : 0;
        this.blit(matrixStack, this.leftPos + 79, this.topPos + 34, 176, 72, l + 1, 16);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        // don't render "Inventory" text
    }
}
