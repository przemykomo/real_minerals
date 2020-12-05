package xyz.przemyk.real_minerals.machines.generators.solid;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

public class SolidGeneratorBlock extends BaseMachineBlock {

    public SolidGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SolidGeneratorTileEntity();
    }
}
