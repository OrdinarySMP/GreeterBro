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
    private int delayInTicks;
    int remainingTicks;
    final Runnable task;

    public ScheduledTask(int delayInTicks, Runnable task) {
      this.delayInTicks = delayInTicks;
      this.task = task;
      this.remainingTicks = delayInTicks;
    }

    public void run() {
      this.task.run();
    }
  }
}
