package xyz.przemyk.real_minerals.machines.not_electric;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class MachineOutputSlot extends SlotItemHandler {
    private final PlayerEntity player;
    private int removeCount;

    public MachineOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, PlayerEntity playerEntity) {
        super(itemHandler, index, xPosition, yPosition);
        player = playerEntity;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        this.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    @Override
    protected void onCrafting(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    @Override
    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        this.removeCount = 0;
        // TODO: playerCrushedEvent similar to playerSmeltedEvent?
    }
}
