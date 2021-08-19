package xyz.przemyk.real_minerals.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;
import java.util.function.Supplier;

// fixes the NullPointerException, should be removed after Minecraft Forge PR#7992 is merged
public class FixedLiquidBlock extends LiquidBlock {
    public FixedLiquidBlock(Supplier<? extends FlowingFluid> p_54694_, Properties p_54695_) {
        super(p_54694_, p_54695_);
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.getFluidState().getType().isSame(getFluid());
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (this.shouldSpreadLiquid(pLevel, pPos)) {
            pLevel.getLiquidTicks().scheduleTick(pPos, pState.getFluidState().getType(), getFluid().getTickDelay(pLevel));
        }

    }
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (this.shouldSpreadLiquid(pLevel, pPos)) {
            pLevel.getLiquidTicks().scheduleTick(pPos, pState.getFluidState().getType(), getFluid().getTickDelay(pLevel));
        }

    }

    private boolean shouldSpreadLiquid(Level pLevel, BlockPos pPos) {
        if (getFluid().is(FluidTags.LAVA)) {
            boolean flag = pLevel.getBlockState(pPos.below()).is(Blocks.SOUL_SOIL);

            for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockpos = pPos.relative(direction.getOpposite());
                if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                    Block block = pLevel.getFluidState(pPos).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
                    pLevel.setBlockAndUpdate(pPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, pPos, pPos, block.defaultBlockState()));
                    this.fizz(pLevel, pPos);
                    return false;
                }

                if (flag && pLevel.getBlockState(blockpos).is(Blocks.BLUE_ICE)) {
                    pLevel.setBlockAndUpdate(pPos, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(pLevel, pPos, pPos, Blocks.BASALT.defaultBlockState()));
                    this.fizz(pLevel, pPos);
                    return false;
                }
            }
        }

        return true;
    }

    private void fizz(LevelAccessor pLevel, BlockPos pPos) {
        pLevel.levelEvent(1501, pPos, 0);
    }

    @Override
    public boolean isPathfindable(BlockState p_54704_, BlockGetter p_54705_, BlockPos p_54706_, PathComputationType p_54707_) {
        return true;
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pContext.isAbove(STABLE_SHAPE, pPos, true) && pState.getValue(LEVEL) == 0 && pContext.canStandOnFluid(pLevel.getFluidState(pPos.above()), getFluid()) ? STABLE_SHAPE : Shapes.empty();
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getFluidState().isSource() || pFacingState.getFluidState().isSource()) {
            pLevel.getLiquidTicks().scheduleTick(pCurrentPos, pState.getFluidState().getType(), getFluid().getTickDelay(pLevel));
        }
        return pState;
    }

    public ItemStack pickupBlock(LevelAccessor pPLevel, BlockPos pPPos, BlockState pPState) {
        if (pPState.getValue(LEVEL) == 0) {
            pPLevel.setBlock(pPPos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(getFluid().getBucket());
        } else {
            return ItemStack.EMPTY;
        }
    }

    public Optional<SoundEvent> getPickupSound() {
        return getFluid().getPickupSound();
    }
}
