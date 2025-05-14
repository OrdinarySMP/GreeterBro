package com.padbro.greeterbro.client;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class JoinCache {
  private static final Map<String, Instant> joins = new HashMap<>();

  public static void add(String player) {
    joins.put(player, Instant.now());
  }

  public static boolean hasJoined(String player) {
    return joins.containsKey(player);
  }

  public static boolean hasRecentlyJoined(String player) {
    Instant joinedAt = joins.get(player);
    if (joinedAt == null) {
      return false;
    }
    return joinedAt.isAfter(
        Instant.now()
            .minus(
                GreeterBroClient.getConfig().returningPlayerConfig.ignoreForMin,
                ChronoUnit.MINUTES));
  }

  public static void clear() {
    joins.clear();
  }
}
