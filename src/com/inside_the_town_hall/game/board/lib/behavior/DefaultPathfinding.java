package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.lib.board.BoardPosition;

import java.util.HashMap;

public class DefaultPathfinding implements IPathfindingBehavior {

    @Override
    public HashMap<BoardPosition, BoardPosition> pathfind(BoardPosition start, BoardPosition destination) {
        // TODO: Implement
        HashMap<BoardPosition, BoardPosition> test = new HashMap<>();
        test.put(start, new BoardPosition(1, 3));
        test.put(new BoardPosition(1, 3), new BoardPosition(1, 4));
        test.put(new BoardPosition(1, 4), new BoardPosition(2, 4));
        test.put(new BoardPosition(2, 4), destination);
        return test;
    }
}
