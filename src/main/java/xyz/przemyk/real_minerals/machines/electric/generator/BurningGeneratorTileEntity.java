package xyz.przemyk.real_minerals.machines.electric.generator;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BurningGeneratorTileEntity extends ElectricMachineTileEntity {

    public static final int FE_PER_TICK = 20;

    public final ItemStackHandler itemHandler = new ItemStackHandler();
    public int burnTime;
    public int burnTimeTotal;

    protected final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    public BurningGeneratorTileEntity() {
        super(Registering.BURNING_GENERATOR_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 0, 80));
    }

    @Override
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote()) {
            boolean dirty = false;

            if (isBurning()) {
                --burnTime;
                energyStorage.addEnergy(FE_PER_TICK);
            } else {
                ItemStack fuelStack = itemHandler.getStackInSlot(0);
                burnTime = ForgeHooks.getBurnTime(fuelStack);
                burnTimeTotal = burnTime;
                if (isBurning()) {
                    dirty = true;
                    if (fuelStack.hasContainerItem()) {
                        itemHandler.setStackInSlot(1, fuelStack.getContainerItem());
                    } else {
                        fuelStack.shrink(1);
                    }
                }
            }

            if (dirty) {
                markDirty();
            }
        }
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
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
    public ITextComponent getDisplayName() {
        return BurningGeneratorContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new BurningGeneratorContainer(id, playerInventory, getPos(), itemHandler, new GeneratorSyncData(this), serverPlayer);
    }

    private static class GeneratorSyncData implements IIntArray {
        private final BurningGeneratorTileEntity machine;

        public GeneratorSyncData(BurningGeneratorTileEntity crusher) {
            this.machine = crusher;
        }

        public int get(int index) {
            switch(index) {
                case 0:
                    return machine.burnTime;
                case 1:
                    return machine.burnTimeTotal;
                case 2:
                    return machine.energyStorage.getEnergyStored();
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    machine.burnTime = value;
                    break;
                case 1:
                    machine.burnTimeTotal = value;
                    break;
                case 2:
                    machine.energyStorage.setEnergy(value);
            }

        }

        public int size() {
            return 3;
        }
    }
}
