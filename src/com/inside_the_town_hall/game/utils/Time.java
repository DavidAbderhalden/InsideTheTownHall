package com.inside_the_town_hall.game.utils;

import org.lwjgl.glfw.GLFW;

public class Time {

    private double lastFrame = 0;
    private double currentFrame;
    private double deltaTime;

    public void onFrame() {
        this.currentFrame = GLFW.glfwGetTime();
        this.deltaTime = currentFrame - lastFrame;
        this.lastFrame = currentFrame;
    }

    public double getDeltaTime() {
        return deltaTime;
    }
}
