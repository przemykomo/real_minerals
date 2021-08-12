package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerStatesAndModels() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            if (blockRegistryObject.BLOCK.get() instanceof EntityBlock) {
                continue;
            }
            Block block = blockRegistryObject.BLOCK.get();
            getVariantBuilder(block)
                    .forAllStates(blockState -> ConfiguredModel.builder()
                    .modelFile(new ModelFile.UncheckedModelFile(modLoc("block/" + block.getRegistryName().getPath())))
                    .build());
        }
    }
}
