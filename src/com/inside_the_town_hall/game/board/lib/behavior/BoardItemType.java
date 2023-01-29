package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.DefaultPathfinding;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.UUID;

public enum BoardItemType {
    MOVABLE("movable"){
        @Override
        public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
            return new MovableBoardItemAction(itemId, entity, boardPosition, new DefaultPathfinding());
        }
    },
    SOLID("solid"){
        @Override
        public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
            return new SolidBoardItemAction(); // TODO: Finish Implementation
        }
    }
    ;

    private final String boardItemTypeString;

    BoardItemType(String boardItemTypeString) {
        this.boardItemTypeString = boardItemTypeString;
    }

    public static BoardItemType fromString(String boardItemTypeString) {
        for(BoardItemType type : BoardItemType.values()) {
            if (type.getAsString().equals(boardItemTypeString)) {
                return type;
            }
        } return null;
    }

    public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
        return null;
    }

    // Getter
    public String getAsString() {
        return boardItemTypeString;
    }
}
