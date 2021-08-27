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

public class AlloyFurnaceContainer extends BaseMachineContainer {

    public static AlloyFurnaceContainer getClientContainer(int id, Inventory playerInventory) {
        return new AlloyFurnaceContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(6), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public AlloyFurnaceContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.ALLOY_FURNACE_CONTAINER.get(), windowId, MachinesRegistry.ALLOY_FURNACE_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        addSlot(new SlotItemHandler(itemHandler, 0, 19, 25));
        addSlot(new SlotItemHandler(itemHandler, 1, 38, 25));
        addSlot(new SlotItemHandler(itemHandler, 2, 57, 25));
        addSlot(new SlotItemHandler(itemHandler, 3, 19, 44));
        addSlot(new SlotItemHandler(itemHandler, 4, 57, 44));

        addSlot(new MachineOutputSlot(itemHandler, 5, 116, 35, playerEntity));
    }

    //TODO
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
