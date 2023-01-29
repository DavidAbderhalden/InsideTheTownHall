package com.inside_the_town_hall.game.board.entities;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.UUID;

/**
 * Every object present on the game board
 *
 * @author NekroQuest
 */
public abstract class Entity {

    protected Level levelHandler;
    protected UUID id;

    public Entity(Level levelHandler) {
        this.levelHandler = levelHandler;
        this.id = UUID.randomUUID();
    }

    /**
     * Increases the level of the entity
     *
     * @return true if max level hasn't been reached
     */
    public boolean upgrade() {
        int newLevel = levelHandler.getLevel() + 1;
        if (newLevel > levelHandler.getMaxLevel()) {
            return false;
        }
        levelHandler.setLevel(newLevel);
        return true;
    }

    // Getter
    public int getSpeed() {
        return this.levelHandler.getSpeed();
    }
}
