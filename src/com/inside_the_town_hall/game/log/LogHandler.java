package com.inside_the_town_hall.game.log;

import com.inside_the_town_hall.game.translation.LanguageManager;
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
     * Translates message and uses log method
     *
     * @param mode Changeable mode for color of log message
     * @param key log message
     */
    public void deepLog(LogMode mode, String key) {
        log(mode, LanguageManager.getInstance().use(key));
    }

    /**
     * Handles logs as information
     *
     * @param mode Changeable mode for color of log message
     * @param message log message
     */
    public void log(LogMode mode, String message) {
        logger.info(mode.parse(message));
    }
}
