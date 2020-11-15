package xyz.przemyk.real_minerals.cables;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import xyz.przemyk.real_minerals.init.RealMinerals;
import xyz.przemyk.real_minerals.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CableNetworksWorldData extends WorldSavedData {
    public static final String NAME = RealMinerals.MODID + "_cable_networks";

    private final Map<String, CableNetwork> networks;
    private final World world;

    public Map<String, CableNetwork> getNetworks() {
//        markDirty(); //TODO: mark dirty only on change
        return networks;
    }

    public CableNetworksWorldData(World world) {
        super(NAME);
        this.networks = new HashMap<>();
        this.world = world;
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT networksListNBT = nbt.getList("networks", Constants.NBT.TAG_COMPOUND);
        for (INBT networkNBT : networksListNBT) {
            CompoundNBT networkCompound = (CompoundNBT) networkNBT;
            CableNetwork network = new CableNetwork();
            network.deserializeNBT(networkCompound);
            networks.put(network.getID(), network);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT networksListNBT = new ListNBT();
        networks.values().forEach(cableNetwork -> {
            networksListNBT.add(cableNetwork.serializeNBT());
        });
        compound.put("networks", networksListNBT);
        return compound;
    }

    public static CableNetworksWorldData get(ServerWorld world) {
        return world.getSavedData().getOrCreate(() -> new CableNetworksWorldData(world), NAME);
    }

    public CableNetwork createNetwork() {
        String id = StringUtils.randomString(new Random(), 8);
        CableNetwork cableNetwork = new CableNetwork(id);
        networks.put(id, cableNetwork);
//        markDirty();
        return cableNetwork;
    }
}
