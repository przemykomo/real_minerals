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
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.util.MachineFuelSlot;

public class SolidGeneratorContainer extends BaseMachineContainer {

    public static final TranslatableComponent TITLE = new TranslatableComponent(RealMinerals.MODID + ".name.burning_generator");

    public static SolidGeneratorContainer getClientContainer(int id, Inventory playerInventory) {
        return new SolidGeneratorContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(), new SimpleContainerData(3), Minecraft.getInstance().player);
    }

    public SolidGeneratorContainer(int windowId, Inventory playerInventory, BlockPos pos, IItemHandler itemHandler, ContainerData machineData, Player playerEntity) {
        super(Registering.BURNING_GENERATOR_CONTAINER.get(), windowId, Registering.BURNING_GENERATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new MachineFuelSlot(itemHandler, 0, 80, 62));

        addPlayerSlots(playerInventory);
    }

    @Override //TODO
    public ItemStack quickMoveStack(Player playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
