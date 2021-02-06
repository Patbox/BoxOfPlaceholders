package eu.pb4.bof.mods;

import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import eu.vanish.Vanish;
import net.minecraft.util.Identifier;

public class VanishPlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("vanish", "safe_online"),
                ctx -> PlaceholderResult.value(String.valueOf(Math.max(ctx.getServer().getCurrentPlayerCount() - Vanish.INSTANCE.getVanishedPlayers().size(), 0))));
        PlaceholderAPI.register(new Identifier("vanish", "invisible_player_count"), ctx -> PlaceholderResult.value(String.valueOf(Vanish.INSTANCE.getVanishedPlayers().size())));
    }
}
