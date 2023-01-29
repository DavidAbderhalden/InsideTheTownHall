package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.IPathfindingBehavior;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;
import java.util.UUID;

/**
 * Actions a specific item on the board can take
 *
 * @author NekroQuest
 */
public interface IBoardItemAction {

    /**
     * Cancels an action of the item
     * @param actionId the id of the action
     * @return if the action has been canceled or even existed
     */
    boolean cancelAction(UUID actionId);

    /**
     * Cancels all of the actions
     */
    void abort();

    IPathfindingBehavior getPathfindingBehavior();

    /**
     * Used by the Scheduler
     * @param path the HashMap with the paths to move
     * @param actionId the id of the moving action
     */
    void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId);

    void pathfindTo(BoardPosition targetPos);
}
