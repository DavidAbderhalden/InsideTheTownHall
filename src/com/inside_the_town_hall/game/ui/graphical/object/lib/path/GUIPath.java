package com.inside_the_town_hall.game.ui.graphical.object.lib.path;

import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.UUID;

/**
 * Path Entity displayed in the GUI
 *
 * @author NekroQuest
 */
public class GUIPath extends GUIObject implements IGUIBoardItem {
    private final UUID itemId;

    public GUIPath(int x, int y, int width, int height, UUID itemId) {
        super(x, y, width, height);
        this.itemId = itemId;
    }

    /**
     * Draws the GUI path
     * @param flags if active
     */
    @Override
    public void draw(boolean[] flags) {
        boolean isActive = false;
        if(flags.length > 0) isActive = flags[0];
        Color color = isActive ? new Color(239, 217, 98, 255) : new Color(255, 241, 161, 255);
        GUIManager.getInstance().drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color);
    }

    /**
     * Pathfinds to path entity if right click
     * @param button the mouse button clicked
     * @param mods
     */
    @Override
    public void onMouseDown(int button, int mods) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
            GUIObject lastObject = GUIManager.getInstance().getCurrentScreen().getLastActive();
            if (lastObject instanceof IGUIBoardItem) {
                BoardItem lastItem = Board.getInstance().getItem(((IGUIBoardItem) lastObject).getItemId());
                BoardItem thisItem = Board.getInstance().getItem(this.itemId);
                lastItem.action().pathfindTo(thisItem.getPosition());
            }
        }
    }

    // Getter
    @Override
    public UUID getItemId() {
        return this.itemId;
    }
}
