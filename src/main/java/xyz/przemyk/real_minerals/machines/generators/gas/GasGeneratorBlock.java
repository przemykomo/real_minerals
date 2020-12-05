package xyz.przemyk.real_minerals.machines.generators.gas;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.FluidMachineBlock;

import javax.annotation.Nullable;

public class GasGeneratorBlock extends FluidMachineBlock {

    public GasGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new GasGeneratorTileEntity();
    }
}
