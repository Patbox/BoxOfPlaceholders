package eu.pb4.bof;

import eu.pb4.bof.mods.LuckPermsPlaceholders;
import eu.pb4.bof.mods.PlasmidPlaceholders;
import eu.pb4.bof.mods.VanishPlaceholders;
import eu.pb4.bof.other.MiniMessagePlaceholders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoxOfPlaceholders implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Box of placeholders");
	private static BoxOfPlaceholders INSTANCE;

	public static final MiniMessage miniMessage = MiniMessage.get();
	private FabricServerAudiences audiences;

	public static FabricServerAudiences getAdventure() {
		return INSTANCE.audiences;
	}
	public static BoxOfPlaceholders getInstance() {
		return INSTANCE;
	}

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTING.register(server -> this.audiences = FabricServerAudiences.of(server));
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.audiences = null);
		INSTANCE = this;

		FabricLoader loader = FabricLoader.getInstance();
		Commands.register();

		if (loader.isModLoaded("luckperms")) {
			LuckPermsPlaceholders.register();
		}

		if (loader.isModLoaded("vanish")) {
			VanishPlaceholders.register();
		}

		if (loader.isModLoaded("plasmid")) {
			PlasmidPlaceholders.register();
		}

		MiniMessagePlaceholders.register();

	}
}
