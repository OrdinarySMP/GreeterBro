package com.padbro.greeterbro.client;

import java.util.LinkedList;
import java.util.List;

public class TickManager {
  private static final List<ScheduledTask> tasks = new LinkedList<>();

  public static void scheduleTask(ScheduledTask task) {
    tasks.add(task);
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

    public ScheduledTask(int delayInTicks, Runnable task) {
      this.task = task;
      this.remainingTicks = delayInTicks;
    }

    public void run() {
      this.task.run();
    }
  }
}
