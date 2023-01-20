package com.inside_the_town_hall.game.ui.graphical.peripheral;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class Mouse implements GLFWMouseButtonCallbackI {

    private final Cursor cursor;
    private final GUIManager guiManager;

    public Mouse(GUIManager guiManager) {
        this.cursor = new Cursor(this);
        this.guiManager = guiManager;
    }

    @Override
    public void invoke(long l, int button, int action, int mods) {
        switch (action) {
            case 0 -> this.guiManager.getCurrentScreen().onMouseUp(button, mods);
            case 1 -> this.guiManager.getCurrentScreen().onMouseDown(button, mods);
        }
        this.guiManager.getCurrentScreen().onMouse(button, action, mods);
    }

    public boolean isMouseButtonDown(int button) {
        return (GLFW.glfwGetMouseButton(this.guiManager.getWindow(), button) == GLFW.GLFW_PRESS);
//        return this.buttonCodes.getOrDefault(button, false);
    }

    public Cursor getCursor() {
        return cursor;
    }

    public static class Cursor implements GLFWCursorPosCallbackI {
        private final Mouse mouse;

        private Cursor(Mouse mouse) {
            this.mouse = mouse;
        }

        @Override
        public void invoke(long l, double x, double y) {}

        public double[] getPos() {
            double[] xArr = new double[1];
            double[] yArr = new double[1];
            GLFW.glfwGetCursorPos(mouse.guiManager.getWindow(), xArr, yArr);
            return new double[] {xArr[0], yArr[0]};
        }
    }
}

