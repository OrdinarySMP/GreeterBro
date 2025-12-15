package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import com.padbro.greeterbro.client.managers.AfkManager;
import com.padbro.greeterbro.client.managers.TickManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(ClientPacketListener.class)
public class OnGameJoinMixin {
    @Inject(at = @At("RETURN"), method = "handleLogin")
    public void onReady(ClientboundLoginPacket packet, CallbackInfo ci) {
        GreeterBroClient.isJoining = true;
        GreeterBroConfig config = GreeterBroClient.getConfig();
        JoinCache joinCache = GreeterBroClient.getJoinCache();
        if (!config.generalConfig.enable) {
            return;
        }

        if (config.afkConfig.enable) {
            AfkManager.isAfk = false;
            AfkManager.setLastActiveNow(false);
        }

        if (joinCache.shouldClearOnJoin()) {
            GreeterBroClient.getJoinCache().clear();
        }

        if (config.generalConfig.enableOwnJoin) {
            List<String> greetingList = config.generalConfig.greetings;

            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            TickManager.scheduleTask(
                    new TickManager.ScheduledTask(
                            config.generalConfig.delayRange.getRandomDelayInTicks(),
                            () -> {
                                LocalPlayer currentPlayer = Minecraft.getInstance().player;
                                if (currentPlayer == null) {
                                    return;
                                }
                                Random rand = new Random();
                                String greetingTemplate = greetingList.get(rand.nextInt(greetingList.size()));
                                String greeting = greetingTemplate.replaceAll("\\s*%player%", "");
                                if (!greeting.isEmpty()) {
                                    currentPlayer.connection.sendChat(greeting);
                                }
                            },
                            player.getName().getString()));
        }
    }
}
