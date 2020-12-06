package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nullable;

public class FluidTags extends FluidTagsProvider {

    public FluidTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    public static final ITag.INamedTag<Fluid> BURNABLE_GAS = tag("burnable_gas");

    @Override
    protected void registerTags() {
        getOrCreateBuilder(BURNABLE_GAS).add(Registering.SHALE_GAS_FLUID.get());
    }

    @SuppressWarnings("SameParameterValue")
    private static Tags.IOptionalNamedTag<Fluid> tag(String name) {
        return net.minecraft.tags.FluidTags.createOptional(new ResourceLocation("forge", name));
    }
}
