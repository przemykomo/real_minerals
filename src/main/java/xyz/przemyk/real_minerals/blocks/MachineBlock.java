package xyz.przemyk.real_minerals.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.przemyk.real_minerals.tileentity.TickableBlockEntity;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class MachineBlock extends Block implements EntityBlock {

    private final BiFunction<BlockPos, BlockState, BlockEntity> tileEntitySupplier;

    public MachineBlock(Properties properties, BiFunction<BlockPos, BlockState, BlockEntity> tileEntitySupplier) {
        super(properties);
        this.tileEntitySupplier = tileEntitySupplier;
        registerDefaultState(this.stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH).setValue(BlockStateProperties.LIT, Boolean.FALSE));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return tileEntitySupplier.apply(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (level1, blockPos, blockState1, blockEntity) -> ((TickableBlockEntity) blockEntity).tick();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inventory -> {
                    for (int i = 0; i < inventory.getSlots(); ++i) {
                        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                    }
                });
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity != null) {
                ItemStack itemStack = player.getItemInHand(handIn);
                Direction face = hit.getDirection();
                if (tryExtractFluid(worldIn, pos, player, handIn, tileEntity, itemStack, face)) {
                    return InteractionResult.CONSUME;
                }

                if (tileEntity instanceof MenuProvider) {
                    NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, pos);
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    private static boolean tryExtractFluid(Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockEntity tileEntity, ItemStack itemStack, Direction face) {
        if (!itemStack.isEmpty()) {
            ItemStack copy = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(itemFluidHandler ->
                    tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face).ifPresent(tileFluidHandler -> {
                FluidStack transferred = tryTransfer(itemFluidHandler, tileFluidHandler, Integer.MAX_VALUE);
                if (!transferred.isEmpty()) {
                    worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getEmptySound(transferred), SoundSource.BLOCKS, 1.0f, 1.0f);
                } else {
                    transferred = tryTransfer(tileFluidHandler, itemFluidHandler, Integer.MAX_VALUE);
                    if (!transferred.isEmpty()) {
                        worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getFillSound(transferred), SoundSource.BLOCKS, 1.0f, 1.0f);
                    }
                }

                if (!transferred.isEmpty()) {
                    player.setItemInHand(handIn, ItemUtils.createFilledResult(itemStack, player, itemFluidHandler.getContainer()));
                }
            }));
            return copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        }
        return false;
    }

    public static FluidStack tryTransfer(IFluidHandler input, IFluidHandler output, int maxFill) {
        // first, figure out how much we can drain
        FluidStack simulated = input.drain(maxFill, IFluidHandler.FluidAction.SIMULATE);
        if (!simulated.isEmpty()) {
            // next, find out how much we can fill
            int simulatedFill = output.fill(simulated, IFluidHandler.FluidAction.SIMULATE);
            if (simulatedFill > 0) {
                // actually drain
                FluidStack drainedFluid = input.drain(simulatedFill, IFluidHandler.FluidAction.EXECUTE);
                if (!drainedFluid.isEmpty()) {
                    // actually fill
                    output.fill(drainedFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
                }
                return drainedFluid;
            }
        }
        return FluidStack.EMPTY;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING, BlockStateProperties.LIT);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation direction) {
        return state.setValue(HorizontalDirectionalBlock.FACING, direction.rotate(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite());
    }
}
