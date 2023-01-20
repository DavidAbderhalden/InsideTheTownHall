package com.inside_the_town_hall.game.ui.graphical.blueprint;

public interface IGUIObject {
    void init();

    void draw(boolean[] flags);

    void exit();

    void update();

    void onKeyDown(int keycode, int scancode, int mods);

    void onKeyUp(int keycode, int scancode, int mods);

    void onKeyPress(int keycode, int scancode, int mods);

    void onKey(int key, int scancode, int action, int mods);

    void onChar(char codepoint);

    void onMouseDown(int button, int mods);

    void onMouseUp(int button, int mods);

    void onMouse(int button, int action, int mods);
}
