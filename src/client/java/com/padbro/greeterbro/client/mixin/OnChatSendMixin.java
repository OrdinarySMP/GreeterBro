package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.managers.AfkManager;
import com.padbro.greeterbro.client.GreeterBroClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class OnChatSendMixin {
  @Inject(method = "sendChatMessage", at = @At("HEAD"))
  private void onSendChatMessage(String content, CallbackInfo ci) {
    this.onMessageSend(content, ci);
  }

  @Inject(method = "sendChatCommand", at = @At("HEAD"))
  private void onSendCommandMessage(String content, CallbackInfo ci) {
    this.onMessageSend(content, ci);
  }

  @Unique
  private void onMessageSend(String content, CallbackInfo ci) {
    if (GreeterBroClient.getConfig().afkConfig.enable) {
      AfkManager.setLastActiveNow(true);
    }
  }
}
