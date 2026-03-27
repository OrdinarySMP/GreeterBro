package com.padbro.greeterbro.client.managers;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.config.AfkConfig;
import com.padbro.greeterbro.client.config.AfkNotifyType;
import com.padbro.greeterbro.client.config.GeneralConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class AfkManager {
    private static final Minecraft minecraftInstance = Minecraft.getInstance();
    public static boolean isAfk = false;
    static GeneralConfig config = GreeterBroClient.getConfig().generalConfig;
    static AfkConfig afkConfig = GreeterBroClient.getConfig().afkConfig;
    private static double previousPitch;
    private static double previousYaw;
    private static Instant lastActiveAt = Instant.now();

    public static void onTick() {
        if (!config.enable || !afkConfig.enable) {
            return;
        }

        if (isPlayerActive()) {
            setLastActiveNow(true);
            return;
        }

        if (isAfk) {
            return;
        }

        boolean isBefore =
                lastActiveAt.isBefore(Instant.now().minus(afkConfig.afkTime, ChronoUnit.MINUTES));

        if (isBefore) {
            goAfk();
        }
    }

    public static void setLastActiveNow(boolean notify) {
        lastActiveAt = Instant.now();
        if (isAfk && notify) {
            leaveAfk();
        }
    }

    public static void goAfk() {
        notifyPlayer(Component.translatable("text.message.GreeterBro.afk.enter_afk"));
        isAfk = true;
    }

    public static void leaveAfk() {
        notifyPlayer(Component.translatable("text.message.GreeterBro.afk.leave_afk"));
        isAfk = false;
    }

    private static void notifyPlayer(MutableComponent message) {
        if (afkConfig.notifyType == AfkNotifyType.Disabled) {
            return;
        }
        LocalPlayer player = minecraftInstance.player;
        if (player != null) {
            if (afkConfig.notifyType == AfkNotifyType.Overlay) {
                player.sendOverlayMessage(message.withStyle(ChatFormatting.YELLOW));
            } else {
                player.sendSystemMessage(message.withStyle(ChatFormatting.GRAY));
            }
        }
    }

    private static boolean isPlayerActive() {
        Entity cameraEntity = minecraftInstance.getCameraEntity();
        if (cameraEntity != null
                && (previousPitch != cameraEntity.getXRot() || previousYaw != cameraEntity.getYRot())) {
            previousPitch = cameraEntity.getXRot();
            previousYaw = cameraEntity.getYRot();
            return true;
        }

        return minecraftInstance.options.keyUp.isDown()
                || minecraftInstance.options.keyDown.isDown()
                || minecraftInstance.options.keyRight.isDown()
                || minecraftInstance.options.keyLeft.isDown()
                || minecraftInstance.options.keySprint.isDown()
                || minecraftInstance.options.keyJump.isDown()
                || minecraftInstance.options.keyShift.isDown()
                || minecraftInstance.options.keyUse.isDown()
                || minecraftInstance.options.keyAttack.isDown()
                || minecraftInstance.options.keyPlayerList.isDown()
                || minecraftInstance.options.keyTogglePerspective.isDown();
    }
}
