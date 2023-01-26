package com.inside_the_town_hall.game.ui.graphical.screen;

import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.ui.graphical.GUIManager;
import com.inside_the_town_hall.game.ui.graphical.GUIScreen;

import java.awt.*;

public class GameScreen extends GUIScreen {

    @Override
    public void draw(boolean[] flags) {
        GUIManager.getInstance().drawBackground();
//        int divider = 64;
//        int height = GUIManager.getInstance().getHeight() / divider;
//        int width = GUIManager.getInstance().getWidth() / divider;
//        for(int x = 0; x<=divider; x++) {
//            for(int y = 0; y<=divider; y++) {
//                int posX = x * width;
//                int posY = y * height;
//                Color color = (x+y) % 2 == 0 ? new Color(0, 224, 255) : new Color(77, 121, 121);
//                GUIManager.getInstance().drawRect(posX, posY, width, height, color);
//            }
//        }
    }
}
