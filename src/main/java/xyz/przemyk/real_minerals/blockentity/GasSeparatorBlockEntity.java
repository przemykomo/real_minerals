package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.GasSeparatorContainer;
import xyz.przemyk.real_minerals.datapack.recipes.GasSeparatorRecipe;
import xyz.przemyk.real_minerals.fluid.tank.DrainOnlyFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GasSeparatorBlockEntity extends ElectricMachineBlockEntity<GasSeparatorRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            markUpdated();
        }
    };
    public final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public final DrainOnlyFluidTank fluidTank = new DrainOnlyFluidTank(FluidAttributes.BUCKET_VOLUME);
    private final LazyOptional<IFluidHandler> fluidHandlerLazyOptional = LazyOptional.of(() -> fluidTank);

    public GasSeparatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.GAS_SEPARATOR_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();

        for (Direction direction : Direction.values()) {
            BlockEntity tileEntity = level.getBlockEntity(worldPosition.relative(direction));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())
                        .ifPresent(fluidHandler -> FluidUtil.tryFluidTransfer(fluidHandler, fluidTank, fluidTank.getFluidAmount(), true));
            }
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
        fluidHandlerLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        fluidTank.readFromNBT(nbt.getCompound("fluid_tank"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.put("fluid_tank", fluidTank.writeToNBT(new CompoundTag()));
        return super.save(compound);
    }

    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    private GasSeparatorRecipe cachedRecipe = null;

    @Override
    public GasSeparatorRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = Recipes.getRecipe(input, level, Recipes.GAS_SEPARATOR_RECIPE_TYPE);
        return cachedRecipe;
    }

    @Override
    protected boolean canProcess(GasSeparatorRecipe recipe) {
        if (recipe != null) {
            FluidStack fluidOutput = recipe.getFluidOutput();
            ItemStack itemOutput = recipe.getResultItem();
            if (fluidOutput.isEmpty()) {
                return false;
            }
            FluidStack currentFluidOutput = fluidTank.getFluid();
            ItemStack currentItemOutput = itemHandler.getStackInSlot(1);
            if (currentFluidOutput.isEmpty() && (currentItemOutput.isEmpty() || itemOutput.isEmpty())) {
                return true;
            }

            if (!currentFluidOutput.isEmpty() && !currentFluidOutput.isFluidEqual(fluidOutput)) {
                return false;
            }

            if (!currentItemOutput.isEmpty() && !currentItemOutput.sameItem(itemOutput)) {
                return false;
            }

            return (currentFluidOutput.getAmount() + fluidOutput.getAmount() <= fluidTank.getCapacity()) && (currentItemOutput.getCount() + itemOutput.getCount() <= currentItemOutput.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void process(GasSeparatorRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack itemStack = itemHandler.getStackInSlot(1);

        FluidStack fluidOutput = recipe.getFluidOutput();
        ItemStack itemOutput = recipe.getResultItem();

        fluidTank.fillInternal(fluidOutput);

        if (!itemOutput.isEmpty()) {
            if (itemStack.isEmpty()) {
                itemHandler.setStackInSlot(1, itemOutput.copy());
            } else {
                itemStack.grow(itemOutput.getCount());
            }
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new GasSeparatorContainer(id, playerInventory, getBlockPos(), itemHandler, new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return fluidTank.writeToNBT(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        fluidTank.readFromNBT(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
    }
}
