package com.inside_the_town_hall.game.environment;

import com.inside_the_town_hall.game.controlls.GameController;

/**
 * Enum of environments
 *
 * @author NekroQuest
 */
public enum Environment {
    DEV("dev") {
        @Override
        public void start() {
            GameController.getInstance().dev();
        }
    },
    TEST("test") {
        @Override
        public void start() {
            GameController.getInstance().test();
        }
    },
    PROD("prod") {
        @Override
        public void start() {
            GameController.getInstance().prod();
        }
    };

    private final String envString; // dev, test, prod

    Environment(String envString) {
        this.envString = envString;
    }

    /**
     * Returns environment from string "dev", "test", "prod"
     *
     * @param envString the environment as string, case-insensitive
     * @return the environment or null
     */
    public static Environment fromString(String envString) {
        for (Environment type : Environment.values()) {
            if (type.envString.equalsIgnoreCase(envString)) {
                return type;
            }
        }
        return null;
    }

    /**
     * starts the program in specific environment
     * Is being overridden by types
     */
    public void start() {
    }

    // Getter
    public String getAsString() {
        return this.envString;
    }
}
