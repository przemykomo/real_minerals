package xyz.przemyk.real_minerals.blocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import xyz.przemyk.real_minerals.RealMinerals;
import xyz.przemyk.real_minerals.network.RealMineralsNetworking;
import xyz.przemyk.real_minerals.network.RickrollPacket;

import java.util.List;

public class RickrollBlock extends Block{
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
    public RickrollBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            boolean flag = pState.getValue(LIT);
            if (flag != pLevel.hasNeighborSignal(pPos)) {
                pLevel.setBlock(pPos, pState.cycle(LIT), 2);
                if (pLevel.hasNeighborSignal(pPos)) {
                    List<ServerPlayer> playerList = pLevel.getEntitiesOfClass(ServerPlayer.class, new AABB(pPos.getX()-3, pPos.getY()-3, pPos.getZ()-3,pPos.getX()+3, pPos.getY()+3, pPos.getZ()+3));
                    for (ServerPlayer player: playerList) {
                        RealMineralsNetworking.INSTANCE.send(PacketDistributor.PLAYER.with(()->player),new RickrollPacket());
                    }
                }
            }
        }
    }
}
