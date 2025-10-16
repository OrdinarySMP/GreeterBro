package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import java.util.List;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class OnPlayerListMixin {
  @Inject(method = "onPlayerList", at = @At("TAIL"))
  private void onPlayerList(PlayerListS2CPacket packet, CallbackInfo ci) {
    if (!GreeterBroClient.isJoining) {
      return;
    }

    GreeterBroConfig config = GreeterBroClient.getConfig();
    if (!config.returningPlayerConfig.cacheOnJoin) {
      return;
    }

    List<PlayerListS2CPacket.Entry> players = packet.getPlayerAdditionEntries();
    JoinCache joinCache = GreeterBroClient.getJoinCache();
    for (PlayerListS2CPacket.Entry entry : players) {
      if (entry.profile() != null) {
        joinCache.add(entry.profile().name());
      }
    }
    GreeterBroClient.isJoining = false;
  }
}
