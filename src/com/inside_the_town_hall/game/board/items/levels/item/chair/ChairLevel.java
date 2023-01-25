package com.inside_the_town_hall.game.board.items.levels.item.chair;

import com.inside_the_town_hall.game.board.items.behaviors.ILevel;

import java.util.HashMap;

public class ChairLevel implements ILevel {

    private final HashMap<Integer, Integer> hitpointsTable;
    private final int level;
    private final int speed; // ticks to take a step

    public ChairLevel(int level) {
        this.level = level;
        this.speed = 200;
        this.hitpointsTable = new HashMap<>() {{
            put(1, 800);
            put(2, 950);
            put(3, 1100);
            put(4, 1550);
            put(5, 1700);
        }};
    }

    @Override
    public int getHitpoints() {
        return this.hitpointsTable.get(this.level);
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    public int getLevel() {
        return this.level;
    }
}
