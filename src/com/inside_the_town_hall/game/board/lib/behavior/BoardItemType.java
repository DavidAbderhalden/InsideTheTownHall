package com.inside_the_town_hall.game.board.lib.behavior;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.behavior.pathfinding.DefaultPathfinding;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;

import java.util.UUID;

/**
 * Differentiates between movable entities and solids
 *
 * @author NekroQuest
 */
public enum BoardItemType {
    MOVABLE("movable") {
        @Override
        public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
            return new MovableBoardItemAction(itemId, entity, boardPosition, new DefaultPathfinding());
        }
    },
    SOLID("solid") {
        @Override
        public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
            return new SolidBoardItemAction(); // TODO: Finish Implementation
        }
    };

    private final String boardItemTypeString;

    BoardItemType(String boardItemTypeString) {
        this.boardItemTypeString = boardItemTypeString;
    }

    /**
     * Creates Type form a string
     *
     * @param boardItemTypeString the string (movable / solid)
     * @return the BoardItemType object
     */
    public static BoardItemType fromString(String boardItemTypeString) {
        for (BoardItemType type : BoardItemType.values()) {
            if (type.getAsString().equals(boardItemTypeString)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Is being overridden
     * Movables and Solids have different actions
     *
     * @param itemId        id of the board item to use the action
     * @param entity        the entity itself
     * @param boardPosition the current position on the board
     * @return the specific action object
     */
    public IBoardItemAction getBoardItemAction(UUID itemId, Entity entity, BoardPosition boardPosition) {
        return null;
    }

    // Getter
    public String getAsString() {
        return boardItemTypeString;
    }
}
