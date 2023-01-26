package com.inside_the_town_hall.game.scheduler;

import java.util.UUID;

/**
 * A task that is being processed
 *
 * @author NekroQuest
 */
public class Task {
    private final UUID id;
    private final Runnable runnable; // method of task
    private final int delay; // in ticks
    private int remainingDelay;
    private final boolean removable; // task is constant or not
    private int lifetime;

    public Task(UUID id, Runnable runnable, int delay, boolean removable, int lifetime) {
        this.id = id;
        this.runnable = runnable;
        this.delay = delay;
        this.remainingDelay = this.delay;
        this.removable = removable;
        this.lifetime = lifetime;
    }

    /**
     * Updates the task delay or runs task
     *
     * @return if task has been run
     */
    public boolean tick() {
        this.remainingDelay--;
        if (this.remainingDelay == 0) {
            this.remainingDelay = this.delay;
            this.runnable.run();
            this.lifetime--;
            return true;
        }
        return false;
    }

    // Getter
    public boolean isRemovable() {
        return this.removable && this.lifetime == 0;
    }

    // Getter
    public UUID getId() {
        return this.id;
    }
}
