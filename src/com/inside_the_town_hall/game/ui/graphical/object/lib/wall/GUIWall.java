package com.inside_the_town_hall.game.ui.graphical.object.lib.wall;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;

import java.awt.*;
import java.util.UUID;

/**
 * Wall Entity displayed in the GUI
 *
 * @author NekroQuest
 */
public class GUIWall extends GUIObject implements IGUIBoardItem {
    private final UUID itemId;

    public GUIWall(int x, int y, int width, int height, UUID itemId) {
        super(x, y, width, height);
        this.itemId = itemId;
    }

    /**
     * Draws the GUI wall
     * @param flags if active
     */
    @Override
    public void draw(boolean[] flags) {
        boolean isActive = false;
        if (flags.length > 0) isActive = flags[0];
        Color color = isActive ? new Color(80, 80, 80, 255) : new Color(54, 54, 54, 255);
        GUIManager.getInstance().drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color);
    }

    // Getter
    @Override
    public UUID getItemId() {
        return this.itemId;
    }

}
