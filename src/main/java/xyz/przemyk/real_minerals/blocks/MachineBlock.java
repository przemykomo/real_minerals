package xyz.przemyk.real_minerals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MachineBlock extends Block {

    private final Supplier<TileEntity> tileEntitySupplier;

    public MachineBlock(Properties properties, Supplier<TileEntity> tileEntitySupplier) {
        super(properties);
        this.tileEntitySupplier = tileEntitySupplier;
        setDefaultState(this.stateContainer.getBaseState().with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, Boolean.FALSE));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileEntitySupplier.get();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inventory -> {
                    for (int i = 0; i < inventory.getSlots(); ++i) {
                        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                    }
                });
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null) {
                ItemStack itemStack = player.getHeldItem(handIn);
                Direction face = hit.getFace();
                if (tryExtractFluid(worldIn, pos, player, handIn, tileEntity, itemStack, face)) {
                    return ActionResultType.CONSUME;
                }

                if (tileEntity instanceof INamedContainerProvider) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
                }
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.SUCCESS;
    }

    private static boolean tryExtractFluid(World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, TileEntity tileEntity, ItemStack itemStack, Direction face) {
        if (!itemStack.isEmpty()) {
            ItemStack copy = ItemHandlerHelper.copyStackWithSize(itemStack, 1);
            copy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(itemFluidHandler ->
                    tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face).ifPresent(tileFluidHandler -> {
                FluidStack transferred = tryTransfer(itemFluidHandler, tileFluidHandler, Integer.MAX_VALUE);
                if (!transferred.isEmpty()) {
                    worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getEmptySound(transferred), SoundCategory.BLOCKS, 1.0f, 1.0f);
                } else {
                    transferred = tryTransfer(tileFluidHandler, itemFluidHandler, Integer.MAX_VALUE);
                    if (!transferred.isEmpty()) {
                        worldIn.playSound(null, pos, transferred.getFluid().getAttributes().getFillSound(transferred), SoundCategory.BLOCKS, 1.0f, 1.0f);
                    }
                }

                if (!transferred.isEmpty()) {
                    player.setHeldItem(handIn, DrinkHelper.fill(itemStack, player, itemFluidHandler.getContainer()));
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.HORIZONTAL_FACING, BlockStateProperties.LIT);
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.with(HorizontalBlock.HORIZONTAL_FACING, direction.rotate(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HorizontalBlock.HORIZONTAL_FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HorizontalBlock.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
}
