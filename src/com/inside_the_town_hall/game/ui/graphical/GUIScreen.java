package com.inside_the_town_hall.game.ui.graphical;

import com.inside_the_town_hall.game.ui.graphical.blueprint.IGUIObject;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Super class to build different GUI Screens
 *
 * @author NekroQuest
 */
public abstract class GUIScreen implements IGUIObject {

    protected final ArrayList<GUIObject> objects;
    private final GUIObject nullObj;
    private GUIObject active;

    public GUIScreen() {
        this.objects = new ArrayList<>();
        this.nullObj = new GUIObject(0, 0, 0, 0) {
            @Override
            public void draw(boolean[] flags) {
            }
        };
        this.active = this.nullObj;
    }

    /**
     * Initializes all the objects on the screen
     */
    @Override
    public void init() {
        objects.forEach(GUIObject::init);
    }

    /**
     * Draws every object and checks if it is active
     * @param flags unused
     */
    @Override
    public void draw(boolean[] flags) {
        objects.forEach(obj -> obj.draw(new boolean[]{
                this.getActive() == obj
        }));
    }

    /**
     * Updates all the objects on the screen
     */
    @Override
    public void update() {
        objects.forEach(IGUIObject::update);
    }

    /**
     * Exits for all the objects on the screen
     */
    @Override
    public void exit() {
        objects.forEach(IGUIObject::exit);
    }

    /**
     * Delegates the key press to the active object
     * @param keycode the key that was pressed
     * @param scancode
     * @param mods
     */
    @Override
    public void onKeyDown(int keycode, int scancode, int mods) {
        this.getActive().onKeyDown(keycode, scancode, mods);
    }

    /**
     *
     * @param codepoint
     */
    @Override
    public void onChar(char codepoint) {
        this.getActive().onChar(codepoint);
    }

    @Override
    public void onKeyUp(int keycode, int scancode, int mods) {
        this.getActive().onKeyUp(keycode, scancode, mods);
    }

    @Override
    public void onKeyPress(int keycode, int scancode, int mods) {
        this.getActive().onKeyPress(keycode, scancode, mods);
    }

    @Override
    public void onKey(int key, int scancode, int action, int mods) {
        this.getActive().onKey(key, scancode, action, mods);
    }

    /**
     * Sets the new, hovered object to active. Delegates on mouse down
     * to object, providing the params
     * @param button mouse button which has been pressed
     * @param mods
     */
    @Override
    public void onMouseDown(int button, int mods) {
        this.setActive(null);
        objects.stream().filter(GUIObject::isHovered).forEach(obj -> {
            obj.onMouseDown(button, mods);
            this.setActive(obj);
        });
    }

    @Override
    public void onMouseUp(int button, int mods) {
        objects.stream().filter(GUIObject::isHovered).forEach(obj -> obj.onMouseUp(button, mods));
    }

    @Override
    public void onMouse(int button, int action, int mods) {
        objects.stream().filter(GUIObject::isHovered).forEach(obj -> obj.onMouse(button, action, mods));
    }

    // Setter
    public void setActive(GUIObject active) {
        this.active = active;
    }

    // Getter
    public GUIObject getActive() {
        return Objects.requireNonNullElse(this.active, this.nullObj);
    }
}
