package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;
import xyz.przemyk.real_minerals.init.BlockRegistryObject;
import xyz.przemyk.real_minerals.init.MachinesRegistry;
import xyz.przemyk.real_minerals.init.Registering;

public class ItemModels extends ItemModelProvider {

    public ItemModels(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        for (BlockRegistryObject blockRegistryObject : Registering.BLOCKS_ITEMS.allBlocks) {
            if (blockRegistryObject == MachinesRegistry.BATTERY_BLOCK || blockRegistryObject == MachinesRegistry.CABLE_BLOCK) {
                continue;
            }
            String path = blockRegistryObject.ITEM.get().getRegistryName().getPath();
            getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
        }

        for (RegistryObject<Item> itemRegistryObject : Registering.ITEMS.getEntries()) {
            String path = itemRegistryObject.get().getRegistryName().getPath();
            getBuilder(path).parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", modLoc("item/" + path));
        }
    }
}
