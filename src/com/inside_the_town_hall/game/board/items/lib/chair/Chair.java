package com.inside_the_town_hall.game.board.items.lib.chair;

import com.inside_the_town_hall.game.board.items.Item;
import com.inside_the_town_hall.game.board.items.levels.item.chair.ChairLevel;

public class Chair extends Item {

    public Chair() {
        super(new ChairLevel(1));
    }

    public int getHitpoints() {
        return super.levelHandler.getHitpoints();
    }
}
