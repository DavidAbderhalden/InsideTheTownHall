package com.inside_the_town_hall.game.board.entities.levels.entity.wizard;

import com.inside_the_town_hall.game.board.entities.levels.Level;

import java.util.HashMap;

/**
 * Wizard Level inheritance specifies level attributes
 *
 * @author NekroQuest
 */
public class WizardLevel extends Level {
    private static final HashMap<Integer, Integer> HITPOINTS = new HashMap<>() {{
        put(1, 30);
        put(2, 35);
        put(3, 40);
        put(4, 50);
    }};

    public WizardLevel(int level) {
        super(HITPOINTS, level, 4, 10);
    }
}
