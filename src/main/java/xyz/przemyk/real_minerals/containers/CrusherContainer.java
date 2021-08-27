package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.util.MachineOutputSlot;

public class CrusherContainer extends BaseMachineContainer {

    public static CrusherContainer getClientContainer(int id, Inventory playerInventory) {
        return new CrusherContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public CrusherContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.CRUSHER_CONTAINER.get(), windowId, MachinesRegistry.CRUSHER_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        addSlot(new SlotItemHandler(itemHandler, 0, 56, 35));
        addSlot(new MachineOutputSlot(itemHandler, 1, 116, 35, playerEntity));
    }

    //TODO
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
