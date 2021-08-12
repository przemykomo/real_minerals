package xyz.przemyk.real_minerals.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockDeferredRegister {
    protected final DeferredRegister<Block> blockRegister;
    protected final DeferredRegister<Item> itemRegister;

    public final List<BlockRegistryObject> allBlocks = new ArrayList<>();

    public BlockDeferredRegister(String modid) {
        blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
    }

    public void register(IEventBus bus) {
        blockRegister.register(bus);
        itemRegister.register(bus);
    }

    public BlockRegistryObject register(final String name, final Supplier<Block> sup, CreativeModeTab itemGroup) {
        RegistryObject<Block> block = blockRegister.register(name, sup);
        RegistryObject<BlockItem> item = itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(itemGroup)));
        BlockRegistryObject blockRegistryObject = new BlockRegistryObject(block, item);
        allBlocks.add(blockRegistryObject);
        return blockRegistryObject;
    }
}
