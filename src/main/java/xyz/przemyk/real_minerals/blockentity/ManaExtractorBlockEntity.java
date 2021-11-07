package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.ManaExtractorContainer;
import xyz.przemyk.real_minerals.datapack.recipes.ManaExtractorRecipe;
import xyz.przemyk.real_minerals.fluid.tank.DrainOnlyFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ManaExtractorBlockEntity extends ElectricMachineBlockEntity<ManaExtractorRecipe> {

    public static final int FE_PER_TICK = 5;
    public static final int WORKING_TIME_TOTAL = 120;
    public static final int CAPACITY = FluidAttributes.BUCKET_VOLUME;
    public final DrainOnlyFluidTank fluidTank = new DrainOnlyFluidTank(CAPACITY) {
        @Override
        protected void onContentsChanged() {
            markUpdated();
        }
    };
    public final LazyOptional<DrainOnlyFluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);
    public final ItemStackHandler itemStackHandler = new ItemStackHandler();
    public final LazyOptional<ItemStackHandler> itemStackHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);

    public ManaExtractorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.MANA_EXTRACTOR_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0), FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    @SuppressWarnings("ConstantConditions")
    private Optional<ManaExtractorRecipe> getRecipe(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return Optional.empty();
        }
        return level.getRecipeManager().getAllRecipesFor(Recipes.MANA_EXTRACTOR_RECIPE_TYPE).stream().filter(recipe -> recipe.ingredient().test(itemStack)).findFirst();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidTankLazyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        fluidTank.readFromNBT(nbt.getCompound("fluid_tank"));
        itemStackHandler.deserializeNBT(nbt.getCompound("inv"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("fluid_tank", fluidTank.writeToNBT(new CompoundTag()));
        compound.put("inv", itemStackHandler.serializeNBT());
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemStackHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void process(ManaExtractorRecipe recipe) {
        itemStackHandler.getStackInSlot(0).shrink(1);
        fluidTank.fillInternal(recipe.result());
    }

    @Override
    protected boolean canProcess(ManaExtractorRecipe recipe) {
        if (recipe != null) {
            if (recipe.result().isEmpty()) {
                return false;
            }
            FluidStack currentOutput = fluidTank.getFluid();
            if (currentOutput.isEmpty()) {
                return true;
            }

            return currentOutput.getAmount() + recipe.result().getAmount() <= fluidTank.getCapacity();
        }
        return false;
    }

    private ManaExtractorRecipe cachedRecipe = null;

    @Override
    protected ManaExtractorRecipe getCachedRecipe() {
        if (cachedRecipe != null && cachedRecipe.ingredient().test(itemStackHandler.getStackInSlot(0))) {
            return cachedRecipe;
        }

        return cachedRecipe = getRecipe(itemStackHandler.getStackInSlot(0)).orElse(null);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new ManaExtractorContainer(pContainerId, pInventory, getBlockPos(), itemStackHandler, new ElectricRecipeProcessingMachineSyncData(this), pPlayer);
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
