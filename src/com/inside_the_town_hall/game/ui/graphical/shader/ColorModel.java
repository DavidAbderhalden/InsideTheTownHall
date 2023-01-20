package com.inside_the_town_hall.game.ui.graphical.shader;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.utils.BufferCreator;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class ColorModel extends Model {
    private int c_id;

    public ColorModel(float[] vertices, byte[] colors) {
        super(GUIManager.getInstance().getColorShader(), vertices);

        this.c_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.c_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(colors), GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void setColor(Color color) {
        byte[] colors = new byte[] {
                (byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)color.getAlpha(),
                (byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)color.getAlpha(),
                (byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)color.getAlpha(),
                (byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue(), (byte)color.getAlpha()
        };
        this.setColor(colors);
    }

    public void setColor(byte[] colors) {
        glBindBuffer(GL_ARRAY_BUFFER, this.c_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(colors), GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void render() {
        this.shader.bind();
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, this.c_id);
        glVertexAttribPointer(1, 4, GL_UNSIGNED_BYTE, true, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
        glDrawElements(GL_TRIANGLES, this.draw_count, GL_UNSIGNED_INT, 0);


        //Unbind buffers
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        //Disable
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
}
