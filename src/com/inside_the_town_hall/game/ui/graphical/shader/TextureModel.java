package com.inside_the_town_hall.game.ui.graphical.shader;

import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.utils.BufferCreator;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TextureModel extends Model {
    private int t_id;

    private final Texture texture;

    /**
     * @param texture
     * @param vertices 4 vertices for a quad. Start in the top left corner and go clockwise.
     * @param tex_coords 4 vertices for the position in the texture. Start in the top left corner and go clockwise.
     */
    public TextureModel(Texture texture, float[] vertices, float[] tex_coords) {
        super(GUIManager.getInstance().getDefaultShader(), vertices);
        this.texture = texture;

        this.t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.t_id);
        glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(tex_coords), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    public void render() {
        this.shader.bind();
        this.shader.setUniform("sampler", 0);
        this.texture.bind(0);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, this.t_id);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
        glDrawElements(GL_TRIANGLES, this.draw_count, GL_UNSIGNED_INT, 0);


        //Unbind buffers
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        //Disable
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

    }

    public Texture getTexture() {
        return texture;
    }
}
