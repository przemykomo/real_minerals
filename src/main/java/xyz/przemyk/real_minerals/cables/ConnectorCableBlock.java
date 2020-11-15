package xyz.przemyk.real_minerals.cables;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.energy.CapabilityEnergy;

public class ConnectorCableBlock extends CableBlock {

    public ConnectorCableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean hasConnection(IWorld world, BlockPos pos, Direction direction) {
        if (super.hasConnection(world, pos, direction)) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(pos.offset(direction));
        if (tileEntity == null) {
            return false;
        }
        return tileEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent();
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ConnectorCableTileEntity();
    }
}

