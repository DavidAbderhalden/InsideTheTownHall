package com.inside_the_town_hall.game.board.lib.board;

import java.util.Objects;

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

    public boolean setX(int x) {
        if(x < Board.getInstance().getLayout().getBoardWidth()) {
            this.x = x;
        }
        return x < Board.getInstance().getLayout().getBoardWidth();
    }

    public boolean setY(int y) {
        if(y < Board.getInstance().getLayout().getBoardHeight()) {
            this.y = y;
        }
        return y < Board.getInstance().getLayout().getBoardHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

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
