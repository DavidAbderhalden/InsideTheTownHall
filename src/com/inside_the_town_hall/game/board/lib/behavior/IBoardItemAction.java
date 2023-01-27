package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;
import java.util.UUID;

public interface IBoardItemAction {


    boolean cancelAction(UUID actionId);

    void abort();

    IPathfindingBehavior getPathfindingBehavior();

    void moveToTask(HashMap<BoardPosition, BoardPosition> path, UUID actionId);

    void pathfindTo(BoardPosition targetPos);
}
