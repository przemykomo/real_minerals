package xyz.przemyk.real_minerals.machines.alloy_furnace;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import xyz.przemyk.real_minerals.machines.MachineBlock;

public class AlloyFurnaceBlock extends MachineBlock {

    public AlloyFurnaceBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AlloyFurnaceTileEntity();
    }
}
