package com.inside_the_town_hall.game.board.entities.lib.path;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.path.PathLevel;

/**
 * Path Entity
 *
 * @author NekroQuest
 */
public class Path extends Entity {

    public Path() {
        super(new PathLevel(1));
    }
}
