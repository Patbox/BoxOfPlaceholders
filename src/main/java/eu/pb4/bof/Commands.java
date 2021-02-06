package eu.pb4.bof;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.placeholders.PlaceholderAPI;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class Commands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                    literal("placeholder")
                            .then(literal("string")
                                    .then(argument("test", StringArgumentType.greedyString())
                                    .executes(Commands::parseString)
                            ))
                            .then(literal("text")
                                    .then(argument("test", TextArgumentType.text())
                                    .executes(Commands::parseText)
                            ))
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
            player.sendMessage(PlaceholderAPI.parseText(test, player), false);
        } else {
            source.sendFeedback(new LiteralText("Only players can use this command!"), false);
        }

        return 0;
    }
}