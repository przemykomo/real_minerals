package xyz.przemyk.real_minerals.cables;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CableNetworksSavedData extends SavedData {
    public static final String NAME = RealMinerals.MODID + "_cable_networks";

    private final Map<String, CableNetwork> networks;
    private final ServerLevel world;

    public Map<String, CableNetwork> getNetworks() {
        return networks;
    }

    public CableNetworksSavedData(ServerLevel world) {
        this.networks = new HashMap<>();
        this.world = world;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public CableNetworksSavedData(CompoundTag nbt, ServerLevel level) {
        this.networks = new HashMap<>();
        this.world = level;

        MinecraftForge.EVENT_BUS.register(this);

        ListTag networksListNBT = nbt.getList("networks", Constants.NBT.TAG_COMPOUND);
        for (Tag networkNBT : networksListNBT) {
            CompoundTag networkCompound = (CompoundTag) networkNBT;
            CableNetwork network = new CableNetwork(this, world);
            network.deserializeNBT(networkCompound);
            networks.put(network.getID(), network);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag networksListNBT = new ListTag();
        networks.values().forEach(cableNetwork -> networksListNBT.add(cableNetwork.serializeNBT()));
        compound.put("networks", networksListNBT);
        return compound;
    }

    public static CableNetworksSavedData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(compoundTag -> new CableNetworksSavedData(compoundTag, world),
                () -> new CableNetworksSavedData(world), NAME);
    }

    public CableNetwork createNetwork() {
        String id = StringUtils.randomString(new Random(), 8);
        CableNetwork cableNetwork = new CableNetwork(id, this, world);
        networks.put(id, cableNetwork);
        setDirty();
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
