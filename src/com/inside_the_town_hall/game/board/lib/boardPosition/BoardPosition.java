package com.inside_the_town_hall.game.board.lib.boardPosition;

import com.inside_the_town_hall.game.board.lib.board.Board;

import java.util.Objects;

/**
 * Position on the game board
 *
 * @author NekroQuest
 */
public class BoardPosition {
    private int x;
    private int y;

    public BoardPosition(int x, int y) {
        boolean isValidPosition = this.setX(x) && this.setY(y);
        if(!isValidPosition) {
            new BoardPosition();
        }
    }

    private BoardPosition() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Basic setter with validation
     *
     * @param x the x coordinate
     * @return if the validation was successful
     */
    public boolean setX(int x) {
        if(x < Board.getInstance().getLayout().getBoardWidth()) {
            this.x = x;
        }
        return x < Board.getInstance().getLayout().getBoardWidth();
    }

    /**
     * Basic setter with validation
     *
     * @param y the y coordinate
     * @return if the validation was successful
     */
    public boolean setY(int y) {
        if(y < Board.getInstance().getLayout().getBoardHeight()) {
            this.y = y;
        }
        return y < Board.getInstance().getLayout().getBoardHeight();
    }

    // Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Overriding
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardPosition that = (BoardPosition) obj;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
