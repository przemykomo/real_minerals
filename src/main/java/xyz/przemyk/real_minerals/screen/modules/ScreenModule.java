package xyz.przemyk.real_minerals.screen.modules;

import com.mojang.blaze3d.vertex.PoseStack;
import xyz.przemyk.real_minerals.screen.MachineScreen;

public abstract class ScreenModule {

    public final int x, y;
    public final MachineScreen<?> screen;

    public ScreenModule(int x, int y, MachineScreen<?> screen) {
        this.x = x;
        this.y = y;
        this.screen = screen;
    }

    public abstract boolean renderHovering(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks);
    public abstract void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY);
}
