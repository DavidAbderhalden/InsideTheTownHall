package com.inside_the_town_hall.game.utils;

/**
 * Utility class, provides static method to build vertices
 *
 * @author NekroQuest
 */
public class VertexUtils {

    /**
     * Creates a 2D vertices array
     *
     * @param x      the X coordinates
     * @param y      the Y coordinates
     * @param width  width of the geometry
     * @param height height of the geometry
     * @return the geometry as a float of vertices
     */
    public static float[] getVertices(float x, float y, float width, float height) {
        return new float[]{
                x, y,
                x + width, y,
                x + width, y + height,
                x, y + height
        };
    }
}

