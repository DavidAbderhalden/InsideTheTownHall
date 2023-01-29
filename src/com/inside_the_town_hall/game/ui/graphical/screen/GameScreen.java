package com.inside_the_town_hall.game.ui.graphical.screen;

import com.inside_the_town_hall.game.board.entities.lib.door.Door;
import com.inside_the_town_hall.game.board.entities.lib.path.Path;
import com.inside_the_town_hall.game.board.entities.lib.wall.Wall;
import com.inside_the_town_hall.game.board.entities.lib.wizard.Wizard;
import com.inside_the_town_hall.game.board.lib.behavior.MovableBoardItemAction;
import com.inside_the_town_hall.game.board.lib.behavior.SolidBoardItemAction;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.GUIScreen;
import com.inside_the_town_hall.game.ui.graphical.behavior.IGUIBoardItem;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;
import com.inside_the_town_hall.game.ui.graphical.object.lib.door.GUIDoor;
import com.inside_the_town_hall.game.ui.graphical.object.lib.path.GUIPath;
import com.inside_the_town_hall.game.ui.graphical.object.lib.wall.GUIWall;
import com.inside_the_town_hall.game.ui.graphical.object.lib.wizard.GUIWizard;

import java.util.LinkedList;

/**
 * The main screen of the game with board etc.
 *
 * @author NekroQuest
 */
public class GameScreen extends GUIScreen {
    private int borderX;
    private int borderY;
    private int deltaSize;

    /**
     * Creates all the BoardItems of the Board Layout in the GUI
     */
    @Override
    public void init() {
        LinkedList<GUIObject> paths = new LinkedList<>();
        LinkedList<GUIObject> walls = new LinkedList<>();
        LinkedList<GUIObject> wizards = new LinkedList<>();
        LinkedList<GUIObject> doors = new LinkedList<>();

        for (BoardItem item : Board.getInstance().getLayout().getItems()) {
            if (item.getItem() instanceof Wizard) {
                wizards.add(new GUIWizard(getPosX(item), getPosY(item), this.deltaSize, this.deltaSize, item.getUUIDId()));
            } else if (item.getItem() instanceof Path) {
                paths.add(new GUIPath(getPosX(item), getPosY(item), this.deltaSize, this.deltaSize, item.getUUIDId()));
            } else if (item.getItem() instanceof Wall) {
                walls.add(new GUIWall(getPosX(item), getPosY(item), this.deltaSize, this.deltaSize, item.getUUIDId()));
            } else if (item.getItem() instanceof Door) {
                doors.add(new GUIDoor(getPosX(item), getPosY(item), this.deltaSize, this.deltaSize, item.getUUIDId(), ((Door) item.getItem()).getDirection()));
            }
        }

        // Layers top is the lowest layer
        LinkedList<LinkedList<GUIObject>> layers = new LinkedList<>() {{
            add(paths);
            add(walls);
            add(doors);
            add(wizards);
        }};
        addLayers(layers);
    }

    /**
     * Adds the object in correct order to object list
     * @param layers sorted list
     */
    private void addLayers(LinkedList<LinkedList<GUIObject>> layers) {
        for (LinkedList<GUIObject> layer : layers) {
            super.objects.addAll(layer);
        }
    }

    /**
     * Draws all the objects of the GUI Screen
     * @param flags if object is active
     */
    @Override
    public void draw(boolean[] flags) {
        GUIManager.getInstance().drawBackground();
        updateMovables();
        for (GUIObject object : super.objects) {
            object.draw(new boolean[]{super.getActive() == object});
        }
    }

    /**
     * Occurs on resizing of the board
     */
    @Override
    public void update() {
        updateBoardSize();
        updateSolids();
        updateMovables();
    }

    /**
     * Updates dimensions of the GUI
     */
    private void updateBoardSize() {
        this.deltaSize = getDeltaSize();
        this.borderX = getBorderX(this.deltaSize);
        this.borderY = getBorderY(this.deltaSize);
    }

    /**
     * Updates all positional data of solid items
     */
    private void updateSolids() {
        super.objects.forEach(obj -> {
            if (obj instanceof IGUIBoardItem) {
                BoardItem item = Board.getInstance().getItem(((IGUIBoardItem) obj).getItemId());
                if (item.action() instanceof SolidBoardItemAction) {
                    obj.setX(getPosX(item));
                    obj.setY(getPosY(item));
                    obj.setHeight(this.deltaSize);
                    obj.setWidth(this.deltaSize);
                }
            }
        });
    }

    /**
     * Updates all positional data of movable items
     */
    private void updateMovables() {
        super.objects.forEach(obj -> {
            if (obj instanceof IGUIBoardItem) {
                BoardItem item = Board.getInstance().getItem(((IGUIBoardItem) obj).getItemId());
                if (item.action() instanceof MovableBoardItemAction) {
                    obj.setX(getPosX(item));
                    obj.setY(getPosY(item));
                    obj.setHeight(this.deltaSize);
                    obj.setWidth(this.deltaSize);
                }
            }
        });
    }

    // Getter
    private int getPosX(BoardItem item) {
        return item.getPosition().getX() * this.deltaSize + this.borderX;
    }

    private int getPosY(BoardItem item) {
        return item.getPosition().getY() * this.deltaSize + this.borderY;
    }

    private int getBorderX(int deltaSize) {
        int centerX = GUIManager.getInstance().getWidth() / 2;
        return centerX - (deltaSize * Board.getInstance().getLayout().getBoardWidth() / 2);
    }

    private int getBorderY(int deltaSize) {
        int centerY = GUIManager.getInstance().getHeight() / 2;
        return centerY - (deltaSize * Board.getInstance().getLayout().getBoardHeight() / 2);
    }

    private int getDeltaSize() {
        return (
                Math.min(GUIManager.getInstance().getHeight(), GUIManager.getInstance().getWidth())
                        / Math.min(Board.getInstance().getLayout().getBoardHeight(), Board.getInstance().getLayout().getBoardWidth()))
                - Math.min(GUIManager.getInstance().getHeight(), GUIManager.getInstance().getWidth()) / 200;
    }
}
