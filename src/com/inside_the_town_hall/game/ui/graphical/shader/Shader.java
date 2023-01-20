package com.inside_the_town_hall.game.ui.graphical.shader;

import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;
import com.inside_the_town_hall.game.io.FileHandler;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;

import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private String name;
    private int program, vs, fs;
    private GUIManager guiManager;
    private final LogHandler LOGGER = new LogHandler(Shader.class);

    public Shader(String name, GUIManager guiManager) {
        this.name = name;
        this.guiManager = guiManager;
        this.program = glCreateProgram();

        this.vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(this.vs, FileHandler.getInstance().readFile("asset/shaders/" + name + ".vs"));
        glCompileShader(this.vs);
        if (glGetShaderi(this.vs, GL_COMPILE_STATUS) != 1) throw new RuntimeException("shader.compile.failed\n" + glGetShaderInfoLog(vs));

        this.fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(this.fs, FileHandler.getInstance().readFile("asset/shaders/" + name + ".fs"));
        glCompileShader(this.fs);
        if (glGetShaderi(this.fs, GL_COMPILE_STATUS) != 1) throw new RuntimeException("shader.compile.failed");

        glAttachShader(this.program, this.vs);
        glAttachShader(this.program, this.fs);

        glBindAttribLocation(this.program, 0, "vertices");
        glBindAttribLocation(this.program, 1, "textures");

        glLinkProgram(this.program);
        if (glGetProgrami(this.program, GL_LINK_STATUS) != 1) throw new RuntimeException("shader.link.failed");
        glValidateProgram(this.program);
        if (glGetProgrami(this.program, GL_VALIDATE_STATUS) != 1) throw new RuntimeException("shader.validation.failed");
    }

    public String getName() {
        return name;
    }

    private void updateShaderParameters() {
        int location = glGetUniformLocation(this.program, "windowSize");
        if (location != -1) {
            glUniform2f(location, this.guiManager.getWidth(), this.guiManager.getHeight());
        }
    }

    public void setUniform(String name, int value) {
        int location = glGetUniformLocation(this.program, name);
        if (location != -1) {
            glUniform1i(location, value);
        }
    }

    public void bind() {
        glUseProgram(this.program);
        this.updateShaderParameters();
    }
}

