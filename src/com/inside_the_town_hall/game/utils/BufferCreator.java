package com.inside_the_town_hall.game.utils;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Utility class to return different types of buffers
 *
 * @author NekroQuest
 */
public class BufferCreator {
    /**
     * Creates a float buffer
     *
     * @param data float array data
     * @return a float buffer with data
     */
    public static FloatBuffer createFloatBuffer(float[] data) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a double buffer
     *
     * @param data double array data
     * @return a double buffer with data
     */
    public static DoubleBuffer createDoubleBuffer(double[] data) {
        DoubleBuffer buffer = org.lwjgl.BufferUtils.createDoubleBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates an int buffer
     *
     * @param data int array data
     * @return an int buffer with data
     */
    public static IntBuffer createIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    /**
     * Creates a byte buffer
     *
     * @param data byte array data
     * @return a byte buffer with data
     */
    public static ByteBuffer createByteBuffer(byte[] data) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
