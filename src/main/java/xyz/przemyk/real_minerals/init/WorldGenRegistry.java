package xyz.przemyk.real_minerals.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.worldgen.MeteoriteFeature;

import java.util.List;
import java.util.function.Supplier;

public class WorldGenRegistry {

    public static final RuleTest GRAVEL = new BlockMatchTest(Blocks.GRAVEL);
    public static final RuleTest OBSIDIAN = new BlockMatchTest(Blocks.OBSIDIAN);

    public static final RegistryObject<MeteoriteFeature> METEORITE_FEATURE = Registering.FEATURES.register("meteorite", () -> new MeteoriteFeature(NoneFeatureConfiguration.CODEC));

    //TODO: change rarity and height of ore generation
    public static ConfiguredFeature<?, ?> COPPER_ORE;
    public static ConfiguredFeature<?, ?> LEAD_ORE;
    public static ConfiguredFeature<?, ?> MAGNESIUM_ORE;
    public static ConfiguredFeature<?, ?> NICKEL_ORE;
    public static ConfiguredFeature<?, ?> PLATINUM_ORE;
    public static ConfiguredFeature<?, ?> SILVER_ORE;
    public static ConfiguredFeature<?, ?> TIN_ORE;
    public static ConfiguredFeature<?, ?> ALUMINUM_ORE;
    public static ConfiguredFeature<?, ?> ZINC_ORE;
    public static ConfiguredFeature<?, ?> MAGNETITE_ORE;

    public static ConfiguredFeature<?, ?> GOLD_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> PLATINUM_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> RUTHENIUM_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> IRIDIUM_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> ZIRCONIUM_GRAVEL_ORE;

    public static ConfiguredFeature<?, ?> TUNGSTEN_OBSIDIAN_ORE;
    public static ConfiguredFeature<?, ?> RHENIUM_OBSIDIAN_ORE;

    public static ConfiguredFeature<?, ?> METEORITE;

    public static void init(IEventBus eventBus) {
        eventBus.addListener(WorldGenRegistry::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(WorldGenRegistry::addFeatures);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_copper"), COPPER_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.COPPER_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_lead"), LEAD_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.LEAD_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_magnesium"), MAGNESIUM_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.MAGNESIUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_nickel"), NICKEL_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.NICKEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_platinum"), PLATINUM_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.PLATINUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_silver"), SILVER_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.SILVER_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_tin"), TIN_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.TIN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_aluminum"), ALUMINUM_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.ALUMINUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_zinc"), ZINC_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.ZINC_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_magnetite"), MAGNETITE_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.MAGNETITE_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_gold"), GOLD_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.GOLD_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_platinum"), PLATINUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.PLATINUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_ruthenium"), RUTHENIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.RUTHENIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_iridium"), IRIDIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.IRIDIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_zirconium"), ZIRCONIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.ZIRCONIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

//            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_obsidian_chromium"), CHROMIUM_OBSIDIAN_ORE = OBSIDIAN_ORE_FEATURE.get().configured(new UnderwaterMagmaConfiguration(10, 10, 1F)).squared().rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(39)).count(UniformInt.of(4, 10)));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_obsidian_tungsten"), TUNGSTEN_OBSIDIAN_ORE = Feature.ORE.configured(new OreConfiguration(OBSIDIAN, ObsidianMinerals.TUNGSTEN_OBSIDIAN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(20)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_obsidian_rhenium"), RHENIUM_OBSIDIAN_ORE = Feature.ORE.configured(new OreConfiguration(OBSIDIAN, ObsidianMinerals.RHENIUM_OBSIDIAN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(20)).squared().count(20));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "meteorite"), METEORITE = METEORITE_FEATURE.get().configured(NoneFeatureConfiguration.INSTANCE));
        });
    }

    public static void addFeatures(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            List<Supplier<ConfiguredFeature<?, ?>>> oreFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            oreFeatures.add(() -> COPPER_ORE);
            oreFeatures.add(() -> LEAD_ORE);
            oreFeatures.add(() -> MAGNESIUM_ORE);
            oreFeatures.add(() -> NICKEL_ORE);
            oreFeatures.add(() -> PLATINUM_ORE);
            oreFeatures.add(() -> SILVER_ORE);
            oreFeatures.add(() -> TIN_ORE);
            oreFeatures.add(() -> ALUMINUM_ORE);
            oreFeatures.add(() -> ZINC_ORE);
            oreFeatures.add(() -> MAGNETITE_ORE);

            oreFeatures.add(() -> GOLD_GRAVEL_ORE);
            oreFeatures.add(() -> PLATINUM_GRAVEL_ORE);
            oreFeatures.add(() -> RUTHENIUM_GRAVEL_ORE);
            oreFeatures.add(() -> IRIDIUM_GRAVEL_ORE);
            oreFeatures.add(() -> ZIRCONIUM_GRAVEL_ORE);

            oreFeatures.add(() -> TUNGSTEN_OBSIDIAN_ORE);
            oreFeatures.add(() -> RHENIUM_OBSIDIAN_ORE);

            //meteorites
            if (category != Biome.BiomeCategory.NONE && category != Biome.BiomeCategory.RIVER && category != Biome.BiomeCategory.SWAMP && category != Biome.BiomeCategory.OCEAN && category != Biome.BiomeCategory.BEACH) {
                List<Supplier<ConfiguredFeature<?, ?>>> localModificationsFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.LOCAL_MODIFICATIONS);
                localModificationsFeatures.add(() -> METEORITE);
            }
        }
    }
}
