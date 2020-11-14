package xyz.przemyk.real_minerals.machines.electric.battery;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

import javax.annotation.Nullable;

public class BatteryBlock extends BaseMachineBlock {

    public BatteryBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BatteryTileEntity();
    }
}
