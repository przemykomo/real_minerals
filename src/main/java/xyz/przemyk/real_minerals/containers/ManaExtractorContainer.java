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
import xyz.przemyk.real_minerals.blockentity.ManaExtractorBlockEntity;
import xyz.przemyk.real_minerals.init.MachinesRegistry;

public class ManaExtractorContainer extends BaseMachineContainer {

    public final ManaExtractorBlockEntity blockEntity;

    public static ManaExtractorContainer getClientContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        return new ManaExtractorContainer(id, playerInventory, data.readBlockPos(), new ItemStackHandler(1), new SimpleContainerData(2), Minecraft.getInstance().player);
    }

    public ManaExtractorContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData containerData, Player playerEntity) {
        super(MachinesRegistry.MANA_EXTRACTOR_CONTAINER.get(), windowId, MachinesRegistry.MANA_EXTRACTOR_BLOCK.BLOCK.get(), pos, containerData, playerEntity, playerInventory);
        blockEntity = (ManaExtractorBlockEntity) playerEntity.level.getBlockEntity(pos);

        addSlot(new SlotItemHandler(itemHandler, 0, 56, 35));
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }
}
