package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.IPathfindingBehavior;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;
import java.util.UUID;

public class SolidBoardItemAction implements IBoardItemAction {
    @Override
    public boolean cancelAction(UUID actionId) {
        return false;
    }

    @Override
    public void abort() {

    }

    @Override
    public IPathfindingBehavior getPathfindingBehavior() {
        return null;
    }

    @Override
    public void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId) {
    }

    @Override
    public void pathfindTo(BoardPosition targetPos) {

    }
}
