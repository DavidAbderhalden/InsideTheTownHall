package com.inside_the_town_hall.game.ui.graphical.font;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public interface IFont {

    float getTextWidth(String text);

    float getTextWidth(String text, float size);

    float getTextWidth(String text, float size, float spacing);

    float getHeight();

    float getHeight(float size);

    void drawString(String text, float x, float y);

    void drawString(String text, float x, float y, float size);

    void drawString(String text, float x, float y, float size, Color color);

    void drawString(String text, float x, float y, float size, Color color, Color background);

    void drawString(String text, float x, float y, float size, Color color, Color background, float spacing);

    void drawString(StyledCharacter[] chars, float x, float y, float size);

    String getName();

    String getVersion();

    record StyledCharacter(char character, float size, float spacing, Color textColor, Color backgroundColor) {}
}
