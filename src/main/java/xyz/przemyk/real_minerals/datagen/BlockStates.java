package xyz.przemyk.real_minerals.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.Registering;
import xyz.przemyk.real_minerals.machines.BaseMachineBlock;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerStatesAndModels() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS.allBlocks) {
            if (blockRegistryObject.BLOCK.get() instanceof BaseMachineBlock) {
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
