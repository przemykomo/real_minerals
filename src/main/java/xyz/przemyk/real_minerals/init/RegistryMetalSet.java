package xyz.przemyk.real_minerals.init;

import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;

public class RegistryMetalSet {
    public final RegistryObject<Item> DUST;
    public final RegistryObject<Item> INGOT;
    public final RegistryObject<Item> NUGGET;

    public RegistryMetalSet(String name) {
        this.DUST = Registering.simpleItem(name + "_dust");
        this.INGOT = Registering.simpleItem(name + "_ingot");
        this.NUGGET = Registering.simpleItem(name + "_nugget");
    }
}
