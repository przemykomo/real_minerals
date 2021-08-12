package xyz.przemyk.real_minerals.datagen.providers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.przemyk.real_minerals.init.Registering;

import javax.annotation.Nullable;

public class FluidTags extends FluidTagsProvider {

    public FluidTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    public static final Tag.Named<Fluid> BURNABLE_GAS = tag("burnable_gas");

    @Override
    protected void addTags() {
        tag(BURNABLE_GAS).add(Registering.SHALE_GAS_FLUID.get());
    }

    @SuppressWarnings("SameParameterValue")
    private static Tags.IOptionalNamedTag<Fluid> tag(String name) {
        return net.minecraft.tags.FluidTags.createOptional(new ResourceLocation("forge", name));
    }
}
