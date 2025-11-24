package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import com.padbro.greeterbro.client.managers.AfkManager;
import com.padbro.greeterbro.client.managers.TickManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

@Mixin(ClientPlayNetworkHandler.class)
public class OnGameJoinMixin {
    @Inject(at = @At("RETURN"), method = "onGameJoin")
    public void onReady(GameJoinS2CPacket packet, CallbackInfo ci) {
        GreeterBroClient.isJoining = true;
    }
}
