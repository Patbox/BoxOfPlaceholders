package eu.pb4.bof.other;

import eu.pb4.bof.Helper;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderContext;
import eu.pb4.placeholders.PlaceholderResult;
import net.minecraft.util.Identifier;

public class MiniMessagePlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("minimessage", "parse"), ctx -> {
            if (ctx.hasArgument()) {
                return PlaceholderResult.value(Helper.parseMiniMessage(ctx.getArgument()));
            } else {
                return PlaceholderResult.invalid();
            }
        });

        PlaceholderAPI.register(new Identifier("minimessage", "parse_placeholders"), ctx -> {
            if (ctx.hasArgument()) {
                if (ctx.playerExist()) {
                    return PlaceholderResult.value(PlaceholderAPI.parseTextAlt(Helper.parseMiniMessage(ctx.getArgument()), ctx.getPlayer()));
                } else {
                    return PlaceholderResult.value(PlaceholderAPI.parseTextAlt(Helper.parseMiniMessage(ctx.getArgument()), ctx.getServer()));
                }
            } else {
                return PlaceholderResult.invalid();
            }

        });


        PlaceholderAPI.register(new Identifier("minimessage", "placeholder"), ctx -> {
            if (ctx.hasArgument()) {
                PlaceholderContext context = ctx.playerExist() ? PlaceholderContext.create(ctx.getArgument(), ctx.getPlayer()) : PlaceholderContext.create(ctx.getArgument(), ctx.getServer());
                PlaceholderResult result = PlaceholderAPI.parsePlaceholder(context);
                return PlaceholderResult.value(Helper.parseMiniMessage(result.getString()));
            } else {
                return PlaceholderResult.invalid();
            }
        });
    }
}
