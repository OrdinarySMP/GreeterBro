package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.AfkManager;
import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.TickManager;
import com.padbro.greeterbro.client.TickManager.ScheduledTask;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import com.padbro.greeterbro.client.config.SpecialGreetingsConfig.SpecialGreeting;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageHandler.class)
public class ChatMixin {

  @Inject(method = "onGameMessage", at = @At("HEAD"))
  public void onMessage(Text message, boolean overlay, CallbackInfo ci) {
    GreeterBroConfig config = GreeterBroClient.getConfig();
    if (!config.generalConfig.enable
        || MinecraftClient.getInstance().player == null
        || (config.afkConfig.enable && AfkManager.isAfk)) {

      return;
    }

    List<String> greetingList;
    String player;
    int chance;
    String currentPlayerName = MinecraftClient.getInstance().player.getName().getString();

    if (this.isFirstJoin(message)) {
      greetingList = config.firstJoinConfig.greetings;
      player = getPlayerName(message, config.firstJoinConfig.customMessage);
      chance = config.firstJoinConfig.greetingChance;
    } else if (this.isNameChange(message)) {
      greetingList = config.nameChangeConfig.greetings;
      player = getPlayerName(message, config.nameChangeConfig.customMessage);
      chance = config.nameChangeConfig.greetingChance;
    } else if (this.isJoinMessage(message)) {
      greetingList = config.generalConfig.greetings;
      player = getPlayerName(message, config.generalConfig.customMessage);
      chance = config.generalConfig.greetingChance;
    } else if (this.isLeaveMessage(message) && config.generalConfig.cancelOnLeave) {
      player = getPlayerName(message, config.generalConfig.customLeaveMessage);
      TickManager.cancelTaskByPlayerName(player);
      return;
    } else {
      return;
    }

    if (player != null) {
      if (config.blacklistConfig.players.contains(player)) {
        return;
      }

      if (!config.generalConfig.enableOwnJoin && player.equals(currentPlayerName)) {
        return;
      }

      Optional<SpecialGreeting> specialGreeting = config.specialGreetings.getForPlayer(player);
      if (specialGreeting.isPresent()) {
        greetingList = specialGreeting.get().greetings;
        chance = specialGreeting.get().greetingChance;
      }

      JoinCache joinCache = GreeterBroClient.getJoinCache();

      if (config.returningPlayerConfig.enable) {
        if (joinCache.hasRecentlyJoined(player)) {
          return;
        }
        if (joinCache.hasJoined(player) && !player.equals(currentPlayerName)) {
          if (specialGreeting.isPresent()) {
            greetingList = specialGreeting.get().returningGreetings;
            chance = specialGreeting.get().greetingChance;
          } else {
            greetingList = config.returningPlayerConfig.greetings;
            chance = config.returningPlayerConfig.greetingChance;
          }
        }
      }

      joinCache.add(player);
    }

    if (Math.random() > (double) chance / 100) {
      return;
    }

    TickManager.cancelTaskByPlayerName(player);

    List<String> finalGreetingList = greetingList;
    TickManager.scheduleTask(
        new ScheduledTask(
            config.generalConfig.delayRange.getRandomDelay(),
            () -> {
              ClientPlayerEntity currentPlayer = MinecraftClient.getInstance().player;
              if (currentPlayer == null) {
                return;
              }
              Random rand = new Random();
              String greetingTemplate =
                  finalGreetingList.get(rand.nextInt(finalGreetingList.size()));
              String greeting = greetingTemplate.replace("%player%", player != null ? player : "");
              if (!greeting.isEmpty()) {
                currentPlayer.networkHandler.sendChatMessage(greeting);
              }
            },
            player));
  }

  @Unique
  private String getPlayerName(Text message, @Nullable String customMessage) {
    // 1. Try Translatable content (vanilla join/rename messages)
    if (message.getContent() instanceof TranslatableTextContent translatable) {
      if (translatable.getArgs().length > 0) {
        return translatable.getArg(0).getString();
      }
    }

    for (Text sibling : message.getSiblings()) {
      if (sibling.getContent() instanceof TranslatableTextContent translatable) {
        if (translatable.getArgs().length > 0) {
          return translatable.getArg(0).getString();
        }
      }
    }

    String flatMessage = message.getString();
    if (customMessage == null || customMessage.isEmpty()) {
      return null;
    }

    Pattern pattern = Pattern.compile(customMessage);
    var matcher = pattern.matcher(flatMessage);

    if (matcher.matches() && matcher.groupCount() >= 1) {
      return matcher.group(1);
    }

    return null; // Couldn't extract player name
  }

  @Unique
  private boolean isJoinMessage(Text message) {
    String joinMessageKey = "multiplayer.player.joined";
    return this.hasKey(message, joinMessageKey)
        || this.hasContent(message, GreeterBroClient.getConfig().generalConfig.customMessage);
  }

  @Unique
  private boolean isFirstJoin(Text message) {
    if (!GreeterBroClient.getConfig().firstJoinConfig.enable) {
      return false;
    }
    return this.hasContent(message, GreeterBroClient.getConfig().firstJoinConfig.customMessage);
  }

  @Unique
  private boolean isNameChange(Text message) {
    if (!GreeterBroClient.getConfig().nameChangeConfig.enable) {
      return false;
    }
    String nameChangeKey = "multiplayer.player.joined.renamed";
    return this.hasKey(message, nameChangeKey)
        || this.hasContent(message, GreeterBroClient.getConfig().nameChangeConfig.customMessage);
  }

  @Unique
  private boolean hasKey(Text message, String key) {
    String vanishKey = "text.vanish.chat.hidden";
    // Vanilla join message
    if (message.getContent() instanceof TranslatableTextContent translatable) {
      String messageKey = translatable.getKey();
      return Objects.equals(key, messageKey);
    }

    // StyledChat modified default message
    boolean hasKey = false;
    for (Text sibling : message.getSiblings()) {
      if (sibling.getContent() instanceof TranslatableTextContent translatable) {
        String messageKey = translatable.getKey();

        // ignore if user is in vanish
        if (Objects.equals(messageKey, vanishKey)) {
          return false;
        }
        if (Objects.equals(messageKey, key)) {
          hasKey = true;
        }
      }
    }

    return hasKey;
  }

  @Unique
  private boolean isLeaveMessage(Text message) {
    String leaveMessageKey = "multiplayer.player.left";
    return this.hasKey(message, leaveMessageKey)
        || this.hasContent(message, GreeterBroClient.getConfig().generalConfig.customLeaveMessage);
  }

  @Unique
  private boolean hasContent(Text message, String content) {
    String messageString = message.getString().trim();
    if (Objects.equals(content, "") || Objects.equals(messageString, "")) {
      return false;
    }
    Pattern pattern = Pattern.compile(content);
    return pattern.matcher(messageString).matches();
  }
}
