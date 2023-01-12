package com.inside_the_town_hall.game.scheduler;

import com.inside_the_town_hall.game.controlls.GameController;

/**
 * Controls the tick speed in separate Thread
 */
public class Clock extends Thread {
    private final int tickSpeed = 100; // ticks per second (max 1000)
    private boolean running = true;

    @Override
    public void run() {
        tick();
    }

    /**
     * Runs tick at every tick speed
     */
    private void tick() {
        long lastTick = System.currentTimeMillis();
        while(this.running) {
            long currentMillis = System.currentTimeMillis();
            if (currentMillis - lastTick > (1000 / this.tickSpeed)) {
                lastTick = currentMillis;
                Thread taskUpdater = new Thread(() -> GameController.getInstance().getScheduler().update());
                taskUpdater.start();
            }
        }
    }

    // Getter
    public int getTickSpeed() {
        return tickSpeed;
    }

    // Setter
    public void shutdown() {
        this.running = false;
    }
}
