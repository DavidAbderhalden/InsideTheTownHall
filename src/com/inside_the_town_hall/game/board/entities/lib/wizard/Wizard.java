package com.inside_the_town_hall.game.board.entities.lib.wizard;

import com.inside_the_town_hall.game.board.entities.Entity;
import com.inside_the_town_hall.game.board.entities.levels.entity.wizard.WizardLevel;

/**
 * Wizard Entity
 *
 * @author NekroQuest
 */
public class Wizard extends Entity {

    public Wizard() {
        super(new WizardLevel(1));
    }
}
