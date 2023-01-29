package com.inside_the_town_hall.game.board.entities.levels.entity.wall;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.HashMap;

/**
 * Wall Level inheritance specifies level attributes
 *
 * @author NekroQuest
 */
public class WallLevel extends Level {
    private static final HashMap<Integer, Integer> HITPOINTS = new HashMap<>() {{
        put(1, 100);
        put(2, 150);
        put(3, 250);
        put(4, 400);
        put(5, 650);
    }};

    public WallLevel(int level) {
        super(HITPOINTS, level, 5, 0);
    }
}
