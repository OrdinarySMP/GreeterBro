package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.ScheduledTask;
import com.padbro.greeterbro.client.TickManager;
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

@Mixin(ChatHud.class)

public class ChatMixin {
    @Inject(method = "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", at = @At("HEAD"))
    public void onMessage (ChatHudLine message, CallbackInfo ci) {
        if (isJoinMessage(message) && MinecraftClient.getInstance().player != null) {
            System.out.println("Join message received" + message.toString());
            int delayInTicks = (int) (Math.random() * (60 - 20)) + 20;
            TickManager.scheduleTask(new ScheduledTask(delayInTicks, () -> {
                MinecraftClient.getInstance().player.networkHandler.sendChatMessage("hello");
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
