package com.inside_the_town_hall.game.board.items;

import com.inside_the_town_hall.game.board.items.behaviors.ILevel;

public abstract class Item {

    protected ILevel levelHandler;

    public Item(ILevel levelHandler) {
        this.levelHandler = levelHandler;
    }
}
