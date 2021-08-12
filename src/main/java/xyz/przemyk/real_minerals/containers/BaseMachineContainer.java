package xyz.przemyk.real_minerals.containers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;

public abstract class BaseMachineContainer extends AbstractContainerMenu {

    public final Block block;

    private final ContainerLevelAccess usabilityTest;
    public final ContainerData machineData;

    protected BaseMachineContainer(@Nullable MenuType<?> type, int id, Block block, BlockPos pos, ContainerData machineData, Player playerEntity) {
        super(type, id);
        this.block = block;
        this.usabilityTest = ContainerLevelAccess.create(playerEntity.level, pos);
        this.machineData = machineData;
        addDataSlots(machineData);
    }

    protected void addPlayerSlots(Inventory playerInventory) {
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
    public boolean stillValid(Player playerIn) {
        return stillValid(usabilityTest, playerIn, block);
    }
}
