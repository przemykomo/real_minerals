package xyz.przemyk.real_minerals.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class FluidUtils {
    public static boolean tryExtractFluid(Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockEntity tileEntity, ItemStack itemStack, Direction face) {
        if (!itemStack.isEmpty()) {
            ItemStack copy = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(itemFluidHandler ->
                    tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face).ifPresent(tileFluidHandler -> {
                FluidStack transferred = tryTransfer(itemFluidHandler, tileFluidHandler, Integer.MAX_VALUE);
                if (!transferred.isEmpty()) {
                    worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getEmptySound(transferred), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    transferred = tryTransfer(tileFluidHandler, itemFluidHandler, Integer.MAX_VALUE);
                    if (!transferred.isEmpty()) {
                        worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getFillSound(transferred), SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                }

                if (!transferred.isEmpty()) {
                    player.setItemInHand(handIn, ItemUtils.createFilledResult(itemStack, player, itemFluidHandler.getContainer()));
                }
            }));
            return copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        }
        return false;
    }

    public static FluidStack tryTransfer(IFluidHandler input, IFluidHandler output, int maxFill) {
        // first, figure out how much we can drain
        FluidStack simulated = input.drain(maxFill, IFluidHandler.FluidAction.SIMULATE);
        if (!simulated.isEmpty()) {
            // next, find out how much we can fill
            int simulatedFill = output.fill(simulated, IFluidHandler.FluidAction.SIMULATE);
            if (simulatedFill > 0) {
                // actually drain
                FluidStack drainedFluid = input.drain(simulatedFill, IFluidHandler.FluidAction.EXECUTE);
                if (!drainedFluid.isEmpty()) {
                    // actually fill
                    output.fill(drainedFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
                }
                return drainedFluid;
            }
        }
        return FluidStack.EMPTY;
    }

    @Nullable
    public static Item getDissolvedItem(FluidStack fluidStack) {
        if (!fluidStack.isEmpty() && fluidStack.hasTag()) {
            CompoundTag tag = fluidStack.getTag();
            if (tag.contains("item", Constants.NBT.TAG_STRING)) {
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item")));
            }
        }
        return null;
    }
}
