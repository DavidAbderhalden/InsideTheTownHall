package com.inside_the_town_hall.game.ui.graphical.screen;

import com.inside_the_town_hall.game.board.entities.lib.path.Path;
import com.inside_the_town_hall.game.board.entities.lib.wizard.Wizard;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.GUIScreen;
import com.inside_the_town_hall.game.ui.graphical.object.lib.GUIObject;
import com.inside_the_town_hall.game.ui.graphical.object.lib.path.GUIPath;
import com.inside_the_town_hall.game.ui.graphical.object.lib.wizard.GUIWizard;

public class GameScreen extends GUIScreen {
    private int borderX;
    private int borderY;
    private int deltaSize;

    @Override
    public void init() {
        update();
    }

    @Override
    public void draw(boolean[] flags) {
        GUIManager.getInstance().drawBackground();
        boolean[] f = {};

        updateMovables();
        for (GUIObject object : super.objects) {
            object.draw(f);
        }
    }

    @Override
    public void update() {
        updateBoardSize();
        updateSolids();
        updateMovables();
    }

    private void updateBoardSize() {
        this.deltaSize = getDeltaSize();
        this.borderX = getBorderX(this.deltaSize);
        this.borderY = getBorderY(this.deltaSize);
    }

    private void updateSolids() {
        super.objects.removeIf((obj) -> obj instanceof GUIPath); // TODO: Change to all solids
        for (BoardItem item : Board.getInstance().getLayout().getItems()) {
            // TODO: Change to all solids
            if (item.getItem() instanceof Path) {
                int posX = item.getPosition().getX() * this.deltaSize + this.borderX;
                int posY = item.getPosition().getY() * this.deltaSize + this.borderY;
                super.objects.add(new GUIPath(posX, posY, this.deltaSize, this.deltaSize));
            }
        }
    }

    private void updateMovables() {
        super.objects.removeIf((obj) -> obj instanceof GUIWizard); // TODO: Change to all movable
        for (BoardItem item : Board.getInstance().getLayout().getItems()) {
            // TODO: Change to all movable
            if (item.getItem() instanceof Wizard) {
                int posX = item.getPosition().getX() * this.deltaSize + this.borderX;
                int posY = item.getPosition().getY() * this.deltaSize + this.borderY;
                super.objects.add(new GUIWizard(posX, posY, this.deltaSize, this.deltaSize));
            }
        }
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
