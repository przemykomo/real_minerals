package xyz.przemyk.real_minerals.machines.electric.generator;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

public class BurningGeneratorBlock extends BaseMachineBlock {

    public BurningGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BurningGeneratorTileEntity();
    }
}
