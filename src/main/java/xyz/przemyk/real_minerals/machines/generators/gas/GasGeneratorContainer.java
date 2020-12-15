package xyz.przemyk.real_minerals.machines.generators.gas;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineContainer;

public class GasGeneratorContainer extends BaseMachineContainer {

    public static GasGeneratorContainer getClientContainer(int id, PlayerInventory playerInventory, PacketBuffer data) {
        BlockPos blockPos = data.readBlockPos();
        System.out.println("Client blockpos: " + blockPos.toString());
        return new GasGeneratorContainer(id, playerInventory, blockPos, new IntArray(4), Minecraft.getInstance().player);
    }

    public static final TranslationTextComponent TITLE = new TranslationTextComponent(RealMinerals.MODID + ".name.gas_generator");

    public final GasGeneratorTileEntity tileEntity;

    public GasGeneratorContainer(int windowId, PlayerInventory playerInventory, BlockPos pos, IIntArray machineData, PlayerEntity playerEntity) {
        super(Registering.GAS_GENERATOR_CONTAINER.get(), windowId, Registering.GAS_GENERATOR_BLOCK.BLOCK.get(), pos, machineData, playerEntity);

        tileEntity = (GasGeneratorTileEntity) playerEntity.world.getTileEntity(pos);

        addPlayerSlots(playerInventory);
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
