package com.inside_the_town_hall.game.ui.graphical.shader;

import com.inside_the_town_hall.game.utils.BufferCreator;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public abstract class Model {
    protected final Shader shader;

    protected int draw_count, v_id, i_id;

    public Model(Shader shader, float[] vertices) {
        this.shader = shader;

        final int[] indices = {0, 1, 2, 2, 3, 0}; //This field is used to translate a quad into two triangles
        this.draw_count = indices.length; //Because it's a quad and a quad consists of two triangles which have 3 vertices each

        this.v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(vertices), GL_DYNAMIC_DRAW);

        this.i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferCreator.createIntBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


    }

    public void setVertices(float[] vertices) {
        glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(vertices), GL_DYNAMIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

//        glBindVertexArray(0);
    }

    public abstract void render();
}
