package com.inside_the_town_hall.game.scheduler;

import java.util.*;

/**
 * Task management class
 *
 * @author NekroQuest
 */
public class Scheduler {
    private final HashMap<UUID, Task> tasks;

    public Scheduler() {
        this.tasks = new HashMap<>();
    }

    /**
     * Creates task that will only run once
     *
     * @param runnable task method
     * @param delay    ticks until task is being run
     */
    public void createVolatileTask(Runnable runnable, int delay) {
        Task task = new Task(UUID.randomUUID(), runnable, delay, true, 1);
        addTask(task);
    }

    /**
     * Creates task that will run for the entire program runtime
     *
     * @param runnable task method
     * @param delay    ticks until task is being run
     */
    public void createConstantTask(Runnable runnable, int delay) {
        Task task = new Task(UUID.randomUUID(), runnable, delay, false, 0);
        addTask(task);
    }

    public void createTimedTask(Runnable runnable, int delay, int lifetime) {
        Task task = new Task(UUID.randomUUID(), runnable, delay, true, lifetime);
        addTask(task);
    }

    /**
     * Adds task to task map
     *
     * @param task task to be added
     */
    private synchronized void addTask(Task task) {
        this.tasks.put(task.getId(), task);
    }

    /**
     * Removes task from task map
     *
     * @param id task id
     * @return successfully removed
     */
    public synchronized boolean removeTask(UUID id) {
        return this.tasks.remove(id) != null;
    }

    /**
     * Returns task from map
     *
     * @param id task to be returned
     * @return task or null if not existent
     */
    public synchronized Task getTask(UUID id) {
        return this.tasks.get(id);
    }

    /**
     * Will be run at every tick. Updates all tasks
     */
    public synchronized void update() {
        Set<UUID> tasksToRemove = new HashSet<>();
        for (Map.Entry<UUID, Task> task : this.tasks.entrySet()) {
            if (task.getValue().tick() && task.getValue().isRemovable()) {
                tasksToRemove.add(task.getKey());
            }
        }
        this.tasks.entrySet().removeIf(
                entry -> tasksToRemove.contains(entry.getKey()));
    }
}
