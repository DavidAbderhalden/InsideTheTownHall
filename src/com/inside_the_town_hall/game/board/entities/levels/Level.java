package com.inside_the_town_hall.game.board.entities.levels;

import java.util.HashMap;

public abstract class Level {

    protected final HashMap<Integer, Integer> hitpointsTable;
    protected int level;
    protected final int maxLevel;
    protected final int speed; // ticks to take a step

    public Level(HashMap<Integer, Integer> hitpointsTable, int level, int maxLevel, int speed) {
        this.hitpointsTable = hitpointsTable;
        this.level = level;
        this.maxLevel = maxLevel;
        this.speed = speed;
    }

    public int getHitpoints() {
        return this.hitpointsTable.get(this.level);
    }

    public int getLevel() {
        return this.level;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setLevel(int newLevel) {
        this.level = newLevel;
    }
}
