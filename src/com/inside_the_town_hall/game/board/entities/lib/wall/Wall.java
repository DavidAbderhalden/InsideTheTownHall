package com.inside_the_town_hall.game.board.entities.lib.wall;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.wall.WallLevel;

public class Wall extends Entity {

    public Wall() {
        super(new WallLevel());
    }
}
