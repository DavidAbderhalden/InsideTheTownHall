package com.inside_the_town_hall.game.log;

public enum LogMode {
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

    public static LogMode fromString(String logModeString) {
          for(LogMode mode : LogMode.values()) {
              if (mode.getAsString().equals(logModeString)) {
                  return mode;
              }
          } return null;
    }

    public String getAsString() {
        return logModeString;
    }

    public String parse(String message) {
        return message;
    }
}
