package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.HashMap;

public interface IPathfindingBehavior {

    HashMap<BoardPosition, BoardPosition> pathfind(BoardPosition start, BoardPosition destination);
}
