package xyz.przemyk.real_minerals.init;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemDeferredRegister {
    protected final DeferredRegister<Item> itemRegister;

    public final List<RegistryObject<Item>> allItems = new ArrayList<>();

    public ItemDeferredRegister(String modid) {
        itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
    }

    public void register(IEventBus bus) {
        itemRegister.register(bus);
    }

    public RegistryObject<Item> register(final String name, final Supplier<Item> sup) {
        RegistryObject<Item> item = itemRegister.register(name, sup);
        allItems.add(item);
        return item;
    }
}
