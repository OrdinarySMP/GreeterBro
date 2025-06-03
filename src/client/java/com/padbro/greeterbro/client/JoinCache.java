package com.padbro.greeterbro.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.padbro.greeterbro.client.config.CacheClearType;
import com.padbro.greeterbro.client.config.ReturningPlayerConfig;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class JoinCache {
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private ArrayList<PlayerCacheEntry> joins;
  private String date;
  private static final File file = new File("join_cache.json");

  public static JoinCache loadCache() {
    String currentDate = getCurrentDate();
    if (!file.exists()) {
      return new JoinCache(currentDate, new ArrayList<>());
    }
    try (FileReader reader = new FileReader(file)) {
      JoinCache joinCache = gson.fromJson(reader, new TypeToken<>() {});
      ReturningPlayerConfig config = GreeterBroClient.getConfig().returningPlayerConfig;

      // Don`t clear the cache if cacheClearType is Never
      // Clear the cache if it`s a new day or if cacheClearType is OnNewSession
      if (config.cacheClearType != CacheClearType.Never
          && (!Objects.equals(joinCache.date, currentDate)
              || config.cacheClearType == CacheClearType.OnNewSession)) {
        joinCache.joins = new ArrayList<>();
        joinCache.date = currentDate;
        writeToFile(joinCache);
      }
      return joinCache;
    } catch (JsonSyntaxException exception) {
      return new JoinCache("", new ArrayList<>());
    } catch (Exception exception) {
      throw new RuntimeException(
          "Cannot load player cache (%s)".formatted(file.getAbsolutePath()), exception);
    }
  }

  public JoinCache(String date, ArrayList<PlayerCacheEntry> joins) {
    this.joins = joins;
    this.date = date;
  }

  public void add(String player) {
    Optional<PlayerCacheEntry> playerCacheEntry = getPlayerCacheEntry(player);

    if (playerCacheEntry.isEmpty()) {
      this.joins.add(new PlayerCacheEntry(player, Instant.now()));
    } else {
      playerCacheEntry.get().setJoinedAt(Instant.now());
    }
    writeToFile(this);
  }

  public boolean hasRecentlyJoined(String player) {
    Optional<PlayerCacheEntry> playerCacheEntry = this.getPlayerCacheEntry(player);
    if (playerCacheEntry.isEmpty()) {
      return false;
    }

    Instant joinedAt = playerCacheEntry.get().getJoinedAt();

    return joinedAt.isAfter(
        Instant.now()
            .minus(
                GreeterBroClient.getConfig().returningPlayerConfig.ignoreForMin,
                ChronoUnit.MINUTES));
  }

  public boolean hasJoined(String player) {
    return this.getPlayerCacheEntry(player).isPresent();
  }

  public void clear() {
    joins = new ArrayList<>();
    writeToFile(this);
  }

  private static String getCurrentDate() {
    return Instant.now()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
  }

  private Optional<PlayerCacheEntry> getPlayerCacheEntry(String player) {
    return this.joins.stream()
        .filter(playerCacheEntry -> player.equals(playerCacheEntry.name))
        .findFirst();
  }

  private static void writeToFile(JoinCache joinCache) {
    try (FileWriter writer = new FileWriter(file)) {
      gson.toJson(joinCache, writer);
    } catch (IOException e) {
      GreeterBroClient.LOGGER.warn("Failed to save cache data to file");
    }
  }

  public boolean shouldClearOnJoin() {
    ReturningPlayerConfig config = GreeterBroClient.getConfig().returningPlayerConfig;
    return config.cacheClearType == CacheClearType.OnJoin ||
            (config.cacheClearType == CacheClearType.OnNewDay &&
            !Objects.equals(this.date, getCurrentDate()));
  }

  public static class PlayerCacheEntry {
    private final String name;
    private long joinedAt;

    public PlayerCacheEntry(String name, Instant joinedAt) {
      this.name = name;
      this.joinedAt = joinedAt.toEpochMilli();
    }

    public Instant getJoinedAt() {
      return Instant.ofEpochMilli(joinedAt);
    }

    public void setJoinedAt(Instant instant) {
      this.joinedAt = instant.toEpochMilli();
    }
  }
}
