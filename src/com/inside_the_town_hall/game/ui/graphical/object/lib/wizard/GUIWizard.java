package com.inside_the_town_hall.game.ui.graphical.object.lib.wizard;

import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;

import java.awt.*;
import java.util.UUID;

/**
 * Wizard Entity displayed in the GUI
 *
 * @author NekroQuest
 */
public class GUIWizard extends GUIObject implements IGUIBoardItem {
    private final UUID itemId;

    public GUIWizard(int x, int y, int width, int height, UUID itemId) {
        super(x, y, width, height);
        this.itemId = itemId;
    }

    /**
     * Draws the GUI wizard
     *
     * @param flags if active
     */
    @Override
    public void draw(boolean[] flags) {
        boolean isActive = false;
        if (flags.length > 0) isActive = flags[0];
        Color color = isActive ? new Color(48, 175, 26) : new Color(71, 135, 239, 255);
        GUIManager.getInstance().drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), color);
    }

    @Override
    public void onKeyDown(int keycode, int scancode, int mods) {
        BoardItem item = Board.getInstance().getLayout().getItem(this.itemId);
        if (GameController.getProperties().MOVE_UP() == keycode) {
            item.action().moveTo(new BoardPosition(item.getPosition().getX(), item.getPosition().getY()-1));
        } else if (GameController.getProperties().MOVE_LEFT() == keycode) {
            item.action().moveTo(new BoardPosition(item.getPosition().getX()-1, item.getPosition().getY()));
        } else if (GameController.getProperties().MOVE_DOWN() == keycode) {
            item.action().moveTo(new BoardPosition(item.getPosition().getX(), item.getPosition().getY()+1));
        } else if (GameController.getProperties().MOVE_RIGHT() == keycode) {
            item.action().moveTo(new BoardPosition(item.getPosition().getX()+1, item.getPosition().getY()));
        }
    }

    // Getter
    @Override
    public UUID getItemId() {
        return this.itemId;
    }
}
