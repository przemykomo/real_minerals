package xyz.przemyk.real_minerals.machines.electric.gas_separator;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

import javax.annotation.Nullable;

public class GasSeparatorBlock extends BaseMachineBlock {

    public GasSeparatorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GasSeparatorTileEntity();
    }
}
