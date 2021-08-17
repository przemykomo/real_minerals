package xyz.przemyk.real_minerals.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.przemyk.real_minerals.init.StoneMinerals;

public class SulfurFeature extends Feature<NoneFeatureConfiguration> {

    public SulfurFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        if (context.random().nextFloat() > 0.4) {
            return false;
        }
        boolean ret = false;
        int max = 2;

        BlockPos blockPos = context.origin();

        for (int i = 0; i < max; i++) {
            if (context.level().getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                context.level().setBlock(blockPos, StoneMinerals.SULFUR_ORE.BLOCK.get().defaultBlockState(), 2);
                ret = true;
            }

            blockPos = blockPos.below();
        }

        return ret;
    }
}
