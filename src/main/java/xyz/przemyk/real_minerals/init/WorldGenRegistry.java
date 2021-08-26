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
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.worldgen.MeteoriteFeature;
import xyz.przemyk.real_minerals.worldgen.SulfurFeature;
import xyz.przemyk.real_minerals.worldgen.UnderLavaDecorator;

import java.util.List;
import java.util.function.Supplier;

import static xyz.przemyk.real_minerals.RealMinerals.MODID;

public class WorldGenRegistry {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MODID);
    public static final DeferredRegister<FeatureDecorator<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, MODID);

    public static final RuleTest GRAVEL = new BlockMatchTest(Blocks.GRAVEL);
    public static final RuleTest OBSIDIAN = new BlockMatchTest(Blocks.OBSIDIAN);

    public static final RegistryObject<MeteoriteFeature> METEORITE_FEATURE = FEATURES.register("meteorite", () -> new MeteoriteFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<SulfurFeature> SULFUR_FEATURE = FEATURES.register("sulfur", () -> new SulfurFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<UnderLavaDecorator> UNDER_LAVA_DECORATOR = DECORATORS.register("under_lava", () -> new UnderLavaDecorator(NoneDecoratorConfiguration.CODEC));

    //TODO: change rarity and height of ore generation
    public static ConfiguredFeature<?, ?> LEAD_ORE;
    public static ConfiguredFeature<?, ?> MAGNESIUM_ORE;
    public static ConfiguredFeature<?, ?> NICKEL_ORE;
    public static ConfiguredFeature<?, ?> PLATINUM_ORE;
    public static ConfiguredFeature<?, ?> SILVER_ORE;
    public static ConfiguredFeature<?, ?> TIN_ORE;
    public static ConfiguredFeature<?, ?> ALUMINUM_ORE;
    public static ConfiguredFeature<?, ?> ZINC_ORE;
    public static ConfiguredFeature<?, ?> MAGNETITE_ORE;
    public static ConfiguredFeature<?, ?> SHALE_GAS_ORE;

    public static ConfiguredFeature<?, ?> GOLD_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> RUTHENIUM_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> IRIDIUM_GRAVEL_ORE;
    public static ConfiguredFeature<?, ?> ZIRCONIUM_GRAVEL_ORE;

    public static ConfiguredFeature<?, ?> TUNGSTEN_OBSIDIAN_ORE;
    public static ConfiguredFeature<?, ?> RHENIUM_OBSIDIAN_ORE;

    public static ConfiguredFeature<?, ?> METEORITE;
    public static ConfiguredFeature<?, ?> SULFUR;

    public static void init(IEventBus eventBus) {
        eventBus.addListener(WorldGenRegistry::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(WorldGenRegistry::addFeatures);
        FEATURES.register(eventBus);
        DECORATORS.register(eventBus);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_lead"), LEAD_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.LEAD_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_magnesium"), MAGNESIUM_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.MAGNESIUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_nickel"), NICKEL_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.NICKEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_silver"), SILVER_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.SILVER_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_tin"), TIN_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.TIN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_aluminum"), ALUMINUM_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.ALUMINUM_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_zinc"), ZINC_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.ZINC_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_magnetite"), MAGNETITE_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, StoneMinerals.MAGNETITE_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_shale_gas"), SHALE_GAS_ORE = Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, Registering.SHALE_GAS_STONE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_gold"), GOLD_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.GOLD_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_ruthenium"), RUTHENIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.RUTHENIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_iridium"), IRIDIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.IRIDIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_gravel_zirconium"), ZIRCONIUM_GRAVEL_ORE = Feature.ORE.configured(new OreConfiguration(GRAVEL, GravelMinerals.ZIRCONIUM_GRAVEL_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_obsidian_tungsten"), TUNGSTEN_OBSIDIAN_ORE = Feature.ORE.configured(new OreConfiguration(OBSIDIAN, ObsidianMinerals.TUNGSTEN_OBSIDIAN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(20)).squared().count(20));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "ore_obsidian_rhenium"), RHENIUM_OBSIDIAN_ORE = Feature.ORE.configured(new OreConfiguration(OBSIDIAN, ObsidianMinerals.RHENIUM_OBSIDIAN_ORE.BLOCK.get().defaultBlockState(), 9)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(20)).squared().count(20));

            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "meteorite"), METEORITE = METEORITE_FEATURE.get().configured(NoneFeatureConfiguration.INSTANCE));
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(RealMinerals.MODID, "sulfur"), SULFUR = SULFUR_FEATURE.get().configured(NoneFeatureConfiguration.INSTANCE).decorated(UNDER_LAVA_DECORATOR.get().configured(NoneDecoratorConfiguration.INSTANCE)));
        });
    }

    public static void addFeatures(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            List<Supplier<ConfiguredFeature<?, ?>>> oreFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

            oreFeatures.add(() -> LEAD_ORE);
            oreFeatures.add(() -> MAGNESIUM_ORE);
            oreFeatures.add(() -> NICKEL_ORE);
            oreFeatures.add(() -> PLATINUM_ORE);
            oreFeatures.add(() -> SILVER_ORE);
            oreFeatures.add(() -> TIN_ORE);
            oreFeatures.add(() -> ALUMINUM_ORE);
            oreFeatures.add(() -> ZINC_ORE);
            oreFeatures.add(() -> MAGNETITE_ORE);
            oreFeatures.add(() -> SHALE_GAS_ORE);

            oreFeatures.add(() -> GOLD_GRAVEL_ORE);
            oreFeatures.add(() -> RUTHENIUM_GRAVEL_ORE);
            oreFeatures.add(() -> IRIDIUM_GRAVEL_ORE);
            oreFeatures.add(() -> ZIRCONIUM_GRAVEL_ORE);

            oreFeatures.add(() -> TUNGSTEN_OBSIDIAN_ORE);
            oreFeatures.add(() -> RHENIUM_OBSIDIAN_ORE);

            oreFeatures.add(() -> SULFUR);

            //meteorites
            if (category != Biome.BiomeCategory.NONE && category != Biome.BiomeCategory.RIVER && category != Biome.BiomeCategory.SWAMP && category != Biome.BiomeCategory.OCEAN && category != Biome.BiomeCategory.BEACH) {
                List<Supplier<ConfiguredFeature<?, ?>>> localModificationsFeatures = event.getGeneration().getFeatures(GenerationStep.Decoration.LOCAL_MODIFICATIONS);
                localModificationsFeatures.add(() -> METEORITE);
            }
        }
    }
}
