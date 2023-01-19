package com.inside_the_town_hall.game.translation;

public enum Language {
    EN_UK("en_uk");

    private final String languageType;

    Language(String type) {
        this.languageType = type;
    }

    public static Language fromString(String type) {
        for(Language lang : Language.values()) {
            if(lang.asString().equals(type)) return lang;
        } return null;
    }

    public String asString() {
        return languageType;
    }
}
