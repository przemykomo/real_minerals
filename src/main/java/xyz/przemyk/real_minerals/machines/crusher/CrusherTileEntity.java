package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.real_minerals.init.RealMinerals;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

public class CrusherTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public static final int CRUSHING_TIME_TOTAL= 100;

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == 1 && ForgeHooks.getBurnTime(stack) == 0) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };

    private final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

    private int burnTime;
    private int crushTime;

    public final IIntArray crusherData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return burnTime;
                case 1:
                    return crushTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    burnTime = value;
                    break;
                case 1:
                    crushTime = value;
                    break;
            }

        }

        public int size() {
            return 2;
        }
    };

    public CrusherTileEntity() {
        super(RealMinerals.CRUSHER_TILE_ENTITY_TYPE.get());
    }

    @Override
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(RealMinerals.MODID + ".name.crusher");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CrusherContainer(i, world, pos, playerInventory, playerEntity);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void tick() {
        boolean wasBurning = isBurning();
        boolean dirty = false;
        if (wasBurning) {
            --burnTime;
        }

        if (!world.isRemote()) {
            ItemStack fuelStack = itemHandler.getStackInSlot(1);
            ItemStack inputStack = itemHandler.getStackInSlot(0);
            if (isBurning() || !fuelStack.isEmpty() && !inputStack.isEmpty()) {
                CrusherRecipe recipe = getRecipe(inputStack);
                if (canCrush(recipe)) {
                    if (!isBurning()) {
                        burnTime = ForgeHooks.getBurnTime(fuelStack);
                        if (isBurning()) {
                            dirty = true;
                            if (fuelStack.hasContainerItem()) {
                                itemHandler.setStackInSlot(1, fuelStack.getContainerItem());
                            } else {
                                fuelStack.shrink(1);
                            }
                        }
                    } else {
                        ++crushTime;
                        if (crushTime >= CRUSHING_TIME_TOTAL) {
                            crushTime = 0;
                            crush(recipe);
                            dirty = true;
                        }
                    }
                } else {
                    crushTime = 0;
                }
            } else if (!isBurning() && crushTime > 0) {
                crushTime = MathHelper.clamp(crushTime - 2, 0, CRUSHING_TIME_TOTAL);
            }

            //todo: if changed burning state, then set blockstate
        }

        if (dirty) {
            markDirty();
        }
    }

    private boolean canCrush(@Nullable CrusherRecipe recipe) {
        if (!itemHandler.getStackInSlot(0).isEmpty() && recipe != null) {
            ItemStack outputStack = recipe.getRecipeOutput();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(2);
            if (currentOutput.isEmpty()) {
                return true;
            }
            if (!currentOutput.isItemEqual(outputStack)) {
                return false;
            }
            return currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    private void crush(CrusherRecipe recipe) {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack recipeOutput = recipe.getRecipeOutput();
        ItemStack currentOutput = itemHandler.getStackInSlot(2);

        if (currentOutput.isEmpty()) {
            itemHandler.setStackInSlot(2, recipeOutput.copy());
        } else if (recipeOutput.isItemEqual(currentOutput)) {
            currentOutput.grow(recipeOutput.getCount());
        }

        input.shrink(1);
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    private CrusherRecipe getRecipe(ItemStack input){
        if (input == null){
            return null;
        }
        Set<CrusherRecipe> recipes = findRecipeByType(RealMinerals.CRUSHER_RECIPE_TYPE, this.world);
        for (CrusherRecipe recipe : recipes){
            if (recipe.isValidInput(input)) {
                return recipe;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked") //TODO: is this safe?
    public static<T extends IRecipe<?>> Set<T> findRecipeByType(IRecipeType<T> typeIn, World world) {
        return ((Set<T>) world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == typeIn).collect(Collectors.toSet()));
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        burnTime = nbt.getInt("BurnTime");
        crushTime = nbt.getInt("CrushTime");
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("BurnTime", burnTime);
        compound.putInt("CrushTime", crushTime);
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
}
