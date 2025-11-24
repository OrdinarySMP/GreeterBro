package com.padbro.greeterbro.client.managers;

import com.padbro.greeterbro.client.GreeterBroClient;
import com.padbro.greeterbro.client.JoinCache;
import com.padbro.greeterbro.client.config.GreeterBroConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.List;
import java.util.Random;

public class JoinMessageManager {
    public static void execute() {
        GreeterBroConfig config = GreeterBroClient.getConfig();
        JoinCache joinCache = GreeterBroClient.getJoinCache();
        if (!config.generalConfig.getEnabled()) {
            return;
        }

        if (config.afkConfig.getEnabled()) {
            AfkManager.isAfk = false;
            AfkManager.setLastActiveNow(false);
        }

        if (joinCache.shouldClearOnJoin()) {
            GreeterBroClient.getJoinCache().clear();
        }

        if (config.generalConfig.enableOwnJoin) {
            List<String> greetingList = config.generalConfig.greetings;

            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) {
                return;
            }
            TickManager.scheduleTask(
                    new TickManager.ScheduledTask(
                            config.generalConfig.delayRange.getRandomDelayInTicks(),
                            () -> {
                                ClientPlayerEntity currentPlayer = MinecraftClient.getInstance().player;
                                if (currentPlayer == null) {
                                    return;
                                }
                                Random rand = new Random();
                                String greetingTemplate = greetingList.get(rand.nextInt(greetingList.size()));
                                String greeting = greetingTemplate.replaceAll("\\s*%player%", "");
                                if (!greeting.isEmpty()) {
                                    currentPlayer.networkHandler.sendChatMessage(greeting);
                                }
                            },
                            player.getName().getString()));
        }
    }
}
