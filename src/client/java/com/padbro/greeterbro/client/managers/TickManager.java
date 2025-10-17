package com.padbro.greeterbro.client.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class TickManager {
  private static final List<ScheduledTask> tasks = new LinkedList<>();

  public static void scheduleTask(ScheduledTask task) {
    tasks.add(task);
  }

  public static void cancelTaskByPlayerName(@Nullable String playerName) {
    if (playerName != null) {
      tasks.removeIf(task -> Objects.equals(task.playerName, playerName));
    }
  }

  public static void onTick() {
    for (ScheduledTask task : tasks) {
      if (task.remainingTicks <= 0) {
        task.run();
        tasks.remove(task);
        return;
      }
      task.remainingTicks--;
    }
  }

  public static class ScheduledTask {
    final Runnable task;
    int remainingTicks;
    String playerName;

    public ScheduledTask(int delayInTicks, Runnable task, String playerName) {
      this.task = task;
      this.remainingTicks = delayInTicks;
      this.playerName = playerName;
    }

    public void run() {
      this.task.run();
    }
  }
}
