package xyz.przemyk.real_minerals.compat.jei;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.util.FluidUtils;

import java.util.List;

public class FluidTooltipCallback {

    @SuppressWarnings("unused")
    public static void onTooltip(int slotIndex, boolean input, FluidStack fluidStack, List<Component> tooltip) {
        Component modId = tooltip.get(tooltip.size() - 1);
        tooltip.clear();
        tooltip.add(new TranslatableComponent(RealMinerals.MODID + ".gui.fluid", fluidStack.getDisplayName(), fluidStack.getAmount()));
        Item item = FluidUtils.getDissolvedItem(fluidStack);
        if (item != null) {
            tooltip.add(new TranslatableComponent(RealMinerals.MODID + ".gui.fluid.dissolved", item.getDescription()));
        }
        tooltip.add(modId);
    }
}
