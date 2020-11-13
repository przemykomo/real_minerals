package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.MachineItemStackHandler;
import xyz.przemyk.real_minerals.machines.MachineSyncData;
import xyz.przemyk.real_minerals.machines.MachineTileEntity;

public class CrusherTileEntity extends MachineTileEntity {

    @Override
    public int getWorkingTimeTotal() {
        return 100;
    }

    public CrusherTileEntity() {
        super(Registering.CRUSHER_TILE_ENTITY_TYPE.get(), new MachineItemStackHandler(1), RealMinerals.CRUSHER_RECIPE_TYPE);
    }

    @Override
    public INamedContainerProvider getServerContainerProvider(BlockPos activationPos) {
        IContainerProvider containerProvider = (id, playerInventory, serverPlayer) -> new CrusherContainer(id, playerInventory, activationPos, itemHandler, new MachineSyncData(this), serverPlayer);
        return new SimpleNamedContainerProvider(containerProvider, CrusherContainer.TITLE);
    }
}
