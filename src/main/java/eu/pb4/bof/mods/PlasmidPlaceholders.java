package eu.pb4.bof.mods;

import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import net.minecraft.util.Identifier;
import xyz.nucleoid.plasmid.game.ManagedGameSpace;
import xyz.nucleoid.plasmid.game.channel.GameChannel;
import xyz.nucleoid.plasmid.game.channel.GameChannelManager;

public class PlasmidPlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("plasmid", "active_game"), ctx -> {
            if (ctx.playerExist()) {
                ManagedGameSpace gameSpace = ManagedGameSpace.forWorld(ctx.getPlayer().world);

                if (gameSpace != null) {
                    return PlaceholderResult.value(gameSpace.getGameConfig().getNameText());
                } else {
                    return ctx.hasArgument() ? PlaceholderResult.value(ctx.getArgument()) : PlaceholderResult.value("Not in game");
                }

            } else {
                return PlaceholderResult.invalid("No Player");
            }
        });

        PlaceholderAPI.register(new Identifier("plasmid", "player_count"), ctx -> {
            if (ctx.playerExist()) {
                ManagedGameSpace gameSpace = ManagedGameSpace.forWorld(ctx.getPlayer().world);

                if (gameSpace != null) {
                    return PlaceholderResult.value(String.valueOf(gameSpace.getPlayerCount()));
                } else {
                    return ctx.hasArgument() ? PlaceholderResult.value(ctx.getArgument()) : PlaceholderResult.value("-");
                }

            } else {
                return PlaceholderResult.invalid("No Player");
            }
        });

        PlaceholderAPI.register(new Identifier("plasmid", "gamechannel_player_count"), ctx -> {
            Identifier identifier = Identifier.tryParse(ctx.getArgument());

            if (identifier == null) {
                return PlaceholderResult.invalid("Invalid channel");
            }
            GameChannelManager manager = GameChannelManager.get(ctx.getServer());
            GameChannel channel = manager.byId(identifier);

            if (channel == null) {
                return PlaceholderResult.invalid("Invalid channel");
            }

            return PlaceholderResult.value(String.valueOf(channel.getPlayerCount()));
        });

        PlaceholderAPI.register(new Identifier("plasmid", "gamechannel_name"), ctx -> {
            Identifier identifier = Identifier.tryParse(ctx.getArgument());

            if (identifier == null) {
                return PlaceholderResult.invalid("Invalid channel");
            }
            GameChannelManager manager = GameChannelManager.get(ctx.getServer());
            GameChannel channel = manager.byId(identifier);

            if (channel == null) {
                return PlaceholderResult.invalid("Invalid channel");
            }

            return PlaceholderResult.value(String.valueOf(channel.getName()));
        });
    }
}
