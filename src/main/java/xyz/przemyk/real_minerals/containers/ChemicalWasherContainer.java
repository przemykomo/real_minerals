package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.blockentity.ChemicalWasherBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class ChemicalWasherContainer extends BaseMachineContainer {

    public final ChemicalWasherBlockEntity blockEntity;

    public static ChemicalWasherContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new ChemicalWasherContainer(id, playerInventory, data.readBlockPos(), new ItemStackHandler(1), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public ChemicalWasherContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.CHEMICAL_WASHER_CONTAINER.get(), windowId, MachinesRegistry.CHEMICAL_WASHER_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        blockEntity = (ChemicalWasherBlockEntity) playerEntity.level.getBlockEntity(pos);

        addSlot(new SlotItemHandler(itemHandler, 0, 56, 35));
    }

    @Override //TODO:
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
