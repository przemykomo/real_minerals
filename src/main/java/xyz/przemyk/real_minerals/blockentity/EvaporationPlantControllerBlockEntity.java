package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.EvaporationPlantContainer;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.multiblock.Cuboid;
import xyz.przemyk.real_minerals.util.FluidUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

import static net.minecraft.core.Direction.*;

@SuppressWarnings("deprecation")
public class EvaporationPlantControllerBlockEntity extends BlockEntity implements TickableBlockEntity, MenuProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler();
    public final LazyOptional<ItemStackHandler> itemStackHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    public final FluidTank fluidTank = new FluidTank(0) {
        @Override
        protected void onContentsChanged() {
            markUpdated();
        }
    };
    public final LazyOptional<IFluidHandler> fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);

    public static final int FLUID_AMOUNT_TO_RESULT = 201;
    public FluidStack fluidStackInProgress = FluidStack.EMPTY;
    public Cuboid innerCuboid = null;
    private int ticks = 0;

    public EvaporationPlantControllerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.EVAPORATION_PLANT_CONTROLLER_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemStackHandlerLazyOptional.invalidate();
        fluidHandlerLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        fluidTank.readFromNBT(compoundTag.getCompound("input"));
        fluidStackInProgress = FluidStack.loadFluidStackFromNBT(compoundTag.getCompound("fluid_in_progress"));
        itemStackHandler.deserializeNBT(compoundTag.getCompound("inv"));
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        compoundTag.put("input", fluidTank.writeToNBT(new CompoundTag()));
        compoundTag.put("fluid_in_progress", fluidStackInProgress.writeToNBT(new CompoundTag()));
        compoundTag.put("inv", itemStackHandler.serializeNBT());
        return super.save(compoundTag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemStackHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (ticks % 40 == 0) {
                boolean wasNull = innerCuboid == null;
                innerCuboid = detectMultiblock(level, getBlockPos(), getBlockState().getValue(HorizontalDirectionalBlock.FACING));
                fluidTank.setCapacity(innerCuboid == null ? 0 : (innerCuboid.end().getX() - innerCuboid.begin().getX()) * (innerCuboid.end().getZ() - innerCuboid.begin().getZ()) * FluidAttributes.BUCKET_VOLUME);
                if (wasNull != (innerCuboid == null)) {
                    markUpdated();
                }
            }
            ticks++;

            if (innerCuboid != null && ticks % 10 == 0) {
                if (fluidStackInProgress.isEmpty() && !fluidTank.isEmpty() && FluidUtils.getDissolvedItem(fluidTank.getFluid()) != null) {
                    fluidStackInProgress = fluidTank.getFluid().copy();
                    fluidStackInProgress.setAmount(1); //I'd set it to 0, but then it'd become empty
                }

                if (!fluidTank.isEmpty() && fluidTank.getFluid().isFluidEqual(fluidStackInProgress)) {
                    fluidStackInProgress.grow(fluidTank.drain(fluidTank.getCapacity() / FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.EXECUTE).getAmount());
                }

                if (fluidStackInProgress.getAmount() >= FLUID_AMOUNT_TO_RESULT) {
                    ItemStack output = itemStackHandler.getStackInSlot(0);
                    Item item = FluidUtils.getDissolvedItem(fluidStackInProgress);
                    if (item != null && (output.isEmpty() || output.is(item))) {
                        fluidStackInProgress.shrink(FLUID_AMOUNT_TO_RESULT);
                        itemStackHandler.insertItem(0, item.getDefaultInstance(), false);
                    }
                }
            }
        }
    }

    private static final int maxHeight = 1;
    private static final int innerLimit = 5;

    public static boolean isValidBlock(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.is(Tags.Blocks.OBSIDIAN) || blockState.is(MachinesRegistry.EVAPORATION_PLANT_CONTROLLER_BLOCK.BLOCK.get());
    }

    public static Cuboid detectMultiblock(Level world, BlockPos master, Direction facing) {
        // center is the lowest block behind in a position behind the controller
        BlockPos center = getOuterPos(world, master.relative(facing.getOpposite()), Direction.DOWN, maxHeight).above();

        // below lowest internal position
        if (master.getY() < center.getY()) {
//            System.out.println("error, INVALID_INNER_BLOCK" + center.below());
            return null;
        }

        // distances to the edges including the outer blocks
        int[] edges = new int[4];
        // order: south/west/north/east
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            // move to wall
            BlockPos pos = getOuterPos(world, center, direction, innerLimit + 1);
            edges[direction.get2DDataValue()] = (pos.getX() - center.getX()) + (pos.getZ() - center.getZ());
        }

        // walls too far away?
        int xd = (edges[SOUTH.get2DDataValue()] - edges[NORTH.get2DDataValue()]) - 1;
        int zd = (edges[EAST.get2DDataValue()] - edges[WEST.get2DDataValue()]) - 1;
        if (xd > innerLimit || zd > innerLimit) {
//            System.out.println("error, TOO_LARGE, xd=" + xd + ", zd=" + zd);
            return null;
        }

        // for the rest of calculation, will use a from and a to position bounds
        BlockPos from = center.offset(edges[WEST.get2DDataValue()], 0, edges[NORTH.get2DDataValue()]);
        BlockPos to = center.offset(edges[EAST.get2DDataValue()], 0, edges[SOUTH.get2DDataValue()]);

        // check the floor (frame check done inside)
        boolean result = detectCap(world, from.below(), to.below());
        if (!result) {
//            System.out.println("can't detectCap!");
            return null;
        }

        // go up layer for layer (again, frame check done inside)
        int height = 0;
        int localMax = Math.min(maxHeight, world.getHeight() - center.getY());
        // its fine to fail on a layer above the first, so store the result in case we need it
        boolean heightResult;
        for (; height < localMax; height++) {
            heightResult = detectLayer(world, from.above(height), to.above(height));
            if (!heightResult) {
                return null;
            }
        }

        // no walls?
        if (height == 0 || height <= master.getY() - center.getY()) {
//            System.out.println("not walls?");
            return null;
        }

        // get inner bounds
        BlockPos minPos = from.offset(1, 0, 1);
        BlockPos maxPos = to.above(height - 1);
        return new Cuboid(minPos, maxPos);
    }

    private static BlockPos getOuterPos(Level world, BlockPos pos, Direction direction, int limit) {
        for (int i = 0; i < limit && world.isLoaded(pos) && world.isEmptyBlock(pos); i++) {
            pos = pos.relative(direction);
        }

        return pos;
    }

    private static boolean detectCap(Level world, BlockPos from, BlockPos to) {
        // ensure the area is loaded before trying
        if (!world.hasChunksAt(from, to)) {
//            System.out.println("not loaded");
            return false;
        }

        // validate frame first
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int height = from.getY();

        // validate inside of the floor
        for (int z = from.getZ() + 1; z < to.getZ(); z++) {
            for (int x = from.getX() + 1; x < to.getX(); x++) {
                if (!isValidBlock(world, mutable.set(x, height, z))) {
//                    System.out.println("INVALID_FLOOR_BLOCK " + mutable);
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean detectLayer(Level world, BlockPos from, BlockPos to) {
        // ensure its loaded
        if (!world.hasChunksAt(from, to)) {
//            System.out.println("detectLayer NOT_LOADED");
            return false;
        }

        // validate frame first
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int height = from.getY();

        // validate the inside
        for (int x = from.getX() + 1; x < to.getX(); x++) {
            for (int z = from.getZ() + 1; z < to.getZ(); z++) {
                // ensure its a valid block for inside the structure
                mutable.set(x, height, z);
                if (!world.isEmptyBlock(mutable)) {
//                    System.out.println("INVALID_INNER_BLOCK line 224 mutable = " + mutable);
                    return false;
                }
            }
        }

        // validate the 4 sides
        Predicate<BlockPos> wallCheck = pos -> isValidBlock(world, pos);
        for (int x = from.getX() + 1; x < to.getX(); x++) {
            if (!wallCheck.test(mutable.set(x, height, from.getZ()))) {
//                System.out.println("INVALID_WALL_BLOCK wallCheck mutable = " + mutable);
                return false;
            }
            if (!wallCheck.test(mutable.set(x, height, to.getZ()))) {
//                System.out.println("INVALID_WALL_BLOCK wallCheck mutable = " + mutable);
                return false;
            }
        }
        for (int z = from.getZ() + 1; z < to.getZ(); z++) {
            if (!wallCheck.test(mutable.set(from.getX(), height, z))) {
//                System.out.println("INVALID_WALL_BLOCK wallCheck mutable = " + mutable);
                return false;
            }
            if (!wallCheck.test(mutable.set(to.getX(), height, z))) {
//                System.out.println("INVALID_WALL_BLOCK wallCheck mutable = " + mutable);
                return false;
            }
        }
        return true;
    }

    private void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.put("input", fluidTank.writeToNBT(new CompoundTag()));
        compoundTag.putInt("capacity", fluidTank.getCapacity());
        compoundTag.put("in_progress", fluidStackInProgress.writeToNBT(new CompoundTag()));

        if (innerCuboid != null) {
            CompoundTag cuboidTag = new CompoundTag();
            cuboidTag.putInt("x1", innerCuboid.begin().getX());
            cuboidTag.putInt("y1", innerCuboid.begin().getY());
            cuboidTag.putInt("z1", innerCuboid.begin().getZ());
            cuboidTag.putInt("x2", innerCuboid.end().getX());
            cuboidTag.putInt("y2", innerCuboid.end().getY());
            cuboidTag.putInt("z2", innerCuboid.end().getZ());

            compoundTag.put("cuboid", cuboidTag);
        }
        return compoundTag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        fluidTank.readFromNBT(tag.getCompound("input"));
        fluidTank.setCapacity(tag.getInt("capacity"));
        fluidStackInProgress = FluidStack.loadFluidStackFromNBT(tag.getCompound("in_progress"));

        if (tag.contains("cuboid", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag cuboidTag = tag.getCompound("cuboid");
            innerCuboid = new Cuboid(new BlockPos(cuboidTag.getInt("x1"), cuboidTag.getInt("y1"), cuboidTag.getInt("z1")),
                    new BlockPos(cuboidTag.getInt("x2"), cuboidTag.getInt("y2"), cuboidTag.getInt("z2")));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new EvaporationPlantContainer(id, playerInventory, getBlockPos(), itemStackHandler, serverPlayer);
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (innerCuboid != null) {
            return new AABB(innerCuboid.begin(), innerCuboid.end());
        }
        return super.getRenderBoundingBox();
    }
}
