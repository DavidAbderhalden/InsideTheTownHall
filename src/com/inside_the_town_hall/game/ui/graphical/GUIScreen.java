package com.inside_the_town_hall.game.ui.graphical;

import com.inside_the_town_hall.game.ui.graphical.blueprint.IGUIObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GUIScreen implements IGUIObject {

    protected final List<GUIObject> objectList;

    private final GUIObject nullObj = new GUIObject(0,0,0,0) {
        @Override
        public void draw(boolean[] flags) {}
    };
    private GUIObject active = nullObj;

    public GUIScreen() {
        objectList = new ArrayList<>();
    }

    public void init() {
        objectList.forEach(GUIObject::init);
    }

    public void draw(boolean[] flags) {
        objectList.forEach(obj -> {
            obj.draw(new boolean[] {
                    this.getActive() == obj
            });
        });
    }

    @Override
    public void update() {
        objectList.forEach(IGUIObject::update);
    }

    public void exit() {
        objectList.forEach(IGUIObject::exit);
    }

    public void onKeyDown(int keycode, int scancode, int mods) {
        this.getActive().onKeyDown(keycode, scancode, mods);
    }

    @Override
    public void onChar(char codepoint) {
        this.getActive().onChar(codepoint);
    }

    public void onKeyUp(int keycode, int scancode, int mods) {
        this.getActive().onKeyUp(keycode, scancode, mods);
    }

    public void onKeyPress(int keycode, int scancode, int mods) {
        this.getActive().onKeyPress(keycode, scancode, mods);
    }

    public void onKey(int key, int scancode, int action, int mods) {
        this.getActive().onKey(key, scancode, action, mods);
    }

    public void onMouseDown(int button, int mods) {
        this.setActive(null);
        objectList.stream().filter(GUIObject::isHovered).forEach(obj -> {
            obj.onMouseDown(button, mods);
            this.setActive(obj);
        });
    }

    public void onMouseUp(int button, int mods) {
        objectList.stream().filter(GUIObject::isHovered).forEach(obj -> obj.onMouseUp(button, mods));
    }

    public void onMouse(int button, int action, int mods) {
        objectList.stream().filter(GUIObject::isHovered).forEach(obj -> obj.onMouse(button, action, mods));
    }

    public void setActive(GUIObject active) {
        this.active = active;
    }

    public GUIObject getActive() {
        return Objects.requireNonNullElse(this.active, this.nullObj);
    }
}
