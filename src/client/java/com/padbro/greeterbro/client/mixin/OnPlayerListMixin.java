package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientPacketListener.class)
public class OnPlayerListMixin {
    @Inject(method = "handlePlayerInfoUpdate", at = @At("TAIL"))
    private void onPlayerList(ClientboundPlayerInfoUpdatePacket packet, CallbackInfo ci) {
        if (!GreeterBroClient.isJoining) {
            return;
        }

        GreeterBroConfig config = GreeterBroClient.getConfig();
        if (!config.returningPlayerConfig.cacheOnJoin) {
            return;
        }

        List<ClientboundPlayerInfoUpdatePacket.Entry> players = packet.newEntries();
        JoinCache joinCache = GreeterBroClient.getJoinCache();
        for (ClientboundPlayerInfoUpdatePacket.Entry entry : players) {
            if (entry.profile() != null) {
                joinCache.add(entry.profile().name());
            }
        }
        GreeterBroClient.isJoining = false;
    }
}
