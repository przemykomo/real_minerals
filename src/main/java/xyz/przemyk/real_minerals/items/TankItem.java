package xyz.przemyk.real_minerals.items;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.blockentity.TankBlockEntity;
import xyz.przemyk.real_minerals.renderers.TankItemRenderer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class TankItem extends BlockItem {

    public TankItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerTankItem(stack, TankBlockEntity.CAPACITY);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(fluidTank -> (double)fluidTank.getFluidInTank(0).getAmount() / fluidTank.getTankCapacity(0)).orElse(0.0);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidTank -> {
            FluidStack fluidStack = fluidTank.getFluidInTank(0);
            if (fluidStack.isEmpty()) {
                components.add(new TranslatableComponent(RealMinerals.MODID + ".empty"));
            } else {
                components.add(new TranslatableComponent(RealMinerals.MODID + ".gui.fluid", fluidStack.getDisplayName(), fluidStack.getAmount()));
                if (fluidStack.hasTag()) {
                    CompoundTag tag = fluidStack.getTag();
                    if (tag.contains("item", Constants.NBT.TAG_STRING)) {
                        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item")));
                        if (item != null) {
                            components.add(new TranslatableComponent(RealMinerals.MODID + ".gui.fluid.dissolved", item.getDescription()));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return TankItemRenderer.INSTANCE;
            }
        });
    }
}
