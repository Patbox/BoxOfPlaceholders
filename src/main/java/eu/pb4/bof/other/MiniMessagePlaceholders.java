package eu.pb4.bof.other;

import eu.pb4.bof.Helper;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderContext;
import eu.pb4.placeholders.PlaceholderResult;
import net.minecraft.util.Identifier;

public class MiniMessagePlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("minimessage", "parse"), ctx -> PlaceholderResult.value(Helper.parseMiniMessage(ctx.getArgument())));

        PlaceholderAPI.register(new Identifier("minimessage", "parse_placeholders"), ctx -> {
            if (ctx.playerExist()) {
                return PlaceholderResult.value(PlaceholderAPI.parseTextAlt(Helper.parseMiniMessage(ctx.getArgument()), ctx.getPlayer()));
            } else {
                return PlaceholderResult.value(PlaceholderAPI.parseTextAlt(Helper.parseMiniMessage(ctx.getArgument()), ctx.getServer()));
            }
        });


        PlaceholderAPI.register(new Identifier("minimessage", "placeholder"), ctx -> {
            if (ctx.playerExist()) {
                return PlaceholderResult.value(PlaceholderAPI.parsePlaceholder(PlaceholderContext.create(ctx.getArgument(), ctx.getPlayer())).getText());
            } else {
                return PlaceholderResult.value(PlaceholderAPI.parsePlaceholder(PlaceholderContext.create(ctx.getArgument(), ctx.getServer())).getText());
            }
        });
    }
}
