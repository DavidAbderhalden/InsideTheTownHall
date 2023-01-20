package com.inside_the_town_hall.game.utils;

public class VertexUtils {

    public static float[] getVertices(float x, float y, float width, float height) {
        return new float[]{
                x, y,
                x + width, y,
                x + width, y + height,
                x, y + height
        };
    }
}

