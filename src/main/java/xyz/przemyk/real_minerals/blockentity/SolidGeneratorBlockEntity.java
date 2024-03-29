package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.containers.SolidGeneratorContainer;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.util.ElectricMachineEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SolidGeneratorBlockEntity extends EnergyOutputBlockEntity {

    public static final int FE_PER_TICK = 20;

    public final ItemStackHandler itemHandler = new ItemStackHandler();
    public int burnTime;
    public int burnTimeTotal;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public SolidGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MachinesRegistry.BURNING_GENERATOR_BLOCK_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 0, 80), blockPos, blockState);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            boolean dirty = false;

            if (isBurning()) {
                --burnTime;
                dirty = true;
                energyStorage.addEnergy(FE_PER_TICK);
                if (!isBurning()) {
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, false), 3);
                }
            } else if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                ItemStack fuelStack = itemHandler.getStackInSlot(0);
                burnTime = burnTimeTotal = ForgeHooks.getBurnTime(fuelStack, null);
                if (isBurning()) {
                    dirty = true;
                    level.setBlock(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, true), 3);
                    if (fuelStack.hasContainerItem()) {
                        itemHandler.setStackInSlot(1, fuelStack.getContainerItem());
                    } else {
                        fuelStack.shrink(1);
                    }
                }
            }

            if (dirty) {
                setChanged();
            }
        }
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        burnTime = nbt.getInt("BurnTime");
        burnTimeTotal = nbt.getInt("BurnTimeTotal");
        super.load(nbt);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("BurnTimeTotal", burnTimeTotal);
        return super.save(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player serverPlayer) {
        return new SolidGeneratorContainer(id, playerInventory, getBlockPos(), itemHandler, new GeneratorSyncData(this), serverPlayer);
    }

    private record GeneratorSyncData(SolidGeneratorBlockEntity machine) implements ContainerData {

        public int get(int index) {
            return switch (index) {
                case 0 -> machine.burnTime;
                case 1 -> machine.burnTimeTotal;
                case 2 -> machine.energyStorage.getEnergyStored();
                default -> 0;
            };
        }

        public void set(int index, int value) {}

        public int getCount() {
            return 3;
        }
    }
}
