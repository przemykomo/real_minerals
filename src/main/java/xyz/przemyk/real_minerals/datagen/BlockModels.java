package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.RealMinerals;

public class BlockModels extends BlockModelProvider {

    public BlockModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        for (BlockRegistryObject blockRegistryObject : RealMinerals.BLOCKS.allBlocks) {
            if (blockRegistryObject == RealMinerals.CRUSHER_BLOCK || blockRegistryObject == RealMinerals.ALLOY_FURNACE_BLOCK) {
                continue;
            }
            String path = blockRegistryObject.BLOCK.get().getRegistryName().getPath();
            getBuilder(path)
                    .parent(new ModelFile.UncheckedModelFile("block/cube_all"))
                    .texture("all", modLoc("block/" + path));
        }
    }
}