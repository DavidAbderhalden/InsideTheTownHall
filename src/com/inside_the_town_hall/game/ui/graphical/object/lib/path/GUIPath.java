package com.inside_the_town_hall.game.ui.graphical.object.lib.path;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class GUIPath extends GUIObject {

    public GUIPath(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(boolean[] flags) {
        Color color = new Color(255, 241, 161, 255);
        GUIManager.getInstance().drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color);
    }

    @Override
    public void onMouseDown(int button, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            System.out.printf("CLICKED ON PATH OBJECT %s x = %d, y = %d\n", this.hashCode(), super.getX(), super.getY());
        }
    }
}
