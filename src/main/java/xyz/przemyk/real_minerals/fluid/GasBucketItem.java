package xyz.przemyk.real_minerals.fluid;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class GasBucketItem extends BucketItem {

    public GasBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
    }

    @Override
    public boolean tryPlaceContainedLiquid(@Nullable PlayerEntity player, World worldIn, BlockPos posIn, @Nullable BlockRayTraceResult rayTrace) {
        worldIn.playSound(player, posIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

        for(int l = 0; l < 8; ++l) {
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, (double)posIn.getX() + Math.random(), (double)posIn.getY() + Math.random(), (double)posIn.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
        }
        return true;
    }
}
