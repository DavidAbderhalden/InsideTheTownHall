package com.inside_the_town_hall.game.board.lib.boardItem;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.behavior.BoardItemType;
import com.inside_the_town_hall.game.board.lib.behavior.IBoardItemAction;
import com.inside_the_town_hall.game.board.lib.board.Board;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/**
 * Item wrapper class for usage of item in board and scheduler tasks
 *
 * @author NekroQuest
 */
public class BoardItem {
    private final UUID id;
    private final BoardPosition boardPosition;
    private final IBoardItemAction boardItemAction;
    private final Entity item;
    private final boolean passable;

    public BoardItem(BoardPosition initPos, Entity item, BoardItemType boardItemType, boolean passable) {
        this.id = UUID.randomUUID();
        this.boardPosition = initPos;
        this.item = item;
        this.boardItemAction = boardItemType.getBoardItemAction(this.id, this.item, this.boardPosition);
        this.passable = passable;
    }

    // TODO: Not every BoardItem can move etc.

    /**
     * Moves item to different position on board
     * Method is overloaded
     *
     * @param x the x coordinate on the board
     * @param y the y coordinate on the board
     * @return if piece could move to the position (position is valid)
     */
    public boolean moveTo(int x, int y) {
        return this.boardPosition.setX(x) && this.boardPosition.setY(y);
    }

    /**
     * Moves item to different position on board
     * Method is overloaded
     *
     * @param newPos the destination as object
     * @return if piece could move to the position (position is valid)
     */
    public boolean moveTo(BoardPosition newPos) {
        return moveTo(newPos.getX(), newPos.getY());
    }

    /**
     * Creates a scheduler task to move item to the destination position
     *
     * @param targetPos the position the item should pathfind to
     */
//    public void pathfindTo(BoardPosition targetPos) {
//        UUID actionId = UUID.randomUUID();
//        HashMap<BoardPosition, BoardPosition> path = this.pathfindingBehavior.pathfind(this.boardPosition, targetPos);
//        GameController.getInstance().getScheduler().createTimedTask(
//                () -> moveToTask(path, actionId),
//                item.getSpeed(),
//                path.size()
//        );
//        this.activeActions.add(actionId);
//    }

    /**
     * SCHEDULER ACTION
     *
     *
     * @param path hashmap of the pathfinding ({pos1, pos2}, {pos2, pos3} ...)
     * @param actionId id of the task action (used for canceling etc.)
     */
//    private void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId) {
//        if(cancelAction(actionId)) {
//            this.LOGGER.deepLog(LogMode.YELLOW, "SCHEDULER.TASK.ACTION.CANCEL", new HashMap<>(){{
//                put("TASK_ID", actionId.toString());
//            }});
//            return;
//        }
//        BoardPosition nextPosition = path.get(this.boardPosition);
//        moveTo(nextPosition);
//    }

    /**
     * SCHEDULER ACTION
     *
     * @param actionId id of the task action (used for canceling etc.)
     * @return has the action been canceled
     */
//    private boolean cancelAction(UUID actionId) {
//        return this.abortedActions.contains(actionId);
//    }

    /**
     * Get all the items on the board surrounding this one
     *
     * @return linked list of all items surrounding
     */
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

    /**
     * Tells if an item is diagonal beside this one
     *
     * @param item the item to check
     * @return if item is diagonal (neighbours - adjacent)
     */
    private boolean isDiagonal(BoardItem item) {
        return Math.abs(this.boardPosition.getX() - item.boardPosition.getX())
                + Math.abs(this.boardPosition.getY() - item.boardPosition.getY()) == 2;
    }

    /**
     * Tells if an item is adjacent beside this one
     *
     * @param item the item to check
     * @return if item is adjacent (neighbours - diagonal)
     */
    private boolean isAdjacent(BoardItem item) {
        return Math.abs(this.boardPosition.getX() - item.boardPosition.getX())
                + Math.abs(this.boardPosition.getY() - item.boardPosition.getY()) == 1;
    }

    /**
     * Cancels all ongoing actions
     */
//    public void abort() {
//        this.abortedActions.addAll(this.activeActions);
//        this.activeActions = new LinkedList<>();
//    }

    // Getter
    public BoardPosition getPosition() {
        return this.boardPosition;
    }

    public Entity getItem() {
        return item;
    }

    public UUID getUUIDId() {
        return id;
    }

//    public IPathfindingBehavior getPathfindingBehavior() {
//        return this.pathfindingBehavior;
//    }

    public IBoardItemAction action() {
        return this.boardItemAction;
    }

    public boolean isPassable() {
        return passable;
    }

    // Overriding
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BoardItem boardItem = (BoardItem) obj;
        return this.id == boardItem.getUUIDId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardPosition, item);
    }
}
