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
    return joins.get(player) != null;
  }

  public static boolean hasRecentlyJoined(String player) {
    Instant joinedAt = joins.get(player);
    if (joinedAt == null) {
      return false;
    }
    return joinedAt.isAfter(
        Instant.now()
            .minus(
                GreeterBroClient.config.get().returningPlayerConfig.ignoreForMin,
                ChronoUnit.SECONDS));
  }

    public static void clear() {
      joins.clear();
    }
}
