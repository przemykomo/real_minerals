package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;

public class BatteryContainer extends BaseMachineContainer {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.battery");

    public static BatteryContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new BatteryContainer(id, playerInventory, BlockPos.ZERO, new IntArray(1), Minecraft.getInstance().player);
    }

    public BatteryContainer(int id, PlayerInventory playerInventory, BlockPos pos, IIntArray batterySyncData, PlayerEntity serverPlayer) {
        super(Registering.BATTERY_CONTAINER.get(), id, Registering.BATTERY_BLOCK.BLOCK.get(), pos, batterySyncData, serverPlayer);

        addPlayerSlots(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
