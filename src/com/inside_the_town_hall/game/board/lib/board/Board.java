package com.inside_the_town_hall.game.board.lib.board;

import com.inside_the_town_hall.game.board.lib.layout.Layout;

import java.util.UUID;

public class Board {
    private static Board instance;
    private final Layout boardLayout;

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board(32, 32);
        }
        return instance;
    }

    private Board(int boardHeight, int boardWidth) {
        this.boardLayout = new Layout(boardHeight, boardWidth);
    }

    public Layout getLayout() {
        return boardLayout;
    }

    // Delegation
    public BoardItem getItem(int x, int y) {
        return this.boardLayout.getItem(x, y);
    }

    public BoardItem getItem(UUID itemId) {
        return this.boardLayout.getItem(itemId);
    }
}
