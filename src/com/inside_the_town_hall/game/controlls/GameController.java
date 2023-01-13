package com.inside_the_town_hall.game.controlls;

import com.inside_the_town_hall.game.scheduler.Clock;
import com.inside_the_town_hall.game.scheduler.Scheduler;
import com.inside_the_town_hall.game.test.TestRunner;
import com.inside_the_town_hall.game.ui.graphical.GUI;

/**
 * Main Loop and different environments are run in the GameController
 * Is a Singleton
 *
 * @author NekroQuest
 */
public class GameController {
    private boolean running;
    private static GameController instance;
    private final Clock clock;
    private final Scheduler scheduler;

    private GameController() {
        this.clock = new Clock();
        this.scheduler = new Scheduler();
        this.running = true;
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Starts the development game
     */
    public void dev() {
        //this.scheduler.createConstantTask(() -> System.out.println("constant1"), 3);
        //this.scheduler.createVolatileTask(() -> System.out.println("volatile1"), 4);
        this.scheduler.createConstantTask(() -> System.out.println("constant2"), 1000);
        //this.scheduler.createVolatileTask(() -> System.out.println("volatile2"), 10);
        this.clock.start();
    }

    /**
     * Runs all the JUnit suites
     */
    public void test() {
        TestRunner tests = new TestRunner();
        tests.run();
    }

    /**
     * Starts the production game
     */
    public void prod() {
        // TODO: Implement
        this.clock.start();
        while (this.running) {
            GUI.getInstance().update();
        }
    }

    /**
     * Shuts down the main loop
     */
    public void shutdown() {
        this.running = false;
        this.clock.shutdown();
    }

    // Getter
    public Scheduler getScheduler() {
        return this.scheduler;
    }
}
