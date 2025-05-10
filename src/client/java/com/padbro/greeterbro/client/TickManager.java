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
            System.out.println("Ticking task");
            if (task.remainingTicks <= 0) {
                System.out.println("Executing task");
                task.run();
                System.out.println("Task executed");
                tasks.remove(task);
                System.out.println("Removing task");
                return;
            }
            System.out.println("Decreasing remainingTicks");
            task.remainingTicks--;
            System.out.println("remainingTicks: " + task.remainingTicks);
        }
    }
}
