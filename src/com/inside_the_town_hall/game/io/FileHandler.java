package com.inside_the_town_hall.game.io;

import com.inside_the_town_hall.game.translation.LanguageManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;

/**
 * Handles all the file input and output streams.
 * Is a Singleton
 *
 * @author NekroQuest
 */
public class FileHandler {
    private final String CONTENT_ROOT; // The root directory
    private static FileHandler instance;

    public static FileHandler getInstance() {
        if(instance == null) {
            // TODO: Maybe outsource to config
            instance = new FileHandler("C:\\Users\\David\\OneDrive\\Programme\\Java\\InsideTheTownHall\\src\\com\\inside_the_town_hall\\game\\");
        } return instance;
    }

    private FileHandler(String CONTENT_ROOT) {
        this.CONTENT_ROOT = CONTENT_ROOT;
    }

    /**
     * @param path the relative path to the file
     * @return byte array content of the file
     */
    public byte[] readFileAsBytes(String path) {
        byte[] bytes = {};
        try {
            bytes = Files.readAllBytes(Paths.get(this.CONTENT_ROOT + path));
        } catch (IOException e) {
            // TODO: Handle exception
        } return bytes;
    }

    /**
     * @param path the relative path to the file
     * @return string content of the file
     */
    public String readFile(String path) {
        return fileInputStream(path).toString();
    }

    /**
     * Reads a file content and handles exceptions
     *
     * @param path the relative path to the file
     * @return string Buffer with the file content
     */
    private StringBuilder fileInputStream(String path) {
        BufferedInputStream bufferedInputStream;
        StringBuilder fileContent = new StringBuilder();
        try {
            bufferedInputStream = new BufferedInputStream(
                    new FileInputStream(this.CONTENT_ROOT + path)
            );
            while(bufferedInputStream.available() > 0) {
                fileContent.append((char)bufferedInputStream.read());
            }
        } catch (IOException e) {
            LanguageManager.getInstance().use("ERROR.IO.FILE.INPUT");
        }
        return fileContent;
    }
}
