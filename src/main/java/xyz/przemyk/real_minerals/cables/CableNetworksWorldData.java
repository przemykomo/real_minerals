package xyz.przemyk.real_minerals.cables;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
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

    public Map<String, CableNetwork> getNetworks() {
        return networks;
    }

    public CableNetworksWorldData() {
        super(NAME);
        this.networks = new HashMap<>();
    }

    @Override
    public void read(CompoundNBT nbt) {
        ListNBT networksListNBT = nbt.getList("networks", Constants.NBT.TAG_COMPOUND);
        for (INBT networkNBT : networksListNBT) {
            CompoundNBT networkCompound = (CompoundNBT) networkNBT;
            CableNetwork network = new CableNetwork(this);
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
        return world.getSavedData().getOrCreate(CableNetworksWorldData::new, NAME);
    }

    public CableNetwork createNetwork() {
        String id = StringUtils.randomString(new Random(), 8);
        CableNetwork cableNetwork = new CableNetwork(id, this);
        networks.put(id, cableNetwork);
        markDirty();
        return cableNetwork;
    }
}
