package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.IPathfindingBehavior;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;
import java.util.UUID;

/**
 * Solid board item actions
 *
 * @author NekroQuest
 */
public class SolidBoardItemAction implements IBoardItemAction {

    /**
     * Cancels an action
     * @param actionId the id of the action
     * @return if the action was canceled
     */
    @Override
    public boolean cancelAction(UUID actionId) {
        return false;
    }

    /**
     * Cancels all actions
     */
    @Override
    public void abort() {

    }

    /**
     * Moves board item in scheduler
     * @param path the HashMap with the paths to move
     * @param actionId the id of the moving action
     */
    @Override
    public void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId) {
    }

    /**
     * Pathfind and move the board item to the destination
     * @param targetPos position to pathfind to
     */
    @Override
    public void pathfindTo(BoardPosition targetPos) {

    }

    // Getter
    @Override
    public IPathfindingBehavior getPathfindingBehavior() {
        return null;
    }
}
