package xyz.przemyk.real_minerals.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.Random;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {

    public MeteoriteFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        Random rand = context.random();
        BlockPos pos = context.origin();
        WorldGenLevel reader = context.level();

        if (rand.nextInt(500) == 0) {
            int x = pos.getX() + rand.nextInt(15);
            int z = pos.getZ() + rand.nextInt(15);
            int y = reader.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
            if (y == 0) {
                return false;
            }

            final int maxY = 255;
            BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

            float meteoriteRadius = (rand.nextFloat() * 4) + 2;

            for (int j = y - 5; j <= maxY; j++) {
                blockPos.setY(j);

                for (int i = x - 30; i <= x + 30; i++) {
                    blockPos.setX(i);

                    for (int k = z - 30; k <= z + 30; k++) {
                        blockPos.setZ(k);
                        final double dx = i - x;
                        final double dz = k - z;
                        final double h = y - meteoriteRadius + 1;

                        final double distanceFrom = dx * dx + dz * dz;

                        if (j > h + distanceFrom * 0.02) {
                            reader.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
                        }
                    }
                }
            }

            BlockPos meteoritePos = new BlockPos(x, reader.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            reader.setBlock(meteoritePos, Registering.METEORITE.BLOCK.get().defaultBlockState(), 2);
            return true;
        }

        return false;
    }
}
