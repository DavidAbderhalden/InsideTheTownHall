package com.inside_the_town_hall.game.board.lib.layout;

import com.inside_the_town_hall.game.board.entities.lib.path.Path;
import com.inside_the_town_hall.game.board.lib.behavior.BoardItemType;
import com.inside_the_town_hall.game.board.lib.behavior.DefaultPathfinding;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class Layout {
    private final int boardHeight;
    private final int boardWidth;

    private final HashMap<BoardPosition, BoardItem> byPosition = new HashMap<>();
    private final HashMap<UUID, BoardItem> byId = new HashMap<>();

    public Layout(int height, int width) {
        this.boardHeight = height;
        this.boardWidth = width;
    }

    public void init() {
        for(int x = 0; x<this.boardWidth; x++) {
            for(int y = 0; y<this.boardHeight; y++) {
                add(new BoardItem(new BoardPosition(x, y), new Path(), BoardItemType.SOLID));
            }
        }
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

    public LinkedList<BoardItem> getItems() {
        LinkedList<BoardItem> items = new LinkedList<>();
        for(Map.Entry<UUID, BoardItem> entry : this.byId.entrySet()) {
            items.add(entry.getValue());
        }
        return items;
    }
}