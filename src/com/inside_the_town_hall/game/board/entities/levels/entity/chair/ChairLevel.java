package com.inside_the_town_hall.game.board.entities.levels.entity.chair;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.HashMap;

public class ChairLevel extends Level {
    private static final HashMap<Integer, Integer> HITPOINTS = new HashMap<>() {{
        put(1, 800);
        put(2, 950);
        put(3, 1100);
        put(4, 1550);
        put(5, 1700);
    }};
    private static final int SPEED = 200;

    public ChairLevel(int level, int maxLevel) {
        super(HITPOINTS, level, maxLevel, SPEED);
    }
//    public int getSpeed() {
//        return this.speed;
//    }
}
