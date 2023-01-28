package com.inside_the_town_hall.game.ui.graphical.object.lib;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.blueprint.IGUIObject;

public abstract class GUIObject implements IGUIObject {
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 50;

    private int x,y,width,height;

    public GUIObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void init() {}

    public void exit() {}

    public void update() {}

    @Override
    public void onKeyDown(int keycode, int scancode, int mods) {}

    @Override
    public void onKeyUp(int keycode, int scancode, int mods) {}

    @Override
    public void onKeyPress(int keycode, int scancode, int mods) {}

    @Override
    public void onKey(int key, int scancode, int action, int mods) {}

    @Override
    public void onChar(char codepoint) {}

    @Override
    public void onMouseDown(int button, int mods) {}

    @Override
    public void onMouseUp(int button, int mods) {}

    public boolean isHovered() {
        double[] cursorPos = GUIManager.getInstance().getMouse().getCursor().getPos();
        return  (cursorPos[0] > this.x && cursorPos[0] < this.x + this.width &&
                cursorPos[1] > this.y && cursorPos[1] < this.y + this.height);
    }

    @Override
    public void onMouse(int button, int action, int mods) {}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
