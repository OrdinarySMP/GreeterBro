package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.TickManager;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(ClientPlayNetworkHandler.class)
public class onGameJoinMixin {
    @Inject(at = @At("RETURN"), method = "onGameJoin")
    public void onReady(GameJoinS2CPacket packet, CallbackInfo ci) {
        JoinCache.clear();
        GreeterBroConfig config = GreeterBroClient.config.get();
        if (config.generalConfig.enableOwnJoin) {
            List<String> greetingList = config.generalConfig.greetings;

            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) {
                return;
            }
            TickManager.scheduleTask(
                    new TickManager.ScheduledTask(
                            config.generalConfig.delayRange.getRandomDelay(),
                            () -> {
                                Random rand = new Random();
                                String greeting = greetingList.get(rand.nextInt(greetingList.size()));
                                player.networkHandler.sendChatMessage(greeting);
                            }));

        }

    }
}
