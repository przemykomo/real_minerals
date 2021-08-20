package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
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
            simpleBlock(blockRegistryObject.BLOCK.get());
        }

        horizontalBlock(MachinesRegistry.MAGNETIZER_BLOCK.BLOCK.get());

        machineBlock(MachinesRegistry.MIXER_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.OXIDIZER_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.MAGNETIC_SEPARATOR_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.GAS_SEPARATOR_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.GAS_GENERATOR_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.GAS_ENRICHER_BLOCK.BLOCK.get());
        machineBlock(MachinesRegistry.CHEMICAL_WASHER_BLOCK.BLOCK.get());
    }

    private void horizontalBlock(Block block) {
        horizontalBlock(block, new ModelFile.UncheckedModelFile(modLoc("block/" + block.getRegistryName().getPath())));
    }

    private void machineBlock(Block block) {
        String path = block.getRegistryName().getPath();
        horizontalBlock(block, models().getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile("block/orientable_with_bottom"))
                .texture("top", modLoc("block/magnetic_separator_top"))
                .texture("front", modLoc("block/" + path +"_front"))
                .texture("side", modLoc("block/magnetic_separator_side"))
                .texture("bottom", modLoc("block/bronze_machine_bottom")));
    }
}
