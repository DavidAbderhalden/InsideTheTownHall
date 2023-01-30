package com.inside_the_town_hall.game.ui.graphical.peripheral;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class Keyboard implements GLFWKeyCallbackI {
    private final GUIManager guiManager;

    public Keyboard(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @Override
    public void invoke(long l, int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW.GLFW_RELEASE -> this.guiManager.getCurrentScreen().onKeyUp(key, scancode, mods);
            case GLFW.GLFW_PRESS -> {
                if (key == GLFW.GLFW_KEY_ENTER && mods == GLFW.GLFW_MOD_ALT) {
                    this.guiManager.toggleFullscreen();
                }
                this.guiManager.getCurrentScreen().onKeyDown(key, scancode, mods);
            }
            case GLFW.GLFW_REPEAT -> this.guiManager.getCurrentScreen().onKeyPress(key, scancode, mods);
        }
        this.guiManager.getCurrentScreen().onKey(key, scancode, action, mods);
    }

    public void invoke(long l, int i) {
        this.guiManager.getCurrentScreen().onChar((char) i);
    }

    public boolean isKeyDown(int key) {
        return ((GLFW.glfwGetKey(guiManager.getWindow(), key)) == GLFW.GLFW_PRESS);
    }
}
