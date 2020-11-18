package xyz.przemyk.real_minerals.machines.electric.magnetic_separator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineContainer;
import xyz.przemyk.real_minerals.machines.not_electric.MachineOutputSlot;

public class MagneticSeparatorContainer extends BaseMachineContainer {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.magnetic_separator");

    public static MagneticSeparatorContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new MagneticSeparatorContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(3), new IntArray(2), Minecraft.getInstance().player);
    }

    public MagneticSeparatorContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IItemHandler itemHandler, IIntArray machineData, PlayerEntity playerEntity) {
        super(Registering.MAGNETIC_SEPARATOR_CONTAINER.get(), windowId, Registering.MAGNETIC_SEPARATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new SlotItemHandler(itemHandler, 0, 45, 27));
        addSlot(new MachineOutputSlot(itemHandler, 1, 111, 21, playerEntity));
        addSlot(new MachineOutputSlot(itemHandler, 2, 111, 49, playerEntity));

        addPlayerSlots(playerInventory);
    }

    @Override //TODO
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (!mergeItemStack(stack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                } else if (index < 28) {
                    if (!mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
}
