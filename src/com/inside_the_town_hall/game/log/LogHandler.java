package com.inside_the_town_hall.game.log;

import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.translation.LanguageManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.HashMap;

/**
 * Handles all the logging to the console
 *
 * @author NekroQuest
 */
public class LogHandler {
    private final Logger logger;

    public LogHandler(Class clazz) {
        PropertyConfigurator.configure(GameController.getProperties().CONTENT_ROOT() + "/properties/log4j.properties");
        this.logger = Logger.getLogger(clazz);
    }

    /**
     * Translates message and uses log method
     * The Method is overloaded
     *
     * @param mode Changeable mode for color of log message
     * @param key  log message
     */
    public void deepLog(LogMode mode, String key) {
        log(mode, LanguageManager.getInstance().use(key));
    }

    /**
     * Translates message and uses log method plus provides a string formatting map
     * The Method is overloaded
     *
     * @param mode Changeable mode for color of log message
     * @param key  log message
     * @param transMap the string formatting map
     */
    public void deepLog(LogMode mode, String key, HashMap<String, String> transMap) {
        log(mode, LanguageManager.getInstance().use(key, transMap));
    }

    /**
     * Handles logs as information
     *
     * @param mode    Changeable mode for color of log message
     * @param message log message
     */
    public void log(LogMode mode, String message) {
        logger.info(mode.parse(message));
    }
}
