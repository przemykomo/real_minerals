package xyz.przemyk.real_minerals.machines.alloy_furnace;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.machines.MachineFuelSlot;
import xyz.przemyk.real_minerals.machines.MachineOutputSlot;

public class AlloyFurnaceContainer extends Container {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.alloy_furnace");

    private final IWorldPosCallable usabilityTest;
    public final IIntArray machineData;

    @SuppressWarnings("ConstantConditions")
    public static AlloyFurnaceContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new AlloyFurnaceContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(7), new IntArray(4), Minecraft.getInstance().player);
    }

    public AlloyFurnaceContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IItemHandler itemHandler, IIntArray machineData, PlayerEntity playerEntity) {
        super(RealMinerals.ALLOY_FURNACE_CONTAINER.get(), windowId);
        usabilityTest = IWorldPosCallable.of(playerEntity.world, pos);
        this.machineData = machineData;

        addSlot(new SlotItemHandler(itemHandler, 0, 19, 16));
        addSlot(new SlotItemHandler(itemHandler, 1, 38, 16));
        addSlot(new SlotItemHandler(itemHandler, 2, 57, 16));
        addSlot(new SlotItemHandler(itemHandler, 3, 19, 35));
        addSlot(new SlotItemHandler(itemHandler, 4, 57, 35));

        addSlot(new MachineFuelSlot(itemHandler, 5, 38, 53));
        addSlot(new MachineOutputSlot(itemHandler, 6, 116, 35, playerEntity));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        trackIntArray(machineData);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(usabilityTest, playerIn, RealMinerals.ALLOY_FURNACE_BLOCK.BLOCK.get());
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
//        return AlloyFurnaceTileEntity.getRecipe(stack, world) != null;
        return true;
    }
}
