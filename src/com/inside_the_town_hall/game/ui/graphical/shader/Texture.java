package com.inside_the_town_hall.game.ui.graphical.shader;

import com.inside_the_town_hall.game.io.FileHandler;
import com.inside_the_town_hall.game.utils.BufferCreator;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {
    private int id, width, height;

    private static final Map<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            textures.put(name, new Texture(name));
        }
        return textures.get(name);
    }

    public static Texture createDynamicTexture(byte[] data) {
        return new Texture(data);
    }

    private Texture(byte[] bytes) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        ByteBuffer data = null;
        data = stbi_load_from_memory(BufferCreator.createByteBuffer(bytes), width, height, comp, 4);
        if (data == null) throw new RuntimeException("texture.load.failed");

        this.id = glGenTextures();
        this.width = width.get();
        this.height = height.get();

        glBindTexture(GL_TEXTURE_2D, this.id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }

    private Texture(String name) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        ByteBuffer data = null;
        data = stbi_load_from_memory(BufferCreator.createByteBuffer(FileHandler.getInstance().readFileAsBytes("asset/textures/" + name)), width, height, comp, 4);
        if (data == null) throw new RuntimeException("texture.load.failed");

        this.id = glGenTextures();
        this.width = width.get();
        this.height = height.get();

        glBindTexture(GL_TEXTURE_2D, this.id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }

    public void bind(int sampler) {
        if (sampler >= 0 && sampler <= 31) {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getAspectRation() {
        return (float) this.width / (float) this.height;
    }
}
