package com.inside_the_town_hall.game.translation;

import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;
import com.inside_the_town_hall.game.io.FileHandler;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Loads and translates languages used in the application
 * Is a Singleton
 *
 * @author NekroQuest
 */
public class LanguageManager {
    private static LanguageManager instance;
    private final HashMap<String, String> indices = new HashMap<>();
    private final LogHandler LOGGER = new LogHandler(LanguageManager.class);

    public static LanguageManager getInstance() {
        if(instance == null) {
            instance = new LanguageManager();
        } return instance;
    }

    /**
     * Used to load a new language to the application.
     * Will trigger the whole language loading process and
     * monitor it. Constantly logs the status.
     *
     * @param lang the chosen language enum
     */
    public void loadLang(Language lang) {
        String jsonString = FileHandler.getInstance().readFile(String.format("asset\\translations\\%s.json", lang.asString()));
        useLang(jsonString);
        LOGGER.deepLog(LogMode.GREEN, "CONFIG.ADAPT.LANG");
    }

    /**
     * Converts the file content to a json object and
     * finally the hashmap. If a language is already loaded,
     * the new one will overwrite the old language.
     *
     * @param jsonString loaded language file in form of a json string
     */
    private void useLang(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        for(int i = 0; i<jsonObject.names().length(); i++) {
            String key = jsonObject.names().getString(i);
            String value = jsonObject.get(jsonObject.names().getString(i)).toString();
            indices.put(key, value);
        }
    }

    /**
     * Outputs the value of a language variable.
     * Provides a use function without message formatting for
     * convenience.
     * @param key json property of the requested message
     * @return the message assigned to key
     */
    public String use(String key) {
        return use(key, new HashMap<>());
    }

    /**
     * Overloads use(String key) function. It outputs a formatted
     * value of a language variable (Replaces with regex).
     *
     * @param key json property of the requested message
     * @param map string interpolation, key as identifier & value as replacement
     * @return the formatted message assigned to key
     */
    public String use(String key, Map<String, String> map) {
        if (indices.isEmpty()) loadLang(Language.EN_UK);
        String message = indices.get(key);
        for(Map.Entry<String, String> entry : map.entrySet()){
            String check = String.format("\\$\\{%s\\}", entry.getKey());
            message = message.replaceAll(check, entry.getValue());
        }
        return message;
    }
}
