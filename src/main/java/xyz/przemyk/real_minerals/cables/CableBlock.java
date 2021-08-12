package xyz.przemyk.real_minerals.cables;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;
import java.util.*;

public class CableBlock extends Block implements EntityBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public static final VoxelShape CENTER_SHAPE = box(5, 5, 5, 11, 11, 11);
    private static final Map<Direction, VoxelShape> SIDE_TO_SHAPE = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(5, 5, 0, 11, 11, 5), Direction.SOUTH, Block.box(5, 5, 11, 11, 11, 16), Direction.EAST, Block.box(11, 5, 5, 16, 11, 11), Direction.WEST, Block.box(0, 5, 5, 5, 11, 11), Direction.UP, Block.box(5, 11, 5, 11, 16, 11)));

    private final Map<BlockState, VoxelShape> stateToShapeMap = new HashMap<>();

    public CableBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
        );

        SIDE_TO_SHAPE.put(Direction.DOWN, Block.box(5, 0, 5, 11, 5, 11)); // looks like ImmutableMap.of() doesn't support so many arguments

        for (BlockState blockState : getStateDefinition().getPossibleStates()) {
            stateToShapeMap.put(blockState, getShapeForState(blockState));
        }
    }

    private VoxelShape getShapeForState(BlockState blockState) {
        VoxelShape voxelShape = CENTER_SHAPE;

        for (Direction direction : Direction.values()) {
            boolean connected = blockState.getValue(getPropertyFromDirection(direction));
            if (connected) {
                voxelShape = Shapes.or(voxelShape, SIDE_TO_SHAPE.get(direction));
            }
        }

        return voxelShape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
        super.createBlockStateDefinition(builder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return stateToShapeMap.get(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        boolean isConnectedToTileEntity = false;
        BooleanProperty property = getPropertyFromDirection(facing);
        Connection connection = hasConnection(world, currentPos, facing);
        switch (connection) {
            case NONE -> stateIn = stateIn.setValue(property, false);
            case CABLE -> stateIn = stateIn.setValue(property, true);
            case MACHINE -> {
                stateIn = stateIn.setValue(property, true);
                isConnectedToTileEntity = true;
            }
            case TILE_ENTITY -> {
                stateIn = stateIn.setValue(property, false);
                isConnectedToTileEntity = true;
            }
        }

        if (!world.isClientSide()) {
            BlockEntity tileEntity = world.getBlockEntity(currentPos);
            if (tileEntity instanceof CableTileEntity cableTileEntity) {
                CableNetwork cableNetwork = CableNetworksSavedData.get((ServerLevel) world).getNetworks().get(cableTileEntity.getNetworkID());
                if (isConnectedToTileEntity) {
                    cableNetwork.addConnectorCable(cableTileEntity);
                } else if (connection != Connection.CABLE) {
                    cableNetwork.removeConnector(cableTileEntity);
                }
            }
        }
        return stateIn;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState currentState = defaultBlockState();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        boolean isConnectedToTileEntity = false;
        for (Direction direction : Direction.values()) {
            BooleanProperty property = getPropertyFromDirection(direction);
            Connection connection = hasConnection(world, pos, direction);
            switch (connection) {
                case NONE -> currentState = currentState.setValue(property, false);
                case CABLE -> currentState = currentState.setValue(property, true);
                case MACHINE -> {
                    currentState = currentState.setValue(property, true);
                    isConnectedToTileEntity = true;
                }
                case TILE_ENTITY -> {
                    currentState = currentState.setValue(property, false);
                    isConnectedToTileEntity = true;
                }
            }
        }

        if (!world.isClientSide()) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof CableTileEntity cableTileEntity) {
                CableNetwork cableNetwork = CableNetworksSavedData.get((ServerLevel) world).getNetworks().get(cableTileEntity.getNetworkID());
                if (isConnectedToTileEntity) {
                    cableNetwork.addConnectorCable(cableTileEntity);
                } else {
                    cableNetwork.removeConnector(cableTileEntity);
                }
            }
        }
        return currentState;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public enum Connection {
        NONE,
        CABLE,
        MACHINE,
        TILE_ENTITY
    }

    protected Connection hasConnection(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockEntity tileEntity = world.getBlockEntity(pos.relative(direction));
        if (tileEntity == null) {
            return Connection.NONE;
        }

        if (tileEntity instanceof CableTileEntity) {
            return Connection.CABLE;
        }

        if (tileEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent()) {
            return Connection.MACHINE;
        }

        return Connection.TILE_ENTITY;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof CableTileEntity cableTileEntity) {
                ArrayList<CableTileEntity> connectedCables = new ArrayList<>();

                for (Direction direction : Direction.values()) {
                    BlockEntity other = worldIn.getBlockEntity(pos.relative(direction));
                    if (other instanceof CableTileEntity) {
                        connectedCables.add((CableTileEntity) other);
                    }
                }

                CableNetworksSavedData cableNetworksSavedData = CableNetworksSavedData.get((ServerLevel) worldIn);
                if (connectedCables.size() > 0) {
                    Set<String> networkIDs = new HashSet<>();
                    connectedCables.forEach(connectedCable -> networkIDs.add(connectedCable.getNetworkID()));
                    if (networkIDs.size() == 1) {
                        @SuppressWarnings("OptionalGetWithoutIsPresent")
                        CableNetwork cableNetwork = cableNetworksSavedData.getNetworks().get(networkIDs.stream().findFirst().get());
                        cableNetwork.addCable(cableTileEntity);
                        cableTileEntity.setNetworkID(cableNetwork.getID());
                    } else {
                        // merge smaller networks to largest one
                        ArrayList<CableNetwork> connectedNetworks = new ArrayList<>();
                        for (String networkID : networkIDs) {
                            connectedNetworks.add(cableNetworksSavedData.getNetworks().get(networkID));
                        }

                        CableNetwork largestNetwork = connectedNetworks.get(0);
                        for (CableNetwork cableNetwork : connectedNetworks) {
                            if (cableNetwork.getSize() > largestNetwork.getSize()) {
                                largestNetwork = cableNetwork;
                            }
                        }

                        for (CableNetwork cableNetwork : connectedNetworks) {
                            cableNetwork.mergeInto(largestNetwork, (ServerLevel) worldIn);
                        }

                        largestNetwork.addCable(cableTileEntity);
                        cableTileEntity.setNetworkID(largestNetwork.getID());
                    }
                } else {
                    CableNetwork cableNetwork = cableNetworksSavedData.createNetwork();
                    cableNetwork.addCable(cableTileEntity);
                    cableTileEntity.setNetworkID(cableNetwork.getID());
                }
            }
        }
    }

    public static BooleanProperty getPropertyFromDirection(Direction direction) {
        return switch (direction) {
            case UP -> UP;
            case DOWN -> DOWN;
            case EAST -> EAST;
            case WEST -> WEST;
            case SOUTH -> SOUTH;
            default -> NORTH;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isClientSide() && newState.getBlock() != state.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof CableTileEntity) {
                CableNetworksSavedData.get((ServerLevel) worldIn).getNetworks().get(((CableTileEntity) tileEntity).getNetworkID()).removeCable(pos, (ServerLevel) worldIn, state);
            }
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem() == Items.STICK && !worldIn.isClientSide()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity != null) {
                player.displayClientMessage(new TextComponent(tileEntity.getCapability(CapabilityEnergy.ENERGY).map(energy -> {
                    CableEnergyStorage cableEnergyStorage = (CableEnergyStorage) energy;
                    return "Stored: " + energy.getEnergyStored() + " Connectors: " + cableEnergyStorage.cableNetwork.getConnectors() + " Network: " + cableEnergyStorage.cableNetwork.getID();
                }).orElse("no")), true);
            }
        }
        return InteractionResult.PASS;
    }
}
