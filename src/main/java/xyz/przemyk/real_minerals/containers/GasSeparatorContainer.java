package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
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

public class GasSeparatorContainer extends BaseMachineContainer {

    public static final TranslatableComponent TITLE = new TranslatableComponent(RealMinerals.MODID + ".name.gas_separator");

    public static GasSeparatorContainer getClientContainer(int id, Inventory playerInventory) {
        return new GasSeparatorContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2), new SimpleContainerData(3), Minecraft.getInstance().player);
    }

    public GasSeparatorContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.GAS_SEPARATOR_CONTAINER.get(), windowId, MachinesRegistry.GAS_SEPARATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new SlotItemHandler(itemHandler, 0, 45, 27));
        addSlot(new MachineOutputSlot(itemHandler, 1, 111, 49, playerEntity));

        addPlayerSlots(playerInventory);
    }

    @Override //TODO:
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
