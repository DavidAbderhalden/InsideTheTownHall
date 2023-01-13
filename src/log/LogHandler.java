package log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Handles all the logging to the console
 * Is a Singleton
 *
 * @author NekroQuest
 */
public class LogHandler {
    private Logger logger;
    private static LogHandler instance;

    private LogHandler(){
        BasicConfigurator.configure();
    }

    public static LogHandler getInstance() {
        if(instance == null) {
            instance = new LogHandler();
        } return instance;
    }

    /**
     * Handles logs as information
     * Object version
     *
     * @param object Object of class
     * @param mode Changeable mode for color of log
     * @param message The log message
     */
    public void log(Object object, LogMode mode, String message) {
        logger = Logger.getLogger(object.getClass());
        logger.info(mode.parse(message));
    }

    /**
     * Handles logs as information
     * Class version
     *
     * @param clazz Class
     * @param mode Changeable mode for color of log
     * @param message The log message
     */
    public void log(Class clazz, LogMode mode, String message) {
        logger = Logger.getLogger(clazz);
        logger.info(mode.parse(message));
    }
}
