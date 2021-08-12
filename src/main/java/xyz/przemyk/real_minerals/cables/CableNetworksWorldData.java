package xyz.przemyk.real_minerals.cables;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CableNetworksWorldData extends WorldSavedData {
    public static final String NAME = RealMinerals.MODID + "_cable_networks";

    private final Map<String, CableNetwork> networks;
    private final ServerWorld world;

    public Map<String, CableNetwork> getNetworks() {
        return networks;
    }

    public CableNetworksWorldData(ServerWorld world) {
        super(NAME);
        this.networks = new HashMap<>();
        this.world = world;

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT networksListNBT = nbt.getList("networks", Constants.NBT.TAG_COMPOUND);
        for (INBT networkNBT : networksListNBT) {
            CompoundNBT networkCompound = (CompoundNBT) networkNBT;
            CableNetwork network = new CableNetwork(this, world);
            network.deserializeNBT(networkCompound);
            networks.put(network.getID(), network);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT networksListNBT = new ListNBT();
        networks.values().forEach(cableNetwork -> networksListNBT.add(cableNetwork.serializeNBT()));
        compound.put("networks", networksListNBT);
        return compound;
    }

    public static CableNetworksWorldData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(() -> new CableNetworksWorldData(world), NAME);
    }

    public CableNetwork createNetwork() {
        String id = StringUtils.randomString(new Random(), 8);
        CableNetwork cableNetwork = new CableNetwork(id, this, world);
        networks.put(id, cableNetwork);
        markDirty();
        return cableNetwork;
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (CableNetwork network : networks.values()) {
                network.tick();
            }
        }
    }
}
