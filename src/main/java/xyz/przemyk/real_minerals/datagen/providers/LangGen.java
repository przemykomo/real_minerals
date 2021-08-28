package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.compat.jei.*;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LangGen extends LanguageProvider {

    public LangGen(DataGenerator gen) {
        super(gen, RealMinerals.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            Block block = blockRegistryObject.BLOCK.get();
            add(block, fixCapitalization(block.getRegistryName().getPath()));
        }

        for (RegistryObject<Block> blockRegistryObject : Registering.BLOCKS.getEntries()) {
            Block block = blockRegistryObject.get();
            add(block, fixCapitalization(block.getRegistryName().getPath()));
        }

        for (RegistryObject<Item> itemRegistryObject : Registering.ITEMS.getEntries()) {
            Item item = itemRegistryObject.get();
            if (!(item instanceof BlockItem)) {
                add(item, fixCapitalization(item.getRegistryName().getPath()));
            }
        }

        for (RegistryObject<Fluid> fluidRegistryObject : Registering.FLUIDS.getEntries()) {
            Fluid fluid = fluidRegistryObject.get();
            add(fluid.getAttributes().getTranslationKey(), fixCapitalization(fluid.getRegistryName().getPath()));
        }

        add("itemGroup.real_minerals", fixCapitalization(RealMinerals.MODID));
        add("real_minerals.gui.energy", "Energy: %s FE");
        add("real_minerals.gui.fluid", "%s: %s mb");
        add("real_minerals.gui.fluid.dissolved", "Dissolved: %s");
        add("real_minerals.empty", "Empty");
        add("real_minerals.active", "Active");

        add(AlloyRecipeCategory.TITLE.getKey(), "Alloying");
        add(ChemicalWasherCategory.TITLE.getKey(), "Chemical Washing");
        add(CrusherRecipeCategory.TITLE.getKey(), "Crushing");
        add(GasEnricherCategory.TITLE.getKey(), "Gas Enriching");
        add(GasSeparatorCategory.TITLE.getKey(), "Gas Separating");
        add(MagneticSeparatorRecipeCategory.TITLE.getKey(), "Magnetic Separating");
        add(MagnetizerRecipeCategory.TITLE.getKey(), "Magnetizing");
        add(MixerCategory.TITLE.getKey(), "Mixing");
        add(OxidizerCategory.TITLE.getKey(), "Oxidizing");
    }

    public String fixCapitalization(String text) {
        String original = text.trim().replace("    ", "").replace("_", " ").replace("/", ".");
        return Arrays.stream(original.split("\\s+"))
                .map(t -> t.substring(0,1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));
    }

}
