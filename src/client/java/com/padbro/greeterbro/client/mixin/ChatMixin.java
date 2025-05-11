package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.TickManager.ScheduledTask;
import com.padbro.greeterbro.client.TickManager;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

@Mixin(ChatHud.class)

public class ChatMixin {
    @Inject(method = "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", at = @At("HEAD"))
    public void onMessage (ChatHudLine message, CallbackInfo ci) {
        GreeterBroConfig config = GreeterBroClient.config.get();

        if (!config.enableGreeterBro) {
            return;
        }

        if (isJoinMessage(message) && MinecraftClient.getInstance().player != null) {
            TickManager.scheduleTask(new ScheduledTask(config.delayRange.getRandomDelay(), () -> {
                Random rand = new Random();
                String greeting = config.greetings.get(rand.nextInt(config.greetings.size()));
                MinecraftClient.getInstance().player.networkHandler.sendChatMessage(greeting);
            }));
        }
    }

    @Unique
    private boolean isJoinMessage(ChatHudLine message) {
        String joinMessageKey = "multiplayer.player.joined";
        if (message.content().getContent() instanceof TranslatableTextContent translatable) {
            String key = translatable.getKey();
            return Objects.equals(key, joinMessageKey);
        }

        boolean isJoinMessage = false;

        for (Text sibling : message.content().getSiblings()) {
            if (sibling.getContent() instanceof TranslatableTextContent translatable) {
                String key = translatable.getKey();
                if (Objects.equals(key, joinMessageKey)) {
                    isJoinMessage = true;
                    break;
                }
            }
        }
        return isJoinMessage;
    }
}
