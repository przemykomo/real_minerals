package xyz.przemyk.real_minerals.screen.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.MachineScreen;

import java.util.function.IntSupplier;

public class ProgressArrowModule extends ScreenModule {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/progress_arrow_module.png");

    private final IntSupplier workTimeSupplier;
    private final int workTimeTotal;

    public ProgressArrowModule(int x, int y, MachineScreen<?> screen, IntSupplier workTimeSupplier, int workTimeTotal) {
        super(x, y, screen);
        this.workTimeSupplier = workTimeSupplier;
        this.workTimeTotal = workTimeTotal;
    }

    @Override
    public boolean renderHovering(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        return false;
    }

    @Override
    public void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        int workTime = workTimeSupplier.getAsInt();
        if (workTime > 0) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, GUI);
            int pixels = workTime * 24 / workTimeTotal;
            screen.blit(matrixStack, screen.getGuiLeft() + x, screen.getGuiTop() + y, 0, 0, pixels + 1, 16);
        }
    }
}
