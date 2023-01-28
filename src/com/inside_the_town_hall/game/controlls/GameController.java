package com.inside_the_town_hall.game.controlls;

import com.inside_the_town_hall.game.board.entities.lib.wizard.Wizard;
import com.inside_the_town_hall.game.board.lib.behavior.BoardItemType;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;
import com.inside_the_town_hall.game.properties.Properties;
import com.inside_the_town_hall.game.scheduler.Clock;
import com.inside_the_town_hall.game.scheduler.Scheduler;
import com.inside_the_town_hall.game.test.TestRunner;
import com.inside_the_town_hall.game.translation.LanguageManager;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.GUIScreen;
import com.inside_the_town_hall.game.ui.graphical.screen.*;

/**
 * Main Loop and different environments are run in the GameController
 * Is a Singleton
 *
 * @author NekroQuest
 */
public class GameController {
    public static final Properties properties = new Properties();
    private final GUIScreen gameScreen;
    private static GameController instance;
    private final Clock clock;
    private final Scheduler scheduler;
    private final LogHandler LOGGER = new LogHandler(GameController.class);

    private GameController() {
        this.clock = new Clock();
        this.scheduler = new Scheduler();
        this.gameScreen = new GameScreen();
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
        this.scheduler.createVolatileTask(() -> this.LOGGER.log(LogMode.GREEN, "4 Ticks"), 4);
        this.scheduler.createConstantTask(() ->
                        this.LOGGER.log(LogMode.RED, "500 Ticks"),
                500);
        // this.scheduler.createTimedTask(() -> this.LOGGER.log(LogMode.GREEN, "Timed"), 100, 3);
        //this.scheduler.createVolatileTask(() -> System.out.println("volatile2"), 10);
        this.clock.start();
        startGame();
        GUIManager.create(1200, 800, LanguageManager.getInstance().use("UI.SCREEN.GAME.TITLE")).init();
        GUIManager.getInstance().setCurrentScreen(this.gameScreen);
        GUIManager.getInstance().run(); /* blocks Thread */
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
        this.clock.start();
        startGame();
        GUIManager.create(1200, 800, LanguageManager.getInstance().use("UI.SCREEN.GAME.TITLE")).init();
        GUIManager.getInstance().setCurrentScreen(this.gameScreen);
        GUIManager.getInstance().run();
    }

    private void startGame() {
        // TODO: Remove Dev stuff below
        BoardItem item = new BoardItem(new BoardPosition(1, 2), new Wizard(), BoardItemType.MOVABLE);
        Board.getInstance().getLayout().add(item);
        item.action().pathfindTo(new BoardPosition(2, 5));
        // item.action().abort();
    }

    /**
     * Shuts down the main loop
     */
    public void shutdown() {
        GUIManager.getInstance().stop();
        this.clock.shutdown();
    }

    // Getter
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public Properties getProperties() {
        return this.properties;
    }
}
