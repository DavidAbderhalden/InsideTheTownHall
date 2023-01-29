package com.inside_the_town_hall.game.ui.graphical.object.lib.door;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.lib.behavior.*;
import com.inside_the_town_hall.game.board.lib.boardPosition.BoardPosition;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;

import java.awt.*;
import java.util.UUID;

public enum Direction {
    NORTH("north") {
        @Override
        public void drawDoor(int x, int y, int width, int height, Color color1, Color color2) {
            int newHeight = height / 4;
            GUIManager.getInstance().drawRect(x, y, width, height, color2);
            GUIManager.getInstance().drawRect(x, y, width, newHeight, color1);
        }
    },
    EAST("east") {
        @Override
        public void drawDoor(int x, int y, int width, int height, Color color1, Color color2) {
            int newWidth = width / 4;
            int newX = x + width - newWidth;
            GUIManager.getInstance().drawRect(x, y, width, height, color2);
            GUIManager.getInstance().drawRect(newX, y, newWidth, height, color1);
        }
    },
    SOUTH("south") {
        @Override
        public void drawDoor(int x, int y, int width, int height, Color color1, Color color2) {
            int newHeight = height / 4;
            int newY = y + height - newHeight;
            GUIManager.getInstance().drawRect(x, y, width, height, color2);
            GUIManager.getInstance().drawRect(x, newY, width, newHeight, color1);
        }
    },
    WEST("west"){
        @Override
        public void drawDoor(int x, int y, int width, int height, Color color1, Color color2) {
            int newWidth = width / 4;
            GUIManager.getInstance().drawRect(x, y, width, height, color2);
            GUIManager.getInstance().drawRect(x, y, newWidth, height, color1);
        }
    };

    private final String directionString;

    Direction(String directionString) {
        this.directionString = directionString;
    }

    public static BoardItemType fromString(String boardItemTypeString) {
        for(BoardItemType type : BoardItemType.values()) {
            if (type.getAsString().equals(boardItemTypeString)) {
                return type;
            }
        } return null;
    }

    public void drawDoor(int x, int y, int width, int height, Color color1, Color color2) {
        GUIManager.getInstance().drawRect(x, y, width, height, color1);
    }

    // Getter
    public String getAsString() {
        return directionString;
    }
}
