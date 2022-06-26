package eu.pb4.bof.mods;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.vanish.Vanish;
import net.minecraft.util.Identifier;

public class VanishPlaceholders {
    public static void register() {
        Placeholders.register(new Identifier("vanish", "safe_online"),
                (ctx, argument) -> PlaceholderResult.value(String.valueOf(Math.max(ctx.server().getCurrentPlayerCount() - Vanish.INSTANCE.getVanishedPlayers().size(), 0))));
        Placeholders.register(new Identifier("vanish", "invisible_player_count"), (ctx, argument) -> PlaceholderResult.value(String.valueOf(Vanish.INSTANCE.getVanishedPlayers().size())));
    }
}
