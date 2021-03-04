package eu.pb4.bof.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.pb4.bof.BoxOfPlaceholders;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedHashMap;


public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final LinkedHashMap<String, Animation> ANIMATIONS = new LinkedHashMap<>();

    public static boolean loadConfig() {
        try {
            File configStyle = Paths.get("", "config", "boxofplacholders", "animations").toFile();

            if (configStyle.mkdirs()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(configStyle, "example.json")));
                writer.write(GSON.toJson(DefaultValues.exampleAnimationData()));
                writer.close();
            }

            ANIMATIONS.clear();

            FilenameFilter filter = (dir, name) -> name.endsWith(".json");

            for (String fileName : configStyle.list(filter)) {
                Animation style = new Animation(GSON.fromJson(new FileReader(new File(configStyle, fileName)), AnimationData.class));
                ANIMATIONS.put(style.id, style);
            }

            return true;

        } catch (IOException exception) {
            BoxOfPlaceholders.LOGGER.error("Something went wrong while reading config!");
            exception.printStackTrace();

            return false;
        }
    }

    public static Animation getAnimation(String key) {
        return ANIMATIONS.get(key);
    }
}
