package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.MachineFuelSlot;
import xyz.przemyk.real_minerals.MachineOutputSlot;

public class CrusherContainer extends BaseMachineContainer {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.crusher");

    public static CrusherContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new CrusherContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(3), new IntArray(4), Minecraft.getInstance().player);
    }

    public CrusherContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IItemHandler itemHandler, IIntArray machineData, PlayerEntity playerEntity) {
        super(Registering.CRUSHER_CONTAINER.get(), windowId, Registering.CRUSHER_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new SlotItemHandler(itemHandler, 0, 56, 17));
        addSlot(new MachineFuelSlot(itemHandler, 1, 56, 53));
        addSlot(new MachineOutputSlot(itemHandler, 2, 116, 35, playerEntity));

        addPlayerSlots(playerInventory);
    }

    //TODO
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index > 1) {
                if (this.hasRecipe(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ForgeHooks.getBurnTime(itemstack1) > 0) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    protected boolean hasRecipe(@SuppressWarnings("unused") ItemStack stack) { //TODO
//        return CrusherTileEntity.getRecipe(stack, world) != null;
        return true;
    }
}
