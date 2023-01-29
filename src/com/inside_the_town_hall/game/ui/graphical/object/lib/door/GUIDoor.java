package com.inside_the_town_hall.game.ui.graphical.object.lib.door;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;

import java.awt.*;
import java.util.UUID;

public class GUIDoor extends GUIObject implements IGUIBoardItem {
    private final UUID itemId;
    private final Direction direction;

    public GUIDoor(int x, int y, int width, int height, UUID itemId, Direction direction) {
        super(x, y, width, height);
        this.itemId = itemId;
        this.direction = direction;
    }

    @Override
    public UUID getItemId() {
        return this.itemId;
    }

    @Override
    public void draw(boolean[] flags) {
        boolean isActive = false;
        if(flags.length > 0) isActive = flags[0];
        Color color1 = isActive ? new Color(143, 102, 63, 255) : new Color(128, 95, 62, 255);
        Color color2 = isActive ? new Color(239, 217, 98, 255) : new Color(255, 241, 161, 255);
        this.direction.drawDoor(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color1, color2);
    }
}
