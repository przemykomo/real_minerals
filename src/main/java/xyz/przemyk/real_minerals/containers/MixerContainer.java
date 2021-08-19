package xyz.przemyk.real_minerals.containers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.real_minerals.blockentity.MixerBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class MixerContainer extends BaseMachineContainer {

    public final MixerBlockEntity blockEntity;

    public static MixerContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new MixerContainer(id, playerInventory, data.readBlockPos(), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public MixerContainer(int windowId, Inventory playerInventory, BlockPos pos, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.MIXER_CONTAINER.get(), windowId, MachinesRegistry.MIXER_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        blockEntity = (MixerBlockEntity) playerEntity.level.getBlockEntity(pos);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }
}
