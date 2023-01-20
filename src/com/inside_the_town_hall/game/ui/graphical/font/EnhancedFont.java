package com.inside_the_town_hall.game.ui.graphical.font;

import com.inside_the_town_hall.game.io.FileHandler;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.shader.Texture;
import com.inside_the_town_hall.game.utils.BufferCreator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;

public class EnhancedFont implements IFont {

    private Map<Character, Glyph> glyphs;

    private Texture atlas;

    private String name;
    private String version;
    private boolean ignoreCase;
    private int spaceWidth;
    private int height;
    private int defaultSpacing;

    public static EnhancedFont loadFromResource(String name) {
        try {
            byte[] zip = FileHandler.getInstance().readFileAsBytes("asset/fonts/" + name + ".bmf");
            var byteStream = new ByteArrayInputStream(zip);
            ZipInputStream zipInputStream = new ZipInputStream(byteStream);

            byte[] fontinfo = null;
            byte[] atlas = null;

            while (true) {
                ZipEntry entry = zipInputStream.getNextEntry();

                if (entry != null) {
                    if (entry.getName().equals("fontinfo.json")) {
                        fontinfo = zipInputStream.readAllBytes();
                    } else if (entry.getName().equals("glyphs.png")) {
                        atlas = zipInputStream.readAllBytes();
                    }
                } else break;
            }
            if (fontinfo == null || atlas == null) throw new Exception();
            String json = new String(fontinfo, StandardCharsets.UTF_8);

            return loadFontinfo(json, atlas);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("font.load.failed");
        }
    }

    private static EnhancedFont loadFontinfo(String json, byte[] atlas) {
        EnhancedFont font = new EnhancedFont();
        font.atlas = Texture.createDynamicTexture(atlas);
        JSONObject jsonObj = new JSONObject(json);

        font.name = jsonObj.getString("name");
        font.version = jsonObj.getString("version");
        font.ignoreCase = jsonObj.getBoolean("ignoreCase");
        font.spaceWidth = jsonObj.getInt("spaceWidth");
        font.height = jsonObj.getInt("height");
        font.defaultSpacing = jsonObj.getInt("spacing");

        JSONArray arr = jsonObj.getJSONArray("glyphs");
        for (Object obj : arr) {
            JSONObject glyphObj = (JSONObject) obj;
            Glyph glyph = new Glyph(glyphObj.getInt("x"), glyphObj.getInt("y"), glyphObj.getInt("width"), font.height, font.atlas.getWidth(), font.atlas.getHeight());
            font.glyphs.put(font.ignoreCase ? Character.toUpperCase(glyphObj.getString("char").charAt(0)) : glyphObj.getString("char").charAt(0), glyph);
        }
        return font;
    }

    private EnhancedFont() {
        this.glyphs = new HashMap<>();
    }

    @Override
    public float getTextWidth(String text) {
        return this.getTextWidth(text, 2);
    }

    @Override
    public float getTextWidth(String text, float size) {
        return this.getTextWidth(text, size, this.defaultSpacing);
    }

