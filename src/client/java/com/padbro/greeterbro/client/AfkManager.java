package com.padbro.greeterbro.client;

import com.padbro.greeterbro.client.config.AfkConfig;
import com.padbro.greeterbro.client.config.AfkNotifyType;
import com.padbro.greeterbro.client.config.GeneralConfig;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class AfkManager {
  private static final MinecraftClient minecraftInstance = MinecraftClient.getInstance();
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
    notifyPlayer(Text.translatable("text.message.GreeterBro.afk.enter_afk"));
    isAfk = true;
  }

  public static void leaveAfk() {
    notifyPlayer(Text.translatable("text.message.GreeterBro.afk.leave_afk"));
    isAfk = false;
  }

  private static void notifyPlayer(MutableText message) {
    if (afkConfig.notifyType == AfkNotifyType.Disabled) {
      return;
    }
    ClientPlayerEntity player = minecraftInstance.player;
    if (player != null) {
      Formatting format =
          afkConfig.notifyType == AfkNotifyType.Overlay ? Formatting.YELLOW : Formatting.GRAY;
      player.sendMessage(message.formatted(format), afkConfig.notifyType == AfkNotifyType.Overlay);
    }
  }

  private static boolean isPlayerActive() {
    Entity cameraEntity = minecraftInstance.getCameraEntity();
    if (cameraEntity != null
        && (previousPitch != cameraEntity.getPitch() || previousYaw != cameraEntity.getYaw())) {
      previousPitch = cameraEntity.getPitch();
      previousYaw = cameraEntity.getYaw();
      return true;
    }

    return minecraftInstance.options.forwardKey.isPressed()
        || minecraftInstance.options.backKey.isPressed()
        || minecraftInstance.options.rightKey.isPressed()
        || minecraftInstance.options.leftKey.isPressed()
        || minecraftInstance.options.sprintKey.isPressed()
        || minecraftInstance.options.jumpKey.isPressed()
        || minecraftInstance.options.sneakKey.isPressed()
        || minecraftInstance.options.useKey.isPressed()
        || minecraftInstance.options.attackKey.isPressed()
        || minecraftInstance.options.playerListKey.isPressed()
        || minecraftInstance.options.togglePerspectiveKey.isPressed();
  }
}
