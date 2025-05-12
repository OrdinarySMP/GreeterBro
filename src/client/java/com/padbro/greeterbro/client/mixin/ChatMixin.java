package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.TickManager.ScheduledTask;
import com.padbro.greeterbro.client.TickManager;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Mixin(MessageHandler.class)

public class ChatMixin {
    @Unique
    private final GreeterBroConfig config = GreeterBroClient.config.get();

  @Inject(method = "onGameMessage", at = @At("HEAD"))
  public void onMessage(Text message, boolean overlay, CallbackInfo ci) {
        if (!this.config.generalConfig.enable) {
            return;
        }
        if (MinecraftClient.getInstance().player == null) {
            return;
        }

        List<String> greetingList = this.getGreetingList(message);
        if (greetingList == null || greetingList.isEmpty()) {
            return;
        }

        TickManager.scheduleTask(new ScheduledTask(this.config.generalConfig.delayRange.getRandomDelay(), () -> {
            Random rand = new Random();
            String greeting = greetingList.get(rand.nextInt(greetingList.size()));
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage(greeting);
        }));
    }

    @Unique
    private List<String> getGreetingList (Text message) {
        if (this.isFirstJoin(message)) {
            return this.config.firstJoinConfig.greetings;
        } else if (this.isNameChange(message)) {
            return this.config.nameChangeConfig.greetings;
        } else if (this.isJoinMessage(message)) {
            return this.config.generalConfig.greetings;
        }
        return List.of();
    }

    @Unique
    private boolean isJoinMessage(Text message) {
        String joinMessageKey = "multiplayer.player.joined";
        return this.hasKey(message, joinMessageKey) || hasContent(message, this.config.generalConfig.customMessage);
    }

    @Unique
    private boolean isFirstJoin(Text message) {
        if (!this.config.firstJoinConfig.enable) {
            return false;
        }
        return hasContent(message, this.config.firstJoinConfig.customMessage);
    }

    @Unique
    private boolean isNameChange(Text message) {
        if (!this.config.nameChangeConfig.enable) {
            return false;
        }
        String nameChangeKey = "multiplayer.player.joined.renamed";
        return this.hasKey(message, nameChangeKey) || hasContent(message, this.config.nameChangeConfig.customMessage);
    }

    @Unique
    private boolean hasKey(Text message, String key) {
        // Vanilla join message
        if (message.getContent() instanceof TranslatableTextContent translatable) {
            String messagekey = translatable.getKey();
            return Objects.equals(key, messagekey);
        }

        // StyledChat modified default message
        for (Text sibling : message.getSiblings()) {
            if (sibling.getContent() instanceof TranslatableTextContent translatable) {
                String messagekey = translatable.getKey();
                if (Objects.equals(messagekey, key)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Unique
    private boolean hasContent(Text message, String content) {
        Pattern pattern = Pattern.compile(content);
        StringBuilder fullMessage = new StringBuilder();

        if (Objects.equals(content, "")) {
            return false;
        }

        for (Text sibling : message.getSiblings()) {
            if (sibling.getContent() instanceof PlainTextContent.Literal(String string)) {
                if (pattern.matcher(string.trim()).matches()) {
                    return true;
                }
                fullMessage.append(string.trim()).append(" ");
            }

            for (Text siblingSiblings : sibling.getSiblings()) {
                if (siblingSiblings.getContent() instanceof PlainTextContent.Literal(String string)) {
                    fullMessage.append(string.trim()).append(" ");
                }
            }
        }


        return pattern.matcher(fullMessage.toString().trim()).matches();
    }
}
