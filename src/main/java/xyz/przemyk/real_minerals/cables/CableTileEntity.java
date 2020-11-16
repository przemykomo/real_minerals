package xyz.przemyk.real_minerals.cables;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class CableTileEntity extends TileEntity {

    private String networkID;

    public CableTileEntity() {
        super(Registering.CABLE_TILE_ENTITY_TYPE.get());
    }

    public CableTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public String getNetworkID() {
        return networkID;
    }

    public void setNetworkID(String networkID) {
        this.networkID = networkID;
        this.cachedEnergyStorage = LazyOptional.empty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("networkID", networkID);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        networkID = nbt.getString("networkID");
        super.read(state, nbt);
    }

    private LazyOptional<CableEnergyStorage> cachedEnergyStorage = LazyOptional.empty();

    protected LazyOptional<CableEnergyStorage> getCachedEnergyStorage() {
        if (cachedEnergyStorage.isPresent()) {
            return cachedEnergyStorage;
        }
        CableNetwork cableNetwork = CableNetworksWorldData.get((ServerWorld) world).getNetworks().get(networkID);
        if (cableNetwork == null) {
            System.out.println("Cable network is null!");
            return cachedEnergyStorage;
        }
        cachedEnergyStorage = cableNetwork.energyStorageLazyOptional;
        return cachedEnergyStorage;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY && !world.isRemote()) {
            return getCachedEnergyStorage().cast();
        }
        return super.getCapability(cap, side);
    }
}
