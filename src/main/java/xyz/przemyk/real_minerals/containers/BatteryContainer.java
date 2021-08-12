package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;

public class BatteryContainer extends BaseMachineContainer {

    public static final TranslatableComponent TITLE = new TranslatableComponent(RealMinerals.MODID + ".name.battery");

    public static BatteryContainer getClientContainer(int id, Inventory playerInventory) {
        return new BatteryContainer(id, playerInventory, BlockPos.ZERO, new SimpleContainerData(1), Minecraft.getInstance().player);
    }

    public BatteryContainer(int id, Inventory playerInventory, BlockPos pos, ContainerData batterySyncData, Player serverPlayer) {
        super(Registering.BATTERY_CONTAINER.get(), id, Registering.BATTERY_BLOCK.BLOCK.get(), pos, batterySyncData, serverPlayer);

        addPlayerSlots(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
