package com.inside_the_town_hall.game.properties;

import com.inside_the_town_hall.game.translation.Language;

/**
 * Record to store immutable program data properties
 */
public record Properties(
        String CONTENT_ROOT,
        String FAVICON_NAME,
        Language DEFAULT_LANG) {

    /**
     * Default constructor
     */
    public Properties() {
        this(
                "C:\\Users\\David\\OneDrive\\Programme\\Java\\InsideTheTownHall\\src\\com\\inside_the_town_hall\\game\\",
                "favicon_256.png",
                Language.EN_UK
        );
    }
}
