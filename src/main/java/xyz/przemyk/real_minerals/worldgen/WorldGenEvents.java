package xyz.przemyk.real_minerals.worldgen;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.RealMinerals;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RealMinerals.MODID)
public class WorldGenEvents {

    public static final RuleTest GRAVEL = new BlockMatchTest(Blocks.GRAVEL);

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addFeatures(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            List<Supplier<ConfiguredFeature<?, ?>>> oreFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            //TODO: change rarity and height of ore generation
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.COPPER_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.LEAD_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.MAGNESIUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.NICKEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.PLATINUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.SILVER_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.TIN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.ALUMINUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.ZINC_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.MAGNESIUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

            //gravel ores
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(GRAVEL, Registering.GOLD_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(GRAVEL, Registering.PLATINUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(GRAVEL, Registering.RUTHENIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(GRAVEL, Registering.IRIDIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            oreFeatures.add(() -> Feature.ORE.configured(new OreConfiguration(GRAVEL, Registering.ZIRCONIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

            //meteorites
            if (category != Biome.BiomeCategory.NONE && category != Biome.BiomeCategory.RIVER && category != Biome.BiomeCategory.SWAMP && category != Biome.BiomeCategory.OCEAN && category != Biome.BiomeCategory.BEACH) {
                List<Supplier<ConfiguredFeature<?, ?>>> localModificationsFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.LOCAL_MODIFICATIONS);
                localModificationsFeatures.add(() -> Registering.METEORITE_FEATURE.get().configured(NoneFeatureConfiguration.INSTANCE));
            }
        }
    }
}
