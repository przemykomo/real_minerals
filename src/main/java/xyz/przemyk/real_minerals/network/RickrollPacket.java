package xyz.przemyk.real_minerals.network;

import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class RickrollPacket {

    public RickrollPacket() {}
    public  RickrollPacket(FriendlyByteBuf buf) {}
    public void toBytes(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context context = ctxSup.get();
        context.enqueueWork(() -> {
            Util.getPlatform().openUri("https://shattereddisk.github.io/rickroll/rickroll.mp4");
        });
        context.setPacketHandled(true);
    }
}