package xyz.przemyk.real_minerals.datapack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.przemyk.real_minerals.RealMinerals;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GasBurningReloadListener extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogManager.getLogger();
    public static Map<Fluid, GasBurningEntry> gasBurningEntries = new HashMap<>();

    public GasBurningReloadListener() {
        super(GSON, RealMinerals.MODID + "/gas_burning");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        gasBurningEntries = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            try {
                JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
                FluidStack fluidStack = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()))), jsonObject.get("amount").getAsInt());
                if (fluidStack.isEmpty()) {
                    throw new IllegalStateException("Fluid can't be empty!");
                }
                int energyPerTick = jsonObject.get("energy_per_tick").getAsInt();
                int burnTime = jsonObject.get("burn_time").getAsInt();
                gasBurningEntries.put(fluidStack.getFluid(), new GasBurningEntry(fluidStack, energyPerTick, burnTime));
            } catch (Exception e) {
                LOGGER.error("Parsing error loading recipe {}", entry.getKey(), e);
            }
        }
    }

}
