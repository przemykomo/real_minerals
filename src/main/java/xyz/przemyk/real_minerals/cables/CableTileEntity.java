package xyz.przemyk.real_minerals.cables;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class CableTileEntity extends BlockEntity {

    private String networkID;

    public CableTileEntity(BlockPos blockPos, BlockState blockState) {
        super(Registering.CABLE_TILE_ENTITY_TYPE.get(), blockPos, blockState);
    }

    public String getNetworkID() {
        return networkID;
    }

    public void setNetworkID(String networkID) {
        this.networkID = networkID;
        this.cachedEnergyStorage = LazyOptional.empty();
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putString("networkID", networkID);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag nbt) {
        networkID = nbt.getString("networkID");
        super.load(nbt);
    }

    private LazyOptional<CableEnergyStorage> cachedEnergyStorage = LazyOptional.empty();

    protected LazyOptional<CableEnergyStorage> getCachedEnergyStorage() {
        if (cachedEnergyStorage.isPresent()) {
            return cachedEnergyStorage;
        }
        CableNetwork cableNetwork = CableNetworksSavedData.get((ServerLevel) level).getNetworks().get(networkID);
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
        if (cap == CapabilityEnergy.ENERGY && !level.isClientSide()) {
            return getCachedEnergyStorage().cast();
        }
        return super.getCapability(cap, side);
    }
}
