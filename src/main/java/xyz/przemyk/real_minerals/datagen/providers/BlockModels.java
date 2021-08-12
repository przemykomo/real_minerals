package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;

public class BlockModels extends BlockModelProvider {

    public BlockModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            if (blockRegistryObject.BLOCK.get() instanceof EntityBlock) {
                continue;
            }
            String path = blockRegistryObject.BLOCK.get().getRegistryName().getPath();
            getBuilder(path)
                    .parent(new ModelFile.UncheckedModelFile("block/cube_all"))
                    .texture("all", modLoc("block/" + path));
        }
    }
}