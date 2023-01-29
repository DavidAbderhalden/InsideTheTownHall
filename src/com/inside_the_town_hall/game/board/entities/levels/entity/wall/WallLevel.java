package com.inside_the_town_hall.game.board.entities.levels.entity.wall;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.HashMap;

public class WallLevel extends Level {
    private static final HashMap<Integer, Integer> HITPOINTS = new HashMap<>() {{
        put(1, 100);
        put(2, 150);
        put(3, 250);
        put(4, 400);
        put(5, 650);
    }};

    public WallLevel() {
        super(HITPOINTS, 1, 5, 0);
    }
}
