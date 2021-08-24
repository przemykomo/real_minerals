package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.przemyk.real_minerals.containers.MixerContainer;
import xyz.przemyk.real_minerals.datapack.recipes.MixerRecipe;
import xyz.przemyk.real_minerals.fluid.tank.TwoInputOneOutputFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MixerBlockEntity extends ElectricMachineBlockEntity<MixerRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 120;

    public final TwoInputOneOutputFluidTank fluidTank = new TwoInputOneOutputFluidTank(10_000, fluidStack -> getRecipe(fluidStack).isPresent(), this::markUpdated);
    public final LazyOptional<IFluidHandler> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    @SuppressWarnings("ConstantConditions")
    private Optional<MixerRecipe> getRecipe(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) {
            return Optional.empty();
        }
        return level.getRecipeManager().getAllRecipesFor(Recipes.MIXER_RECIPE_TYPE).stream().filter(recipe -> recipe.firstInput().isFluidEqual(fluidStack) || recipe.secondInput().isFluidEqual(fluidStack)).findFirst();
    }

    public MixerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.MIXER_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
                FE_PER_TICK, WORKING_TIME_TOTAL, blockPos, blockState);
    }

    @Override
    public void tick() {
        super.tick();

        for (Direction direction : Direction.values()) {
            BlockEntity tileEntity = level.getBlockEntity(worldPosition.relative(direction));
            if (tileEntity != null) {
                tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite())
                        .ifPresent(fluidHandler -> FluidUtil.tryFluidTransfer(fluidHandler, fluidTank, FluidAttributes.BUCKET_VOLUME, true));
            }
        }
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
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("fluid_tank", fluidTank.writeToNBT(new CompoundTag()));
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void process(MixerRecipe recipe) {
        if (fluidTank.firstInput.getFluid().isFluidEqual(recipe.firstInput())) {
            fluidTank.firstInput.drain(recipe.firstInput(), IFluidHandler.FluidAction.EXECUTE);
            fluidTank.secondInput.drain(recipe.secondInput(), IFluidHandler.FluidAction.EXECUTE);
        } else {
            fluidTank.firstInput.drain(recipe.secondInput(), IFluidHandler.FluidAction.EXECUTE);
            fluidTank.secondInput.drain(recipe.firstInput(), IFluidHandler.FluidAction.EXECUTE);
        }
        fluidTank.output.fill(recipe.output(), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected boolean canProcess(MixerRecipe recipe) {
        if (recipe != null) {
            if ((fluidTank.firstInput.getFluid().isFluidEqual(recipe.firstInput()) && fluidTank.firstInput.getFluidAmount() >= recipe.firstInput().getAmount()
            && fluidTank.secondInput.getFluid().isFluidEqual(recipe.secondInput()) && fluidTank.secondInput.getFluidAmount() >= recipe.secondInput().getAmount())
            || (fluidTank.secondInput.getFluid().isFluidEqual(recipe.firstInput()) && fluidTank.secondInput.getFluidAmount() >= recipe.firstInput().getAmount()
                    && fluidTank.firstInput.getFluid().isFluidEqual(recipe.secondInput()) && fluidTank.firstInput.getFluidAmount() >= recipe.secondInput().getAmount())) {
                return fluidTank.output.isEmpty() || (fluidTank.output.getFluid().isFluidEqual(recipe.output()) && fluidTank.output.getSpace() >= recipe.output().getAmount());
            }
        }
        return false;
    }

    private MixerRecipe cachedRecipe = null;

    @Override
    protected MixerRecipe getCachedRecipe() {
        FluidStack firstInput = fluidTank.firstInput.getFluid();
        FluidStack secondInput = fluidTank.secondInput.getFluid();
        if (cachedRecipe != null && ((firstInput.isFluidEqual(cachedRecipe.firstInput()) && secondInput.isFluidEqual(cachedRecipe.secondInput()))
                || (secondInput.isFluidEqual(cachedRecipe.firstInput()) && firstInput.isFluidEqual(cachedRecipe.secondInput())))) {
            return cachedRecipe;
        }
        return cachedRecipe = level.getRecipeManager().getAllRecipesFor(Recipes.MIXER_RECIPE_TYPE).stream().filter(recipe ->
                (recipe.firstInput().isFluidEqual(firstInput) && recipe.secondInput().isFluidEqual(secondInput))
                        || (recipe.secondInput().isFluidEqual(firstInput) && recipe.firstInput().isFluidEqual(secondInput))).findFirst().orElse(null);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new MixerContainer(id, playerInventory, getBlockPos(), new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        fluidTank.writeToNBT(tag);
        return tag;
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
