package com.inside_the_town_hall.game.ui.graphical.object.lib.wizard;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.UUID;

public class GUIWizard extends GUIObject implements IGUIBoardItem {
    private final UUID itemId;

    public GUIWizard(int x, int y, int width, int height, UUID itemId) {
        super(x, y, width, height);
        this.itemId = itemId;
    }

    @Override
    public void draw(boolean[] flags) {
        boolean isActive = false;
        if(flags.length > 0) isActive = flags[0];
        Color color = isActive ? new Color(48, 175, 26) : new Color(71, 135, 239, 255);
        GUIManager.getInstance().drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color);
    }

    @Override
    public void update() {
        //
    }

    @Override
    public void onMouseDown(int button, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            System.out.printf("LEFT CLICKED ON WIZARD OBJECT %s x = %d, y = %d\n", this.hashCode(), super.getX(), super.getY());
        }
    }

    @Override
    public UUID getItemId() {
        return this.itemId;
    }
}
