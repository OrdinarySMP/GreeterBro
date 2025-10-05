package com.padbro.greeterbro.client.mixin;

import com.padbro.greeterbro.client.AfkManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class OnChatSendMixin {
  @Inject(method = "sendChatMessage", at = @At("HEAD"))
  private void onSendChatMessage(String content, CallbackInfo ci) {
    AfkManager.setLastActiveNow();
  }

  @Inject(method = "sendChatCommand", at = @At("HEAD"))
  private void onSendCommandMessage(String content, CallbackInfo ci) {
    AfkManager.setLastActiveNow();
  }
}
