package xyz.przemyk.real_minerals.machines.electric.magnetic_separator;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

import javax.annotation.Nullable;

public class MagneticSeparatorBlock extends BaseMachineBlock {

    public MagneticSeparatorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MagneticSeparatorTileEntity();
    }
}
