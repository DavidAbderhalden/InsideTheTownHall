package com.inside_the_town_hall.game.board.lib.layout;

import com.inside_the_town_hall.game.board.entities.lib.door.Door;
import com.inside_the_town_hall.game.board.entities.lib.path.Path;
import com.inside_the_town_hall.game.board.entities.lib.wall.Wall;
import com.inside_the_town_hall.game.board.lib.behavior.BoardItemType;
import com.inside_the_town_hall.game.board.lib.boardItem.BoardItem;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.ui.graphical.object.lib.door.Direction;

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
        // path
        for (int x = 0; x < this.boardWidth; x++) {
            for (int y = 0; y < this.boardHeight; y++) {
                add(new BoardItem(new BoardPosition(x, y), new Path(), BoardItemType.SOLID, true));
            }
        }
        // wall
        int vertical1 = (this.boardWidth - 2) / 3;
        int vertical2 = vertical1 * 2 + 1;
        for (int y = 0; y < this.boardHeight; y++) {
            // Walls around
            if ((y + 1) % (this.boardHeight / 4) == 0 ||y == 0) {
                for (int x = 0; x < this.boardWidth; x++) {
                    // Horizontal walls
                    if(y == 0 || y == this.boardHeight-1 || x <= vertical1 || x >= vertical2) {
                        remove(new BoardPosition(x, y));
                        add(new BoardItem(new BoardPosition(x, y), new Wall(), BoardItemType.SOLID, false));
                    }
                }
            } else {
                // Vertical walls
                remove(new BoardPosition(0, y));
                remove(new BoardPosition(this.boardHeight-1, y));
                remove(new BoardPosition(vertical1, y));
                remove(new BoardPosition(vertical2, y));
                add(new BoardItem(new BoardPosition(0, y), new Wall(), BoardItemType.SOLID, false));
                add(new BoardItem(new BoardPosition(this.boardHeight-1, y), new Wall(), BoardItemType.SOLID, false));
                add(new BoardItem(new BoardPosition(vertical1, y), new Wall(), BoardItemType.SOLID, false));
                add(new BoardItem(new BoardPosition(vertical2, y), new Wall(), BoardItemType.SOLID, false));
            }
        }
        // door
        // TODO: Generic Generation
        remove(new BoardPosition(10, 10));
        remove(new BoardPosition(3, 7));
        remove(new BoardPosition(3, 15));
        remove(new BoardPosition(10, 19));
        remove(new BoardPosition(10, 29));
        remove(new BoardPosition(21, 5));
        remove(new BoardPosition(21, 20));
        remove(new BoardPosition(29, 7));
        remove(new BoardPosition(24, 15));
        remove(new BoardPosition(24, 23));
        remove(new BoardPosition(15, 31));
        remove(new BoardPosition(16, 31));
        add(new BoardItem(new BoardPosition(10, 10), new Door(Direction.EAST), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(3, 7), new Door(Direction.SOUTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(3, 15), new Door(Direction.NORTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(10, 19), new Door(Direction.EAST), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(10, 29), new Door(Direction.EAST), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(21, 5), new Door(Direction.WEST), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(21, 20), new Door(Direction.WEST), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(29, 7), new Door(Direction.SOUTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(24, 15), new Door(Direction.SOUTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(24, 23), new Door(Direction.NORTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(15, 31), new Door(Direction.SOUTH), BoardItemType.SOLID, true));
        add(new BoardItem(new BoardPosition(16, 31), new Door(Direction.SOUTH), BoardItemType.SOLID, true));

    }

    public BoardItem getItem(int x, int y) {
        return this.byPosition.get(new BoardPosition(x, y));
    }

    public BoardItem getItem(UUID itemId) {
        return this.byId.get(itemId);
    }

    public boolean add(BoardItem item) {
        this.byPosition.put(item.getPosition(), item);
        this.byId.put(item.getUUIDId(), item);
        return true;
    }

    public void remove(BoardPosition position) {
        this.byPosition.remove(position);
        this.byId.values().removeIf(boardItem -> boardItem.getPosition().equals(position));
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public LinkedList<BoardItem> getItems() {
        LinkedList<BoardItem> items = new LinkedList<>();
        for (Map.Entry<UUID, BoardItem> entry : this.byId.entrySet()) {
            items.add(entry.getValue());
        }
        return items;
    }

    public HashMap<UUID, BoardItem> getById() {
        return this.byId;
    }
}