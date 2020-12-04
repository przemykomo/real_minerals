package xyz.przemyk.real_minerals.machines.electric.magnetizer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.electric.ElectricMachineEnergyStorage;
import xyz.przemyk.real_minerals.machines.electric.ElectricRecipeProcessingTileEntity;

public class MagnetizerTileEntity extends ElectricRecipeProcessingTileEntity<MagnetizerRecipe> {

    public static final int FE_PER_TICK = 60;
    public static final int WORKING_TIME_TOTAL = 120;

    public MagnetizerTileEntity() {
        super(Registering.MAGNETIZER_TILE_ENTITY_TYPE.get(), new ElectricMachineEnergyStorage(10_000, 80, 0), FE_PER_TICK, 2, WORKING_TIME_TOTAL);
    }

    private MagnetizerRecipe cachedRecipe = null;

    @Override
    protected MagnetizerRecipe getCachedRecipe() {
        NonNullList<ItemStack> input = NonNullList.withSize(1, itemHandler.getStackInSlot(0));
        if (cachedRecipe != null && cachedRecipe.isValidInput(input)) {
            return cachedRecipe;
        }

        cachedRecipe = RealMinerals.getRecipe(input, world, RealMinerals.MAGNETIZER_RECIPE_TYPE);
        return cachedRecipe;
    }

    @Override
    protected boolean canProcess(MagnetizerRecipe recipe) {
        if (recipe != null) {
            ItemStack outputStack = recipe.getRecipeOutput();
            if (outputStack.isEmpty()) {
                return false;
            }
            ItemStack currentOutput = itemHandler.getStackInSlot(1);
            if (currentOutput.isEmpty()) {
                return true;
            }
            return currentOutput.getCount() + outputStack.getCount() <= currentOutput.getMaxStackSize();
        }
        return false;
    }

    @Override
    protected void process(MagnetizerRecipe recipe) {
        itemHandler.getStackInSlot(0).shrink(1);
        ItemStack outputStack = itemHandler.getStackInSlot(1);
        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(1, recipeOutput.copy());
        } else {
            outputStack.grow(recipeOutput.getCount());
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return MagnetizerContainer.TITLE;
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new MagnetizerContainer(id, playerInventory, getPos(), itemHandler, new RecipeProcessingMachineSyncData(this), serverPlayer);
    }
}
