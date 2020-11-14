package xyz.przemyk.real_minerals.machines.electric.generator;

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
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineContainer;
import xyz.przemyk.real_minerals.machines.not_electric.MachineFuelSlot;

public class BurningGeneratorContainer extends BaseMachineContainer {

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.burning_generator");

    public static BurningGeneratorContainer getClientContainer(int id, PlayerInventory playerInventory) {
        return new BurningGeneratorContainer(id, playerInventory, BlockPos.ZERO, new ItemStackHandler(), new IntArray(3), Minecraft.getInstance().player);
    }

    public BurningGeneratorContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IItemHandler itemHandler, IIntArray machineData, PlayerEntity playerEntity) {
        super(Registering.BURNING_GENERATOR_CONTAINER.get(), windowId, Registering.BURNING_GENERATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        addSlot(new MachineFuelSlot(itemHandler, 0, 80, 62));

        addPlayerSlots(playerInventory);
    }

    @Override //TODO
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
