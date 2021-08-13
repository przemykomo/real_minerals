package xyz.przemyk.real_minerals.screen.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.screen.MachineScreen;

import java.util.function.IntSupplier;

public class EnergyModule extends ScreenModule {

    private static final ResourceLocation GUI = new ResourceLocation(RealMinerals.MODID, "textures/gui/energy_module.png");

    private final IntSupplier energySupplier;
    private final int capacity;

    public EnergyModule(IntSupplier energySupplier, int capacity, int x, int y, MachineScreen<?> screen) {
        super(x, y, screen);
        this.energySupplier = energySupplier;
        this.capacity = capacity;
    }

    @Override
    public boolean renderHovering(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (screen.isHovering(x, y, 16, 72, mouseX, mouseY)) {
            screen.renderTooltip(matrixStack, new TranslatableComponent(RealMinerals.MODID + ".gui.energy", energySupplier.getAsInt()), mouseX, mouseY);
            return true;
        }
        return false;
    }

    @Override
    public void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        int energy = energySupplier.getAsInt();
        if (energy > 0) {
            RenderSystem.setShaderTexture(0, GUI);
            int energyScaled = energy * 72 / capacity;
            screen.blit(matrixStack, screen.getGuiLeft() + x, screen.getGuiTop() + y + 72 - energyScaled, 0, 72 - energyScaled, 16, energyScaled + 1);
        }
    }
}
