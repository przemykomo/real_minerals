package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.fmllegacy.RegistryObject;

public class BlockRegistryObject {
    public final RegistryObject<Block> BLOCK;
    public final RegistryObject<BlockItem> ITEM;

    public BlockRegistryObject(RegistryObject<Block> block, RegistryObject<BlockItem> item) {
        BLOCK = block;
        ITEM = item;
    }
}
