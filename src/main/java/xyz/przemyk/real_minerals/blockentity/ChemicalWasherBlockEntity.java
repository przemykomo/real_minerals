package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.ChemicalWasherContainer;
import xyz.przemyk.real_minerals.datapack.recipes.ChemicalWasherRecipe;
import xyz.przemyk.real_minerals.fluid.tank.DoubleFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChemicalWasherBlockEntity extends ElectricMachineBlockEntity<ChemicalWasherRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 120;

    @SuppressWarnings("ConstantConditions")
    public final DoubleFluidTank doubleFluidTank = new DoubleFluidTank(10_000, fluidStack -> {
        if (fluidStack.isEmpty()) {
            return false;
        } else {
            return level.getRecipeManager().getAllRecipesFor(Recipes.CHEMICAL_WASHER_RECIPE_TYPE).stream().anyMatch(chemicalWasherRecipe -> chemicalWasherRecipe.inputFluidStack().isFluidEqual(fluidStack));
        }
    }, this::markUpdated);
    public final LazyOptional<DoubleFluidTank> fluidTankLazyOptional = LazyOptional.of(() -> doubleFluidTank);

    public final ItemStackHandler itemHandler = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public ChemicalWasherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.CHEMICAL_WASHER_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidTankLazyOptional.invalidate();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        doubleFluidTank.readFromNBT(nbt.getCompound("fluid_tank"));
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("fluid_tank", doubleFluidTank.writeToNBT(new CompoundTag()));
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void process(ChemicalWasherRecipe recipe) {
        doubleFluidTank.input.drain(recipe.inputFluidStack(), IFluidHandler.FluidAction.EXECUTE);
        itemHandler.extractItem(0, 1, false);
        doubleFluidTank.output.fill(recipe.outputFluidStack(), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected boolean canProcess(ChemicalWasherRecipe recipe) {
        if (recipe != null && recipe.inputFluidStack().isFluidEqual(doubleFluidTank.input.getFluid()) && doubleFluidTank.input.getFluidAmount() >= recipe.inputFluidStack().getAmount()
        && recipe.ingredient().test(itemHandler.getStackInSlot(0))) {
            return doubleFluidTank.output.isEmpty() || (doubleFluidTank.output.getFluid().isFluidEqual(recipe.outputFluidStack()) && doubleFluidTank.output.getSpace() >= recipe.outputFluidStack().getAmount());
        }
        return false;
    }

    private ChemicalWasherRecipe cachedRecipe = null;

    @Override
    protected ChemicalWasherRecipe getCachedRecipe() {
        if (cachedRecipe != null && testRecipe(cachedRecipe)) {
            return cachedRecipe;
        }

        return cachedRecipe = level.getRecipeManager().getAllRecipesFor(Recipes.CHEMICAL_WASHER_RECIPE_TYPE).stream().filter(this::testRecipe).findFirst().orElse(null);
    }

    private boolean testRecipe(ChemicalWasherRecipe recipe) {
        return recipe.inputFluidStack().isFluidEqual(doubleFluidTank.input.getFluid()) && recipe.ingredient().test(itemHandler.getStackInSlot(0));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new ChemicalWasherContainer(id, playerInventory, getBlockPos(), itemHandler, new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        doubleFluidTank.writeToNBT(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        doubleFluidTank.readFromNBT(tag);
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
