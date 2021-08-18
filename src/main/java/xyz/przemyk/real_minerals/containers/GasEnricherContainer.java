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
import xyz.przemyk.real_minerals.blockentity.ElectricMachineBlockEntity;
import xyz.przemyk.real_minerals.blockentity.GasEnricherBlockEntity;
import xyz.przemyk.real_minerals.blockentity.GasSeparatorBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class GasEnricherContainer extends BaseMachineContainer {

    public final GasEnricherBlockEntity blockEntity;

    public static GasEnricherContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new GasEnricherContainer(id, playerInventory, data.readBlockPos(), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public GasEnricherContainer(int windowId, Inventory playerInventory, BlockPos pos, ContainerData machineData, Player playerEntity) {
        super(MachinesRegistry.GAS_ENRICHER_CONTAINER.get(), windowId, MachinesRegistry.GAS_ENRICHER_BLOCK.BLOCK.get(), pos, machineData, playerEntity, playerInventory);
        blockEntity = (GasEnricherBlockEntity) playerEntity.level.getBlockEntity(pos);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }
}
