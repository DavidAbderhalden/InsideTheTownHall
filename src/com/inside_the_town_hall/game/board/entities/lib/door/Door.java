package com.inside_the_town_hall.game.board.entities.lib.door;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.wall.WallLevel;
import com.inside_the_town_hall.game.ui.graphical.object.lib.door.Direction;

/**
 * Door Entity
 *
 * @author NekroQuest
 */
public class Door extends Entity {
    private final Direction direction; // direction the door is facing

    public Door(Direction direction) {
        super(new WallLevel(1)); // door shares level with wall
        this.direction = direction;
    }

    // Getter
    public Direction getDirection() {
        return this.direction;
    }
}
