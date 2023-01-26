package com.inside_the_town_hall.game.utils;

import org.lwjgl.glfw.GLFW;

/**
 * Utility class to keep track of the frame rate
 *
 * @author NekroQuest
 */
public class Time {

    private double lastFrame = 0;
    private double deltaTime;

    /**
     * Calculates and saves the rate
     * Is executed on every frame
     */
    public void onFrame() {
        double currentFrame = GLFW.glfwGetTime();
        this.deltaTime = currentFrame - lastFrame;
        this.lastFrame = currentFrame;
    }

    // Getter
    public double getDeltaTime() {
        return deltaTime;
    }
}
