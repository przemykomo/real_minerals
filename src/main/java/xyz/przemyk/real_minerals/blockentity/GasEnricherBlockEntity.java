package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.containers.GasEnricherContainer;
import xyz.przemyk.real_minerals.datapack.recipes.GasEnricherRecipe;
import xyz.przemyk.real_minerals.fluid.tank.DoubleFluidTank;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Recipes;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class GasEnricherBlockEntity extends ElectricMachineBlockEntity<GasEnricherRecipe> {

    public static final int FE_PER_TICK = 20;
    public static final int WORKING_TIME_TOTAL = 120;

    public final DoubleFluidTank doubleFluidTank = new DoubleFluidTank(10_000, fluidStack -> getRecipe(fluidStack).isPresent(), this::markUpdated);
    public final LazyOptional<DoubleFluidTank> fluidTankLazyOptional = LazyOptional.of(() -> doubleFluidTank);

    @SuppressWarnings("ConstantConditions")
    private Optional<GasEnricherRecipe> getRecipe(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) {
            return Optional.empty();
        }
        return level.getRecipeManager().getAllRecipesFor(Recipes.GAS_ENRICHER_RECIPE_TYPE).stream().filter(gasEnricherRecipe -> gasEnricherRecipe.input().isFluidEqual(fluidStack)).findFirst();
    }

    public GasEnricherBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.GAS_ENRICHER_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0),
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
                        .ifPresent(fluidHandler -> FluidUtil.tryFluidTransfer(fluidHandler, doubleFluidTank, FluidAttributes.BUCKET_VOLUME, true));
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
        doubleFluidTank.readFromNBT(nbt.getCompound("fluid_tank"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("fluid_tank", doubleFluidTank.writeToNBT(new CompoundTag()));
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
    protected void process(GasEnricherRecipe recipe) {
        doubleFluidTank.input.drain(recipe.input(), IFluidHandler.FluidAction.EXECUTE);
        doubleFluidTank.output.fill(recipe.output(), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected boolean canProcess(GasEnricherRecipe recipe) {
        if (recipe != null && doubleFluidTank.input.getFluid().isFluidEqual(recipe.input()) && doubleFluidTank.input.getFluidAmount() >= recipe.input().getAmount()) {
            return doubleFluidTank.output.isEmpty() ||
                    (doubleFluidTank.output.getFluid().isFluidEqual(recipe.output())
                            && doubleFluidTank.output.getSpace() >= recipe.output().getAmount());
        }
        return false;
    }

    private GasEnricherRecipe cachedRecipe = null;

    @Override
    protected GasEnricherRecipe getCachedRecipe() {
        FluidStack input = doubleFluidTank.input.getFluid();
        if (cachedRecipe != null && input.isFluidEqual(cachedRecipe.input())) {
            return cachedRecipe;
        }

        return cachedRecipe = getRecipe(input).orElse(null);
    }

    public static final TranslatableComponent TITLE = new TranslatableComponent(RealMinerals.MODID + ".name.gas_enricher");

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new GasEnricherContainer(id, playerInventory, getBlockPos(), new ElectricRecipeProcessingMachineSyncData(this), serverPlayer);
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
