package eu.pb4.bof.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.pb4.bof.BoxOfPlaceholders;
import eu.pb4.bof.config.data.AnimationData;
import eu.pb4.bof.config.data.ConfigData;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class ConfigManager {
    public static final LinkedHashMap<String, Animation> ANIMATIONS = new LinkedHashMap<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static Config CONFIG;

    public static boolean loadConfig() {
        try {
            Path configDir = FabricLoader.getInstance().getConfigDir().resolve("boxofplacholders");
            Path configAnimations = configDir.resolve("animations");

            configDir.toFile().mkdirs();

            ConfigData config;
            File configFile = new File(configDir.toFile(), "config.json");
            File staticFile = new File(configDir.toFile(), "static.json");

            if (configFile.exists()) {
                config = GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8), ConfigData.class);
            } else {
                config = new ConfigData();
            }

            Map<String, String> staticText;

            if (staticFile.exists()) {
                staticText = GSON.fromJson(new InputStreamReader(new FileInputStream(staticFile), StandardCharsets.UTF_8), Map.class);
            } else {
                staticText = Map.of("example", "Some text");
            }

            CONFIG = new Config(config, staticText);

            {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8));
                writer.write(GSON.toJson(config));
                writer.close();
            }
            {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staticFile), StandardCharsets.UTF_8));
                writer.write(GSON.toJson(staticText));
                writer.close();
            }
            if (configAnimations.toFile().mkdirs()) {
                BufferedWriter writer2 = new BufferedWriter(new FileWriter(new File(configAnimations.toFile(), "example.json")));
                writer2.write(GSON.toJson(DefaultValues.exampleAnimationData()));
                writer2.close();
            }

            ANIMATIONS.clear();

            FilenameFilter filter = (dir, name) -> name.endsWith(".json");

            for (String fileName : Objects.requireNonNull(configAnimations.toFile().list(filter))) {
                Animation style = new Animation(GSON.fromJson(new FileReader(new File(configAnimations.toFile(), fileName)), AnimationData.class));
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

    public static Config getConfig() {
        return CONFIG;
    }
}
