package eu.pb4.bof.mods;

import eu.pb4.bof.Helper;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

public class LuckPermsPlaceholders {
    public static LuckPerms LUCKPERMS = null;

    public static void register() {
        Placeholders.register(new Identifier("luckperms", "prefix"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.player());
                if (user != null) {
                    String out;

                    if (argument != null && argument.length() > 0) {
                        var args = argument.split("/", 2);
                        int amount;
                        try {
                            amount = Math.max(Integer.parseInt(args[0]), 0);
                        } catch (Exception e) {
                            amount = 0;
                        }

                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getPrefixes();

                        out = getPlayerTitleString(args, amount, map);
                    } else {
                        out = user.getCachedData().getMetaData().getPrefix();
                    }

                    return PlaceholderResult.value(out != null ? TextParserUtils.formatText(out) : Text.empty());
                } else {
                    return PlaceholderResult.value(ConfigManager.getConfig().luckpermsInvalidPrefix);
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "suffix"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.player());
                if (user != null) {
                    String out;

                    if (argument != null && argument.length() > 0) {
                        var args = argument.split("/", 2);
                        int amount;
                        try {
                            amount = Math.max(Integer.parseInt(args[0]), 0);
                        } catch (Exception e) {
                            amount = 0;
                        }

                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getSuffixes();

                        out = getPlayerTitleString(args, amount, map);
                    } else {
                        out = user.getCachedData().getMetaData().getSuffix();
                    }

                    return PlaceholderResult.value(out != null ? TextParserUtils.formatText(out) : Text.empty());
                } else {
                    return PlaceholderResult.value(ConfigManager.getConfig().luckpermsInvalidSuffix);
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "prefix_if_in_group"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && argument != null && argument.length() > 0) {
                User user = getUser(ctx.player());
                Group group = LUCKPERMS.getGroupManager().getGroup(argument);

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParserUtils.formatText(group.getCachedData().getMetaData().getPrefix()) : Text.empty());
                } else {
                    return PlaceholderResult.value(Text.empty());
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "suffix_if_in_group"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && argument != null && argument.length() > 0) {
                User user = getUser(ctx.player());
                Group group = LUCKPERMS.getGroupManager().getGroup(argument);

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParserUtils.formatText(group.getCachedData().getMetaData().getSuffix()) : Text.empty());
                } else {
                    return PlaceholderResult.value(Text.empty());
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "primary_group"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.player());

                if (user != null) {
                    return PlaceholderResult.value(user.getPrimaryGroup());
                } else {
                    return PlaceholderResult.value(Text.empty());
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });


        Placeholders.register(new Identifier("luckperms", "primary_group_display"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.player());

                if (user != null) {
                    Group group = LUCKPERMS.getGroupManager().getGroup(user.getPrimaryGroup());
                    if (group != null) {
                        var display = group.getDisplayName(user.getQueryOptions());

                        return PlaceholderResult.value(display != null ? TextParserUtils.formatText(display) : Text.empty());
                    }
                }
                return PlaceholderResult.value(Text.empty());

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "group_expiry_time"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && argument != null && argument.length() > 0) {
                User user = getUser(ctx.player());

                if (user != null) {
                    Duration time = user.resolveInheritedNodes(user.getQueryOptions()).stream()
                            .filter(Node::hasExpiry)
                            .filter(NodeType.INHERITANCE::matches)
                            .map(NodeType.INHERITANCE::cast)
                            .filter(n -> n.getGroupName().equals(argument))
                            .map(Node::getExpiryDuration)
                            .filter(Objects::nonNull)
                            .filter(d -> !d.isNegative())
                            .findFirst()
                            .orElse(Duration.ofMillis(0));
                    return PlaceholderResult.value(Helper.durationToString(time));
                } else {
                    return PlaceholderResult.value(Text.empty());
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        Placeholders.register(new Identifier("luckperms", "permission_expiry_time"), (ctx, argument) -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && argument != null && argument.length() > 0) {
                User user = getUser(ctx.player());

                if (user != null) {
                    Duration time = user.resolveInheritedNodes(user.getQueryOptions()).stream()
                            .filter(Node::hasExpiry)
                            .filter(NodeType.PERMISSION::matches)
                            .map(NodeType.PERMISSION::cast)
                            .filter(n -> n.getPermission().equals(argument))
                            .map(Node::getExpiryDuration)
                            .filter(Objects::nonNull)
                            .filter(d -> !d.isNegative())
                            .findFirst()
                            .orElse(Duration.ofMillis(0));
                    return PlaceholderResult.value(Helper.durationToString(time));
                } else {
                    return PlaceholderResult.value(Text.empty());
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });
    }

    @NotNull
    private static String getPlayerTitleString(String[] args, int amount, SortedMap<Integer, String> map) {
        String out;
        List<String> list = new ArrayList<>();

        int pos = 0;
        for (var value : map.values()) {
            if (amount <= pos++) {
                continue;
            }

            list.add(value);
        }

        out = String.join(args.length == 2 ? args[1] : " ", list);
        return out;
    }


    @SuppressWarnings("all")
    private static boolean getLuckPerms() {
        if (LUCKPERMS != null) {
            return false;
        }

        try {
            LUCKPERMS = LuckPermsProvider.get();
            return false;
        } catch (Exception e) {
            LUCKPERMS = null;
            return true;
        }
    }

    @Nullable
    private static User getUser(ServerPlayerEntity player) {
        try {
            return LUCKPERMS.getPlayerAdapter(ServerPlayerEntity.class).getUser(player);
        } catch (Exception e) {
            return null;
        }
    }
}
