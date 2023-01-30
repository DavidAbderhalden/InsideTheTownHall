package com.inside_the_town_hall.game.properties;

import com.inside_the_town_hall.game.translation.Language;
import org.lwjgl.glfw.GLFW;

/**
 * Record to store immutable program data properties
 */
public record Properties(
        String CONTENT_ROOT,
        String FAVICON_NAME,
        Language DEFAULT_LANG,
        int MOVE_UP,
        int MOVE_LEFT,
        int MOVE_DOWN,
        int MOVE_RIGHT) {

    /**
     * Default constructor
     */
    public Properties() {
        this(
                "C:\\Users\\David\\OneDrive\\Programme\\Java\\InsideTheTownHall\\src\\com\\inside_the_town_hall\\game\\",
                "favicon_256.png",
                Language.EN_UK,
                GLFW.GLFW_KEY_W,
                GLFW.GLFW_KEY_A,
                GLFW.GLFW_KEY_S,
                GLFW.GLFW_KEY_D
        );
    }
}
