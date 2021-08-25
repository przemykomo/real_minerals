package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.blockentity.EvaporationPlantControllerBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.util.MachineOutputSlot;

public class EvaporationPlantContainer extends BaseMachineContainer {

    public final EvaporationPlantControllerBlockEntity blockEntity;

    public static EvaporationPlantContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new EvaporationPlantContainer(id, playerInventory, data.readBlockPos(), new ItemStackHandler(), Minecraft.getInstance().player);
    }

    public EvaporationPlantContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, Player playerEntity) {
        super(MachinesRegistry.EVAPORATION_PLANT_CONTAINER.get(), windowId, MachinesRegistry.EVAPORATION_PLANT_CONTROLLER_BLOCK.BLOCK.get(), pos, new SimpleContainerData(1), playerEntity, playerInventory);
        blockEntity = (EvaporationPlantControllerBlockEntity) playerEntity.level.getBlockEntity(pos);
        addSlot(new MachineOutputSlot(itemHandler, 0, 116, 35, playerEntity));
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}