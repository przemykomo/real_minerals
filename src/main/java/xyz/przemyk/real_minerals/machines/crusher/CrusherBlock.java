package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.MachineBlock;

public class CrusherBlock extends MachineBlock {

    public CrusherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CrusherTileEntity();
    }
}
