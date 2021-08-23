package xyz.przemyk.real_minerals.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import xyz.przemyk.real_minerals.blockentity.TankBlockEntity;
import xyz.przemyk.real_minerals.util.FluidUtils;

import javax.annotation.Nullable;

public class TankBlock extends Block implements EntityBlock {

    public TankBlock() {
        super(Properties.copy(Blocks.GLASS));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TankBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemStack itemStack = player.getItemInHand(hand);
                Direction face = hit.getDirection();
                if (FluidUtils.tryExtractFluid(level, pos, player, hand, blockEntity, itemStack, face)) {
                    return InteractionResult.CONSUME;
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
