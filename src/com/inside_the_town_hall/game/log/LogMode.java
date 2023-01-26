package com.inside_the_town_hall.game.log;

/**
 * Color of the log message
 *
 * @author NekroQuest
 */
public enum LogMode {
    YELLOW("yellow"){
        @Override
        public String parse(String message) {
            return "\u001b[33m"+message+"\u001b[0m";
        }
    },
    GREEN("green"){
        @Override
        public String parse(String message) {
            return "\u001b[32m"+message+"\u001b[0m";
        }
    },
    RED("red"){
        @Override
        public String parse(String message) {
            return "\u001b[31m"+message+"\u001b[0m";
        }
    },
    BLACK("black"){
        @Override
        public String parse(String message) {
            return "\u001b[30m"+message+"\u001b[0m";
        }
    }
    ;

    private final String logModeString;

    LogMode(String logModeString) {
        this.logModeString = logModeString;
    }

    /**
     * Create a LogMode object from a string
     *
     * @param logModeString color as string
     * @return the correct LogMode object
     */
    public static LogMode fromString(String logModeString) {
          for(LogMode mode : LogMode.values()) {
              if (mode.getAsString().equals(logModeString)) {
                  return mode;
              }
          } return null;
    }

    /**
     * Is being overridden by specific modes
     *
     * @param message the log message
     * @return the ascii colored message
     */
    public String parse(String message) {
        return message;
    }

    // Getter
    public String getAsString() {
        return logModeString;
    }
}
