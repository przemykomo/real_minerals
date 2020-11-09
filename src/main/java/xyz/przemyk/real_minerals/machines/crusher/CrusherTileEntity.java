package xyz.przemyk.real_minerals.machines.crusher;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
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

public class CrusherTileEntity extends TileEntity implements ITickableTileEntity {

    public static final int CRUSHING_TIME_TOTAL = 100;

    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
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

    public int burnTime;
    public int crushTime;

    public CrusherTileEntity() {
        super(RealMinerals.CRUSHER_TILE_ENTITY_TYPE.get());
    }

    @Override
    public void remove() {
        super.remove();
        itemHandlerLazyOptional.invalidate();
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
                CrusherRecipe recipe = getCachedRecipe(inputStack);
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

    private CrusherRecipe cachedRecipe = null;

    @SuppressWarnings("ConstantConditions")
    @Nullable
    private CrusherRecipe getCachedRecipe(ItemStack input){
        if (input == null){
            return null;
        }

        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = getRecipe(input, world);
        return cachedRecipe;
    }

    public static CrusherRecipe getRecipe(ItemStack input, World world) {
        Set<CrusherRecipe> recipes = getAllCrushingRecipes(world);
        for (CrusherRecipe recipe : recipes){
            if (recipe.isValidInput(input)) {
                return recipe;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked") //I cannot cast it directly to Set<CrusherRecipe> but generics somehow work
    public static<T extends IRecipe<?>> Set<T> getAllCrushingRecipes(World world) {
        return ((Set<T>) world.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == RealMinerals.CRUSHER_RECIPE_TYPE).collect(Collectors.toSet()));
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
