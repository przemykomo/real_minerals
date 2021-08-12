package xyz.przemyk.real_minerals.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.lwjgl.opengl.GL11;
import xyz.przemyk.real_minerals.RealMinerals;

public class FluidDisplay {

    public static final ResourceLocation TANK_TEXTURE = new ResourceLocation(RealMinerals.MODID, "textures/gui/tank.png");

    private final FluidTank fluidTank;
    private Fluid oldFluid;

    private final int x;
    private final int y;

    private ResourceLocation resourceLocation;

    public FluidDisplay(FluidTank fluidTank, int x, int y) {
        this.fluidTank = fluidTank;
        this.x = x;
        this.y = y;
    }


    public void draw(MatrixStack matrixStack) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TANK_TEXTURE);
        GuiUtils.drawTexturedModalRect(matrixStack, x, y, 0, 0, 18, 72, 0);

        FluidStack fluidStack = fluidTank.getFluid();
        Fluid fluid = fluidStack.getFluid();

        if (resourceLocation == null || oldFluid != fluid) {
            oldFluid = fluid;

            if (fluid != Fluids.EMPTY) {
                ResourceLocation registryName = fluid.getRegistryName();
                if (registryName != null) {
//                    resourceLocation = new ResourceLocation(registryName.getNamespace(), "textures/" + registryName.getPath() + ".png");
                    //noinspection ConstantConditions
                    resourceLocation = new ResourceLocation("textures/" + Fluids.WATER.getRegistryName().getPath() + ".png");
                }
            }
        }

        if (!fluidStack.isEmpty() && resourceLocation != null) {
            minecraft.getTextureManager().bindTexture(resourceLocation);

            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            int k = fluidStack.getAmount() * 71 / fluidTank.getCapacity();
            AbstractGui.blit(matrixStack, x + 2, y + 2 - k, 0, -k, 14, 68 - k, 16, 16);
            RenderSystem.disableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.popMatrix();
        }
    }
}
