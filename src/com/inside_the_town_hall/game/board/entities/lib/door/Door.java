package com.inside_the_town_hall.game.board.entities.lib.door;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.wall.WallLevel;
import com.inside_the_town_hall.game.ui.graphical.object.lib.door.Direction;

public class Door extends Entity {
    private final Direction direction;

    public Door(Direction direction) {
        super(new WallLevel());
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
