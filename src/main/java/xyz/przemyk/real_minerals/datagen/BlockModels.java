package xyz.przemyk.real_minerals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

public class BlockModels extends BlockModelProvider {

    public BlockModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS.allBlocks) {
            if (blockRegistryObject.BLOCK.get().hasTileEntity(blockRegistryObject.BLOCK.get().getDefaultState())) {
                continue;
            }
            String path = blockRegistryObject.BLOCK.get().getRegistryName().getPath();
            getBuilder(path)
                    .parent(new ModelFile.UncheckedModelFile("block/cube_all"))
                    .texture("all", modLoc("block/" + path));
        }
    }
}