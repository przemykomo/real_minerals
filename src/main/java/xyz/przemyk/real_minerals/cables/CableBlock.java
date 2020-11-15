package xyz.przemyk.real_minerals.cables;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CableBlock extends Block {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public CableBlock(Properties properties) {
        super(properties);
        setDefaultState(getDefaultState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CableTileEntity();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getState(getDefaultState(), context.getWorld(), context.getPos());
    }

    @SuppressWarnings("deprecation")
    @Override //TODO: consider only the specific face passed in?
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return getState(stateIn, worldIn, currentPos);
    }

    private BlockState getState(BlockState currentState, IWorld world, BlockPos pos) {
        return currentState
                .with(NORTH, hasConnection(world, pos, Direction.NORTH))
                .with(EAST, hasConnection(world, pos, Direction.EAST))
                .with(SOUTH, hasConnection(world, pos, Direction.SOUTH))
                .with(WEST, hasConnection(world, pos, Direction.WEST))
                .with(UP, hasConnection(world, pos, Direction.UP))
                .with(DOWN, hasConnection(world, pos, Direction.DOWN));
    }

    protected boolean hasConnection(IWorld world, BlockPos pos, Direction direction) {
        return world.getTileEntity(pos.offset(direction)) instanceof CableTileEntity;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof CableTileEntity) {
                CableTileEntity cableTileEntity = (CableTileEntity) tileEntity;
                ArrayList<CableTileEntity> connectedCables = new ArrayList<>();

                for (Direction direction : Direction.values()) {
                    if (state.get(getPropertyFromDirection(direction))) {
                        TileEntity other = worldIn.getTileEntity(pos.offset(direction));
                        if (other instanceof CableTileEntity) {
                            connectedCables.add((CableTileEntity) other);
                        }
                    }
                }

                CableNetworksWorldData cableNetworksWorldData = CableNetworksWorldData.get((ServerWorld) worldIn);
                if (connectedCables.size() > 0) {
                    Set<String> networkIDs = new HashSet<>();
                    connectedCables.forEach(connectedCable -> networkIDs.add(connectedCable.networkID));
                    if (networkIDs.size() == 1) {
                        @SuppressWarnings("OptionalGetWithoutIsPresent")
                        CableNetwork cableNetwork = cableNetworksWorldData.getNetworks().get(networkIDs.stream().findFirst().get());
                        cableNetwork.addCable(cableTileEntity);
                        cableTileEntity.networkID = cableNetwork.getID();
                    } else {
                        // merge smaller networks to largest one
                        ArrayList<CableNetwork> connectedNetworks = new ArrayList<>();
                        for (String networkID : networkIDs) {
                            connectedNetworks.add(cableNetworksWorldData.getNetworks().get(networkID));
                        }

                        CableNetwork largestNetwork = connectedNetworks.get(0);
                        for (CableNetwork cableNetwork : connectedNetworks) {
                            if (cableNetwork.getSize() > largestNetwork.getSize()) {
                                largestNetwork = cableNetwork;
                            }
                        }

                        for (CableNetwork cableNetwork : connectedNetworks) {
                            cableNetwork.mergeInto(largestNetwork, (ServerWorld) worldIn);
                        }

                        largestNetwork.addCable(cableTileEntity);
                        cableTileEntity.networkID = largestNetwork.getID();
                    }
                } else {
                    CableNetwork cableNetwork = cableNetworksWorldData.createNetwork();
                    cableNetwork.addCable(cableTileEntity);
                    cableTileEntity.networkID = cableNetwork.getID();
                }
            }
        }
    }

    private static BooleanProperty getPropertyFromDirection(Direction direction) {
        switch (direction){
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case SOUTH:
                return SOUTH;
            default:
                return NORTH;
        }
    }
}
