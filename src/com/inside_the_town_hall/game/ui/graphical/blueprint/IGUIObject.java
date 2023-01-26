package com.inside_the_town_hall.game.ui.graphical.blueprint;

/**
 * The GUI Object interface used by all objects inside the GUI
 *
 * @author NekroQuest
 */
public interface IGUIObject {

    /**
     * Initialize method for GUI Object
     */
    void init();

    /**
     * Drawing method of GUI Object
     * @param flags
     */
    void draw(boolean[] flags);

    /**
     * Exit Option
     */
    void exit();

    /**
     * Update is performed on every frame
     */
    void update();

    /**
     * Keyboard press event down
     * @param keycode the key that was pressed
     * @param scancode
     * @param mods
     */
    void onKeyDown(int keycode, int scancode, int mods);

    /**
     * Keyboard press event up
     * @param keycode the key that was pressed
     * @param scancode
     * @param mods
     */
    void onKeyUp(int keycode, int scancode, int mods);

    /**
     * Keyboard press event hold
     * @param keycode the key that was pressed
     * @param scancode
     * @param mods
     */
    void onKeyPress(int keycode, int scancode, int mods);

    /**
     *
     * @param key
     * @param scancode
     * @param action
     * @param mods
     */
    void onKey(int key, int scancode, int action, int mods);

    void onChar(char codepoint);

    void onMouseDown(int button, int mods);

    void onMouseUp(int button, int mods);

    void onMouse(int button, int action, int mods);
}
