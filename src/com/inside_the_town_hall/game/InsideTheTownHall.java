package com.inside_the_town_hall.game;

import com.inside_the_town_hall.game.environment.Environment;

/**
 * Program entry point
 * Parses env variables
 *
 * @author NekroQuest
 * @version 0.0.1
 */
public class InsideTheTownHall {
    private static final String SYNOPSIS = "InsideTheTownHall -m|-M dev|test|prod";
    private static Environment ENV;

    public static void main(String[] args) {
        if (!parseArgs(args)) {
            System.out.println(SYNOPSIS);
            return;
        }
        ENV.start();
    }

    /**
     * Sets the environment type to match args
     *
     * @param args program arguments
     */
    private static boolean parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-m")) {
                try {
                    ENV = Environment.fromString(args[i + 1]);
                    return ENV != null;
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }
            }
        }
        return false;
    }

    // Getter
    public static Environment getEnv() {
        return ENV;
    }
}
