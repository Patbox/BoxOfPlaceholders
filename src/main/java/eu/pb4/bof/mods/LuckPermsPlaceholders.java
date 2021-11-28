package eu.pb4.bof.mods;

import eu.pb4.bof.Helper;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import eu.pb4.placeholders.TextParser;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

public class LuckPermsPlaceholders {
    public static LuckPerms LUCKPERMS = null;

    public static void register() {
        PlaceholderAPI.register(new Identifier("luckperms", "prefix"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.getPlayer());
                if (user != null) {
                    String out = "";

                    if (ctx.getArgument().length() > 0) {
                        var args = ctx.getArgument().split("/", 2);
                        int amount;
                        try {
                            amount = Math.max(Integer.valueOf(args[0]), 0);
                        } catch (Exception e) {
                            amount = 0;
                        }

                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getPrefixes();

                        List<String> list = new ArrayList<>();

                        int pos = 0;
                        for (var value : map.values()) {
                            if (amount <= pos++) {
                                continue;
                            }

                            list.add(value);
                        }

                        out = String.join(args.length == 2 ? args[1] : " ", list);
                    } else {
                        out = user.getCachedData().getMetaData().getPrefix();
                    }

                    return PlaceholderResult.value(out != null ? TextParser.parse(out) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.value(ConfigManager.getConfig().luckpermsInvalidPrefix);
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "suffix"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.getPlayer());
                if (user != null) {
                    String out = "";

                    if (ctx.getArgument().length() > 0) {
                        var args = ctx.getArgument().split("/", 2);
                        int amount;
                        try {
                            amount = Math.max(Integer.valueOf(args[0]), 0);
                        } catch (Exception e) {
                            amount = 0;
                        }

                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getSuffixes();

                        List<String> list = new ArrayList<>();

                        int pos = 0;
                        for (var value : map.values()) {
                            if (amount <= pos++) {
                                continue;
                            }

                            list.add(value);
                        }

                        out = String.join(args.length == 2 ? args[1] : " ", list);
                    } else {
                        out = user.getCachedData().getMetaData().getSuffix();
                    }

                    return PlaceholderResult.value(out != null ? TextParser.parse(out) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.value(ConfigManager.getConfig().luckpermsInvalidSuffix);
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "prefix_if_in_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && ctx.getArgument().length() > 0) {
                User user = getUser(ctx.getPlayer());
                Group group = LUCKPERMS.getGroupManager().getGroup(ctx.getArgument());

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParser.parse(group.getCachedData().getMetaData().getPrefix()) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.value(LiteralText.EMPTY);
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "suffix_if_in_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && ctx.getArgument().length() > 0) {
                User user = getUser(ctx.getPlayer());
                Group group = LUCKPERMS.getGroupManager().getGroup(ctx.getArgument());

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParser.parse(group.getCachedData().getMetaData().getSuffix()) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.value(LiteralText.EMPTY);
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "primary_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.getPlayer());

                if (user != null) {
                    return PlaceholderResult.value(user.getPrimaryGroup());
                } else {
                    return PlaceholderResult.value(LiteralText.EMPTY);
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });


        PlaceholderAPI.register(new Identifier("luckperms", "primary_group_display"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = getUser(ctx.getPlayer());

                if (user != null) {
                    Group group = LUCKPERMS.getGroupManager().getGroup(user.getPrimaryGroup());
                    if (group != null) {
                        var display = group.getDisplayName(user.getQueryOptions());

                        return PlaceholderResult.value(display != null ? TextParser.parse(display) : LiteralText.EMPTY);
                    }
                }
                return PlaceholderResult.value(LiteralText.EMPTY);

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "group_expiry_time"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && ctx.getArgument().length() > 0) {
                User user = getUser(ctx.getPlayer());

                if (user != null) {
                    Duration time = user.resolveInheritedNodes(user.getQueryOptions()).stream()
                            .filter(Node::hasExpiry)
                            .filter(NodeType.INHERITANCE::matches)
                            .map(NodeType.INHERITANCE::cast)
                            .filter(n -> n.getGroupName().equals(ctx.getArgument()))
                            .map(Node::getExpiryDuration)
                            .filter(Objects::nonNull)
                            .filter(d -> !d.isNegative())
                            .findFirst()
                            .orElse(Duration.ofMillis(0));
                    return PlaceholderResult.value(Helper.durationToString(time));
                } else {
                    return PlaceholderResult.value(LiteralText.EMPTY);
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "permission_expiry_time"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && ctx.getArgument().length() > 0) {
                User user = getUser(ctx.getPlayer());

                if (user != null) {
                    Duration time = user.resolveInheritedNodes(user.getQueryOptions()).stream()
                            .filter(Node::hasExpiry)
                            .filter(NodeType.PERMISSION::matches)
                            .map(NodeType.PERMISSION::cast)
                            .filter(n -> n.getPermission().equals(ctx.getArgument()))
                            .map(Node::getExpiryDuration)
                            .filter(Objects::nonNull)
                            .filter(d -> !d.isNegative())
                            .findFirst()
                            .orElse(Duration.ofMillis(0));
                    return PlaceholderResult.value(Helper.durationToString(time));
                } else {
                    return PlaceholderResult.value(LiteralText.EMPTY);
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });
    }


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
