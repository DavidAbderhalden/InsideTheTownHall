package com.inside_the_town_hall.game.board.lib.board;

import com.inside_the_town_hall.game.board.items.Item;
import com.inside_the_town_hall.game.board.lib.behavior.IPathfindingBehavior;
import com.inside_the_town_hall.game.controlls.GameController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

public class BoardItem {
    private final UUID id;
    private final BoardPosition boardPosition;
    private final Item item;
    private final IPathfindingBehavior pathfindingBehavior;

    public BoardItem(BoardPosition initPos, Item item, IPathfindingBehavior pathfindingBehavior) {
        this.id = UUID.randomUUID();
        this.boardPosition = initPos;
        this.item = item;
        this.pathfindingBehavior = pathfindingBehavior;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardItem boardItem = (BoardItem) obj;
        // TODO: Override BoardPosition / Item
        return this.id == boardItem.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardPosition, item);
    }

    public boolean moveTo(int x, int y) {
        return this.boardPosition.setX(x) && this.boardPosition.setY(y);
    }

    public boolean moveTo(BoardPosition newPos) {
        return moveTo(newPos.getX(), newPos.getY());
    }

    public void pathfindTo(BoardPosition targetPos) {
        HashMap<BoardPosition, BoardPosition> path = this.pathfindingBehavior.pathfind(this.boardPosition, targetPos);
        GameController.getInstance().getScheduler().createTimedTask(
                () -> moveToTargetTask(path, this.id),
                item.getSpeed(),
                path.size()
        );
    }

    // Scheduler task Runnable
    private void moveToTargetTask(HashMap<BoardPosition, BoardPosition> path, UUID targetId) {
        BoardItem targetItem = Board.getInstance().getItem(targetId);
        BoardPosition nextPosition = path.get(targetItem.getPosition());
        targetItem.moveTo(nextPosition);
    }

    public LinkedList<BoardItem> getNeighbours() {
        LinkedList<BoardItem> neighbours = new LinkedList<>();
        int targetX = this.boardPosition.getX();
        int targetY = this.boardPosition.getY();
        for (int x = targetX - 1; x <= targetX + 1; x++) {
            for (int y = targetY - 1; y <= targetY + 1; y++) {
                neighbours.add(Board.getInstance().getItem(x, y));
            }
        }
        return neighbours;
    }

    private boolean isDiagonal(BoardItem item) {
        return Math.abs(this.boardPosition.getX() - item.boardPosition.getX())
                + Math.abs(this.boardPosition.getY() - item.boardPosition.getY()) == 2;
    }

    private boolean isAdjacent(BoardItem item) {
        return Math.abs(this.boardPosition.getX() - item.boardPosition.getX())
                + Math.abs(this.boardPosition.getY() - item.boardPosition.getY()) == 1;
    }

    public BoardPosition getPosition() {
        return this.boardPosition;
    }

    public Item getItem() {
        return item;
    }

    public UUID getId() {
        return id;
    }

    public IPathfindingBehavior getPathfindingBehavior() {
        return this.pathfindingBehavior;
    }
}
