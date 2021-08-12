package xyz.przemyk.real_minerals.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.Random;

public class MeteoriteFeature extends Feature<NoFeatureConfig> {

    public MeteoriteFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (rand.nextInt(500) == 0) {
            int x = pos.getX() + rand.nextInt(15);
            int z = pos.getZ() + rand.nextInt(15);
            int y = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z);
            if (y == 0) {
                return false;
            }

            final int maxY = 255;
            BlockPos.Mutable blockPos = new BlockPos.Mutable();

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
                            reader.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
            }

            BlockPos meteoritePos = new BlockPos(x, reader.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), z);
            reader.setBlockState(meteoritePos, Registering.METEORITE.BLOCK.get().getDefaultState(), 2);
            return true;
        }

        return false;
    }
}
