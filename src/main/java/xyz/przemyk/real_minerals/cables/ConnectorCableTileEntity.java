package xyz.przemyk.real_minerals.cables;

import net.minecraft.tileentity.ITickableTileEntity;
import xyz.przemyk.real_minerals.init.Registering;

public class ConnectorCableTileEntity extends CableTileEntity implements ITickableTileEntity {

    public ConnectorCableTileEntity() {
        super(Registering.CONNECTOR_CABLE_TILE_ENTITY_TYPE.get());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        if (!world.isRemote()) {
            getCachedEnergyStorage().ifPresent(energyStorage -> {
                energyStorage.trySendToNeighbors(world, pos);
            });
        }
    }
}
