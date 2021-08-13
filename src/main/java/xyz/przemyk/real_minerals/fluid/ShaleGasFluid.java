package xyz.przemyk.real_minerals.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.fluids.FluidAttributes;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.init.Registering;

public class ShaleGasFluid extends Fluid {

    @Override
    public Item getBucket() {
        return Registering.SHALE_GAS_BUCKET.get();
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return true;
    }

    @Override
    protected Vec3 getFlow(BlockGetter blockReader, BlockPos pos, FluidState fluidState) {
        return Vec3.ZERO;
    }

    @Override
    public int getTickDelay(LevelReader p_205569_1_) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    public float getHeight(FluidState p_215662_1_, BlockGetter p_215662_2_, BlockPos p_215662_3_) {
        return 1;
    }

    @Override
    public float getOwnHeight(FluidState p_223407_1_) {
        return 1;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState state) {
        return true;
    }

    @Override
    public int getAmount(FluidState state) {
        return 8;
    }

    @Override
    public VoxelShape getShape(FluidState p_215664_1_, BlockGetter p_215664_2_, BlockPos p_215664_3_) {
        return Shapes.empty();
    }

    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(new ResourceLocation(RealMinerals.MODID, "block/shale_gas_still"), null)
                .translationKey(RealMinerals.MODID + ".shale_gas")
                .viscosity(100)
                .gaseous()
                .rarity(Rarity.UNCOMMON)
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
                .build(this);
    }
}