    @Override
    public float getTextWidth(String text, float size, float spacing) {
        float width = 0;
        for (char c : text.toCharArray()) {
            Glyph glyph = this.glyphs.get(Character.toUpperCase(c));
            if(glyph != null) {
                width+=(glyph.width + spacing);
            } else width+=(this.spaceWidth+spacing);
        }
        return width*size;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getHeight(float size) {
        return this.height*size;
    }

    @Override
    public void drawString(String text, float x, float y) {
        this.drawString(text, x, y, 2);
    }

    @Override
    public void drawString(String text, float x, float y, float size) {
        this.drawString(text, x, y, size, Color.BLACK);
    }

    @Override
    public void drawString(String text, float x, float y, float size, Color color) {
        this.drawString(text, x, y, size, color, new Color(255, 255, 255, 0));
    }

    @Override
    public void drawString(String text, float x, float y, float size, Color color, Color background) {
        this.drawString(text, x, y, size, color, background, this.defaultSpacing);
    }

    @Override
    public void drawString(String text, float x, float y, float size, Color color, Color background, float spacing) {
        GUIManager.getInstance().getFontShader().bind();
        GUIManager.getInstance().getFontShader().setUniform("sampler", 0);
        this.atlas.bind(0);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        float xOffset = x;
        for (Character c : text.toCharArray()) {
            StyledCharacter styledCharacter = new StyledCharacter(c, size, spacing, color, background);
            xOffset+=drawChar(styledCharacter, xOffset, y);
        }
//        glyphs.get('A').render(0, 0, 20, 20, Color.BLACK, new Color(0, 0, 0, 0));

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }

    @Override
    public void drawString(StyledCharacter[] chars, float x, float y, float size) {
        GUIManager.getInstance().getFontShader().bind();
        GUIManager.getInstance().getFontShader().setUniform("sampler", 0);
        this.atlas.bind(0);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        float xOffset = x;
        for (StyledCharacter styledCharacter : chars) {
            xOffset+=drawChar(styledCharacter, xOffset, y);
        }

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }


    private int drawChar(StyledCharacter styledCharacter, float x, float y) {
        Glyph glyph = this.glyphs.get(this.ignoreCase ? Character.toUpperCase(styledCharacter.character()) : styledCharacter.character());
        if (glyph != null) {
            final int width = (int) (glyph.getWidth()*styledCharacter.size());
            glyph.render(x, y, width, (int) (this.height*styledCharacter.size()), styledCharacter.textColor(), styledCharacter.backgroundColor());
            return (int) (width+styledCharacter.spacing()*styledCharacter.size());
        } else {
            return (int) ((this.spaceWidth + styledCharacter.spacing())*styledCharacter.size());
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    private static final int[] indices = {0, 1, 2, 2, 3, 0};
    private static final int draw_count = indices.length;
    private static final class Glyph {
        private final int x;
        private final int y;
        private final int width;

        private final int height;

        private int v_id, t_id, i_id, c_id, b_id;

        public Glyph(int x, int y, int width, int height, int atlasWidth, int atlasHeight) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            createModel(atlasWidth, atlasHeight);
        }

        public void render(float x, float y, int width, int height, Color color, Color background) {
            byte[] color_rgb = new byte[] {
                    (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(),
                    (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(),
                    (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(),
                    (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(),
            };
            byte[] background_rgba = new byte[] {
                    (byte) background.getRed(), (byte) background.getGreen(), (byte) background.getBlue(), (byte) background.getAlpha(),
                    (byte) background.getRed(), (byte) background.getGreen(), (byte) background.getBlue(), (byte) background.getAlpha(),
                    (byte) background.getRed(), (byte) background.getGreen(), (byte) background.getBlue(), (byte) background.getAlpha(),
                    (byte) background.getRed(), (byte) background.getGreen(), (byte) background.getBlue(), (byte) background.getAlpha(),
            };

            glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(new float[] {
                    x, y,
                    x+width, y,
                    x+width, y+height,
                    x, y+height,
            }), GL_DYNAMIC_DRAW);
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, this.t_id);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, this.c_id);
            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(color_rgb), GL_DYNAMIC_DRAW);
            glVertexAttribPointer(2, 3, GL_UNSIGNED_BYTE, true, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, this.b_id);
            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(background_rgba), GL_DYNAMIC_DRAW);
            glVertexAttribPointer(3, 4, GL_UNSIGNED_BYTE, true, 0, 0);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
            glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);

            //Unbind buffers
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        private void createModel(int atlasWidth, int atlasHeight) {
            final float pxlWidth = 1.0f / atlasWidth;
            final float pxlHeight = 1.0f / atlasHeight;
            this.v_id = glGenBuffers();

            this.t_id = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, this.t_id);
//            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(VertexUtils.getVertices(this.x, this.y, this.width, this.height)), GL_STATIC_DRAW);
            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createFloatBuffer(new float[] {
                    pxlWidth * x,               pxlHeight * y,
                    pxlWidth * (x+width),       pxlHeight * y,
                    pxlWidth * (x+width),       pxlHeight * (y+height),
                    pxlWidth * x,               pxlHeight * (y+height)
            }), GL_STATIC_DRAW);

            this.i_id = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferCreator.createIntBuffer(indices), GL_STATIC_DRAW);

            this.c_id = glGenBuffers();
//            glBindBuffer(GL_ARRAY_BUFFER, this.c_id);
//            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(new byte[] {
//                    (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0,
//            }), GL_DYNAMIC_DRAW);

            this.b_id = glGenBuffers();
//            glBindBuffer(GL_ARRAY_BUFFER, this.b_id);
//            glBufferData(GL_ARRAY_BUFFER, BufferCreator.createByteBuffer(new byte[] {
//                    (byte)0, (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0, (byte)0,
//                    (byte)0, (byte)0, (byte)0, (byte)0,
//            }), GL_DYNAMIC_DRAW);
//
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
//            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

    }
}

