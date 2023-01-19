package com.inside_the_town_hall.game.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Handles all the logging to the console
 *
 * @author NekroQuest
 */
public class LogHandler {
    private final Logger logger;
    private static LogHandler instance;

    public LogHandler(Class clazz) {
        BasicConfigurator.configure();
        this.logger = Logger.getLogger(clazz);
    }

    /**
     * Handles logs as information
     *
     * @param mode Changeable mode for color of com.inside_the_town_hall.game.log
     * @param message The com.inside_the_town_hall.game.log message
     */
    public void log(LogMode mode, String message) {
        logger.info(mode.parse(message));
    }
}
