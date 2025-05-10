package com.padbro.greeterbro.client;

public class ScheduledTask {
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
