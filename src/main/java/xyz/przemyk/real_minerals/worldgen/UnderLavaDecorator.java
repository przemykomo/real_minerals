package xyz.przemyk.real_minerals.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class UnderLavaDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {

    public UnderLavaDecorator(Codec<NoneDecoratorConfiguration> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecorationContext decorationContext, Random random, NoneDecoratorConfiguration cfg, BlockPos blockPos) {
        BlockPos origin = new BlockPos(blockPos.getX() + 8, 0, blockPos.getZ() + 8);
        WorldGenLevel level = decorationContext.getLevel();
        return BlockPos.betweenClosedStream(origin, origin.offset(15, 11, 15)).filter(pos -> level.getBlockState(pos).is(Blocks.LAVA))
                .flatMap(pos -> Arrays.stream(Direction.values())
                        .filter(direction -> direction != Direction.UP)
                        .map(pos::relative).filter(newPos -> !level.getBlockState(newPos).is(Blocks.LAVA)));
    }
}
