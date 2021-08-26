package xyz.przemyk.real_minerals.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.real_minerals.RealMinerals;

import javax.annotation.Nullable;
import java.util.List;

public class MagnetItem extends Item {

    public static final int RANGE = 7;

    public MagnetItem(Properties pProperties) {
        super(pProperties);
    }

    public static boolean isActive(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        return compoundtag != null && compoundtag.getBoolean("active");
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return isActive(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (isActive(pStack)) {
            pTooltipComponents.add(new TranslatableComponent(RealMinerals.MODID + ".active"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (isActive(pStack)) {
            AABB aabb = new AABB(pEntity.position().add(-RANGE, -RANGE, -RANGE), pEntity.position().add(RANGE, RANGE, RANGE));
            for (ItemEntity itemEntity : pLevel.getEntitiesOfClass(ItemEntity.class, aabb)) {
                Vec3 relativePos = new Vec3(pEntity.getX() - itemEntity.getX(), pEntity.getY() + (double) pEntity.getEyeHeight() / 2.0 - itemEntity.getY(), pEntity.getZ() - itemEntity.getZ());
                double sqrLength = relativePos.lengthSqr();
                if (sqrLength < 1.0) {
                    itemEntity.setDeltaMovement(0, 0, 0);
                    itemEntity.setPos(pEntity.getX(), pEntity.getY() + pEntity.getEyeHeight() / 2.0, pEntity.getZ());
                    itemEntity.setPickUpDelay(0);
                } else {
                    double force = 1.0 - Math.sqrt(sqrLength) / RANGE;
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(relativePos.normalize().scale(force * force)));
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            CompoundTag compoundTag = itemStack.getOrCreateTag();
            compoundTag.putBoolean("active", !compoundTag.getBoolean("active"));
        }

        return InteractionResultHolder.success(itemStack);
    }
}
