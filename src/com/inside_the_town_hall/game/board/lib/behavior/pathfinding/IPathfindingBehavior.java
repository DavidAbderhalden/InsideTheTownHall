package com.inside_the_town_hall.game.board.lib.behavior.pathfinding;

import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;

/**
 * Used for different pathfinding approaches
 *
 * @author NekroQuest
 */
public interface IPathfindingBehavior {

    HashMap<BoardPosition, BoardPosition> pathfind(BoardPosition start, BoardPosition destination);
}
