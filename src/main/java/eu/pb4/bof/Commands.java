package eu.pb4.bof;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.PlaceholderAPI;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class Commands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    literal("boxofplaceholders")
                            .requires(Permissions.require("bof.command", 3))
                            .then(literal("string")
                                    .then(argument("test", StringArgumentType.greedyString())
                                    .executes(Commands::parseString)
                            ))
                            .then(literal("text")
                                    .then(argument("test", TextArgumentType.text())
                                    .executes(Commands::parseText)
                            ))
                            .then(literal("reload")
                                    .executes(Commands::reloadConfig))
            );

        });
    }

    public static int parseString(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        String test = context.getArgument("test", String.class);

        ServerPlayerEntity player = source.getPlayer();

        if (player != null) {
            source.sendFeedback(new LiteralText(PlaceholderAPI.parseString(test, player)), false);
        } else {
            source.sendFeedback(new LiteralText("Only players can use this command!"), false);
        }

        return 0;
    }

    public static int parseText(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        Text test = context.getArgument("test", LiteralText.class);

        ServerPlayerEntity player = source.getPlayer();

        if (player != null) {
            source.sendFeedback(PlaceholderAPI.parseText(test, player), false);
        } else {
            source.sendFeedback(new LiteralText("Only players can use this command!"), false);
        }

        return 0;
    }

    private static int reloadConfig(CommandContext<ServerCommandSource> context) {
        if (ConfigManager.loadConfig()) {
            context.getSource().sendFeedback(new LiteralText("Reloaded config!"), false);
        } else {
            context.getSource().sendError(new LiteralText("Error occurred while reloading config!").formatted(Formatting.RED));

        }
        return 1;
    }
}