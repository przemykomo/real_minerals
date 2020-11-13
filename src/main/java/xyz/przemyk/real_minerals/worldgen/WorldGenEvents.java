package xyz.przemyk.real_minerals.worldgen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.init.RealMinerals;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = RealMinerals.MODID)
public class WorldGenEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void generateOres(BiomeLoadingEvent event) {
        Biome.Category category = event.getCategory();
        if (category != Biome.Category.NETHER && category != Biome.Category.THEEND) {
            List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);

            //TODO: change rarity and height of ore generation
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.COPPER_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.LEAD_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.MAGNESIUM_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.NICKEL_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.PLATINUM_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.SILVER_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.TIN_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.ALUMINUM_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));
            features.add(() -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Registering.ZINC_ORE.BLOCK.get().getDefaultState(), 9)).range(64).square().func_242731_b(20));

            //TODO: gravel ores
        }
    }
}
