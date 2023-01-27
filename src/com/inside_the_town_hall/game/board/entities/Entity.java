package com.inside_the_town_hall.game.board.entities;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.UUID;

public abstract class Entity {

    protected Level levelHandler;
    protected UUID id;

    public Entity(Level levelHandler) {
        this.levelHandler = levelHandler;
        this.id = UUID.randomUUID();
    }

    public boolean upgrade() {
        int newLevel = levelHandler.getLevel() + 1;
        if (newLevel > levelHandler.getMaxLevel()) {
            return false;
        }
        levelHandler.setLevel(newLevel);
        return true;
    }

    public int getSpeed() {
        return this.levelHandler.getSpeed();
    }
}
