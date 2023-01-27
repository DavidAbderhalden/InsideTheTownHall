package com.inside_the_town_hall.game.board.entities.lib.chair;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.chair.ChairLevel;

public class Chair extends Entity {

    public Chair() {
        super(new ChairLevel(1, 5));
    }
}
