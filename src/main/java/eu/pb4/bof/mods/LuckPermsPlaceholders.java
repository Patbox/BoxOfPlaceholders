package eu.pb4.bof.mods;

import eu.pb4.bof.Helper;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import eu.pb4.placeholders.TextParser;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.time.Duration;
import java.util.Objects;
import java.util.SortedMap;

public class LuckPermsPlaceholders {
    public static LuckPerms LUCKPERMS = null;

    public static void register() {
        PlaceholderAPI.register(new Identifier("luckperms", "prefix"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.playerExist()) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());
                if (user != null) {
                    String out = "";
                    int amount;
                    try {
                        amount = Integer.valueOf(ctx.getArgument());
                    } catch (Exception e) {
                        amount = 0;
                    }

                    if (ctx.getArgument().length() > 0 && amount > 0) {
                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getPrefixes();

                        if (amount > map.size()) {
                            amount = map.size();
                        }

                        for (int x = 1; x <= amount; x++) {
                            out += map.get(x);
                        }

                    } else {
                        out = user.getCachedData().getMetaData().getPrefix();
                    }

                    return PlaceholderResult.value(out != null ? TextParser.parse(out) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "suffix"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.playerExist()) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());
                if (user != null) {
                    String out = "";
                    int amount;
                    try {
                        amount = Integer.valueOf(ctx.getArgument());
                    } catch (Exception e) {
                        amount = 0;
                    }

                    if (ctx.getArgument().length() > 0 && amount > 0) {
                        SortedMap<Integer, String> map = user.getCachedData().getMetaData().getSuffixes();

                        if (amount > map.size()) {
                            amount = map.size();
                        }

                        for (int x = 1; x <= amount; x++) {
                            out += map.get(x);
                        }

                    } else {
                        out = user.getCachedData().getMetaData().getSuffix();
                    }

                    return PlaceholderResult.value(out != null ? TextParser.parse(out) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "prefix_if_in_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.playerExist() && ctx.getArgument().length() > 0) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());
                Group group = LUCKPERMS.getGroupManager().getGroup(ctx.getArgument());

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParser.parse(group.getCachedData().getMetaData().getPrefix()) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "suffix_if_in_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer() && ctx.getArgument().length() > 0) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());
                Group group = LUCKPERMS.getGroupManager().getGroup(ctx.getArgument());

                if (user != null && group != null) {
                    return PlaceholderResult.value(user.getInheritedGroups(user.getQueryOptions()).contains(group)
                            ? TextParser.parse(group.getCachedData().getMetaData().getSuffix()) : LiteralText.EMPTY);
                } else {
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "primary_group"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.hasPlayer()) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());

                if (user != null) {
                    return PlaceholderResult.value(user.getPrimaryGroup());
                } else {
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });


        PlaceholderAPI.register(new Identifier("luckperms", "group_expiry_time"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.playerExist() && ctx.getArgument().length() > 0) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());

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
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });

        PlaceholderAPI.register(new Identifier("luckperms", "permission_expiry_time"), ctx -> {
            if (getLuckPerms()) {
                return PlaceholderResult.invalid("Luckperms isn't loaded yet!");
            } else if (ctx.playerExist() && ctx.getArgument().length() > 0) {
                User user = LUCKPERMS.getUserManager().getUser(ctx.getPlayer().getUuid());

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
                    return PlaceholderResult.invalid("No data!");
                }

            } else {
                return PlaceholderResult.invalid("No player/argument!");
            }
        });
    }


    private static boolean getLuckPerms() {
        try {
            LUCKPERMS = LuckPermsProvider.get();
            return false;
        } catch (Exception e) {
            LUCKPERMS = null;
            return true;
        }
    }
}
