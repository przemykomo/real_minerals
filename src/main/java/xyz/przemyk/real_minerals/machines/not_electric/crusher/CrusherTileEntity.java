package xyz.przemyk.real_minerals.machines.not_electric.crusher;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.not_electric.MachineItemStackHandler;
import xyz.przemyk.real_minerals.machines.not_electric.MachineTileEntity;

import javax.annotation.Nullable;

public class CrusherTileEntity extends MachineTileEntity<CrusherRecipe> {

    @Override
    public int getWorkingTimeTotal() {
        return 100;
    }

    public CrusherTileEntity() {
        super(Registering.CRUSHER_TILE_ENTITY_TYPE.get(), new MachineItemStackHandler(1), RealMinerals.CRUSHER_RECIPE_TYPE);
    }

    @Override
    public ITextComponent getDisplayName() {
        return CrusherContainer.TITLE;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity serverPlayer) {
        return new CrusherContainer(id, playerInventory, getPos(), itemHandler, new MachineSyncData(this), serverPlayer);
    }
}
