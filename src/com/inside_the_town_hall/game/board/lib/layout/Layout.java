package com.inside_the_town_hall.game.board.lib.layout;

import com.inside_the_town_hall.game.board.lib.board.BoardItem;
import com.inside_the_town_hall.game.board.lib.board.BoardPosition;

import java.util.HashMap;
import java.util.UUID;

public class Layout {
    private final int boardHeight;
    private final int boardWidth;

    private final HashMap<BoardPosition, BoardItem> byPosition = new HashMap<>();
    private final HashMap<UUID, BoardItem> byId = new HashMap<>();

    public Layout(int height, int width) {
        this.boardHeight = height;
        this.boardWidth = width;
        init();
    }

    private void init() {
        // Initialize the first board
    }

    public BoardItem getItem(int x, int y) {
        return this.byPosition.get(new BoardPosition(x, y));
    }

    public BoardItem getItem(UUID itemId) {
        return this.byId.get(itemId);
    }

    public boolean add(BoardItem item) {
        this.byPosition.put(item.getPosition(), item);
        this.byId.put(item.getId(), item);
        return true;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }
}