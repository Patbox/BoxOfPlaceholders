package eu.pb4.bof;

import eu.pb4.bof.config.ConfigManager;
import eu.pb4.bof.mods.GOMLPlaceholders;
import eu.pb4.bof.mods.LuckPermsPlaceholders;
import eu.pb4.bof.mods.VanishPlaceholders;
import eu.pb4.bof.other.BoPPlaceholders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoxOfPlaceholders implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Box of placeholders");
	private static BoxOfPlaceholders INSTANCE;

	public static BoxOfPlaceholders getInstance() {
		return INSTANCE;
	}

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			ConfigManager.loadConfig();
		});
		INSTANCE = this;

		FabricLoader loader = FabricLoader.getInstance();
		Commands.register();

		if (loader.isModLoaded("luckperms")) {
			LuckPermsPlaceholders.register();
		}

		if (loader.isModLoaded("vanish")) {
			VanishPlaceholders.register();
		}

		if (loader.isModLoaded("goml")) {
			GOMLPlaceholders.register();
		}

		if (loader.isModLoaded("plasmid")) {
		}

		BoPPlaceholders.register();
	}
}
