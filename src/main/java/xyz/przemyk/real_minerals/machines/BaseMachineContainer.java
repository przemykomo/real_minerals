package xyz.przemyk.real_minerals.machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public abstract class BaseMachineContainer extends Container {

    public final Block block;

    private final IWorldPosCallable usabilityTest;
    public final IIntArray machineData;

    protected BaseMachineContainer(@Nullable ContainerType<?> type, int id, Block block, BlockPos pos, IIntArray machineData, PlayerEntity playerEntity) {
        super(type, id);
        this.block = block;
        this.usabilityTest = IWorldPosCallable.of(playerEntity.world, pos);
        this.machineData = machineData;
        trackIntArray(machineData);
    }

    protected void addPlayerSlots(PlayerInventory playerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(usabilityTest, playerIn, block);
    }
}
