package com.inside_the_town_hall.game.ui.graphical;

public class GUI {
    private static GUI instance;
    private long window;

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    public void update() {
    }
}
