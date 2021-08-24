package xyz.przemyk.real_minerals.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import xyz.przemyk.real_minerals.blockentity.TankBlockEntity;
import xyz.przemyk.real_minerals.util.FluidUtils;

import javax.annotation.Nullable;

public class TankBlock extends Block implements EntityBlock {

    public TankBlock() {
        super(Properties.copy(Blocks.GLASS));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TankBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                ItemStack itemStack = player.getItemInHand(hand);
                Direction face = hit.getDirection();
                if (FluidUtils.tryExtractFluid(level, pos, player, hand, blockEntity, itemStack, face)) {
                    return InteractionResult.CONSUME;
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof TankBlockEntity tankBlockEntity) {
            FluidStack fluidStack = tankBlockEntity.fluidTank.getFluid();
            return fluidStack.getFluid().getAttributes().getLuminosity(fluidStack);
        }
        return super.getLightEmission(state, world, pos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null && compoundTag.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
            if (level.getBlockEntity(blockPos) instanceof TankBlockEntity tankBlockEntity) {
                tankBlockEntity.fluidTank.readFromNBT(compoundTag.getCompound("BlockEntityTag").getCompound("fluid_tank"));
            }
        }
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack itemStack =  super.getPickBlock(state, target, world, pos, player);
        if (world.getBlockEntity(pos) instanceof TankBlockEntity tankBlockEntity) {
            CompoundTag itemTag = itemStack.getOrCreateTag();
            CompoundTag blockEntityTag = new CompoundTag();
            blockEntityTag.put("fluid_tank", tankBlockEntity.fluidTank.writeToNBT(new CompoundTag()));
            itemTag.put("BlockEntityTag", blockEntityTag);
        }
        return itemStack;
    }
}
