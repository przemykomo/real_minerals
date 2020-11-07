package xyz.przemyk.real_minerals.init;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraftforge.fml.RegistryObject;

public class BlockRegistryObject {
    public final RegistryObject<Block> BLOCK;
    public final RegistryObject<BlockItem> ITEM;

    public BlockRegistryObject(RegistryObject<Block> block, RegistryObject<BlockItem> item) {
        BLOCK = block;
        ITEM = item;
    }
}
