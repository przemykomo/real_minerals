package xyz.przemyk.real_minerals.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.real_minerals.init.Registering;

public class MagnetBlockEntity extends BlockEntity implements TickableBlockEntity {

    public static final int RANGE = 7;

    public MagnetBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registering.MAGNET_BLOCK_ENTITY_TYPE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void tick() {
        AABB aabb = new AABB(worldPosition.offset(-RANGE, -RANGE, -RANGE), worldPosition.offset(RANGE, RANGE, RANGE));
        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, aabb)) {
            Vec3 relativePos = new Vec3(worldPosition.getX() - itemEntity.getX(), (worldPosition.getY() - itemEntity.getY()) * 2, worldPosition.getZ() - itemEntity.getZ());
            double force = 1.0 - relativePos.length() / RANGE;
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(relativePos.normalize().scale(force * force * 0.1)));
        }
    }
}
