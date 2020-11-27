package xyz.przemyk.real_minerals.machines.electric.gas_separator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineContainer;
import xyz.przemyk.real_minerals.machines.not_electric.MachineOutputSlot;

public class GasSeparatorContainer extends BaseMachineContainer {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.gas_separator");

    public static GasSeparatorContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new GasSeparatorContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(2), new IntArray(3), Minecraft.getInstance().player);
    }

    public GasSeparatorContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IItemHandler itemHandler, IIntArray machineData, PlayerEntity playerEntity) {
        super(Registering.GAS_SEPARATOR_CONTAINER.get(), windowId, Registering.GAS_SEPARATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new SlotItemHandler(itemHandler, 0, 45, 27));
        addSlot(new MachineOutputSlot(itemHandler, 1, 111, 49, playerEntity));

        addPlayerSlots(playerInventory);
    }

    @Override //TODO:
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
