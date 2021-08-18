package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.util.MachineOutputSlot;

public class MagnetizerContainer extends BaseMachineContainer {

    public static final TranslatableComponent TITLE = new TranslatableComponent(RealMinerals.MODID + ".name.magnetizer");

    public static MagnetizerContainer getClientContainer(int id, Inventory playerInventory) {
        return new MagnetizerContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public MagnetizerContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.MAGNETIZER_CONTAINER.get(), windowId, MachinesRegistry.MAGNETIZER_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        addSlot(new SlotItemHandler(itemHandler, 0, 56, 35));
        addSlot(new MachineOutputSlot(itemHandler, 1, 116, 35, playerEntity));
    }

    @Override //TODO: does it work the way it should?
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (!moveItemStackTo(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (index < 28) {
                    if (!moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
