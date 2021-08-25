package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.real_minerals.blockentity.GasGeneratorBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class GasGeneratorContainer extends BaseMachineContainer {

    public final GasGeneratorBlockEntity blockEntity;

    public static GasGeneratorContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new GasGeneratorContainer(id, playerInventory, data.readBlockPos(), new SimpleContainerData(4), Minecraft.getInstance().player);
    }

    public GasGeneratorContainer(int windowId, Inventory playerInventory, BlockPos pos, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.GAS_GENERATOR_CONTAINER.get(), windowId, MachinesRegistry.GAS_GENERATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        blockEntity = (GasGeneratorBlockEntity) playerEntity.level.getBlockEntity(pos);
    }


    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
