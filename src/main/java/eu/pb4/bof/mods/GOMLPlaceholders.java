package eu.pb4.bof.mods;

import com.mojang.authlib.GameProfile;
import draylar.goml.api.ClaimUtils;
import eu.pb4.bof.config.Config;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import eu.pb4.placeholders.TextParser;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GOMLPlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("goml", "claim_owners"), (ctx) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimOwners;
            if (ctx.hasArgument()) {
                wildnessText = TextParser.parse(ctx.getArgument());
            }

            var claims = ClaimUtils.getClaimsAt(ctx.getPlayer().getServerWorld(), ctx.getPlayer().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> owners = new ArrayList<>();
                for (UUID owner : claim.getValue().getOwners()) {
                    GameProfile profile = ctx.getServer().getUserCache().getByUuid(owner);

                    if (profile != null) {
                        owners.add(profile.getName());
                    }
                }


                return PlaceholderResult.value(owners.size() > 0 ? new LiteralText(String.join(", ", owners)) : wildnessText);
            }
        });

        PlaceholderAPI.register(new Identifier("goml", "claim_trusted"), (ctx) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }

            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimTrusted;
            if (ctx.hasArgument()) {
                wildnessText = TextParser.parse(ctx.getArgument());
            }

            var claims = ClaimUtils.getClaimsAt(ctx.getPlayer().getServerWorld(), ctx.getPlayer().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> trusted = new ArrayList<>();
                for (UUID owner : claim.getValue().getTrusted()) {
                    GameProfile profile = ctx.getServer().getUserCache().getByUuid(owner);

                    if (profile != null) {
                        trusted.add(profile.getName());
                    }
                }


                return PlaceholderResult.value(trusted.size() > 0 ? new LiteralText(String.join(", ", trusted)) : wildnessText);
            }
        });

        PlaceholderAPI.register(new Identifier("goml", "claim_info"), (ctx) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }

            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimInfo;
            Text canBuildText = config.gomlClaimCanBuildInfo;
            Text cantBuildText = config.gomlClaimCantBuildInfo;

            if (ctx.hasArgument()) {
                String[] texts = ctx.getArgument().replace("\\/", "&bslsh;").split("/");

                if (texts.length > 0) {
                    wildnessText = TextParser.parse(texts[0].replace("&bslsh;", "/"));
                }
                if (texts.length > 1) {
                    canBuildText = TextParser.parse(texts[1].replace("&bslsh;", "/"));
                }
                if (texts.length > 2) {
                    cantBuildText = TextParser.parse(texts[2].replace("&bslsh;", "/"));
                }
            }

            var claims = ClaimUtils.getClaimsAt(ctx.getPlayer().getServerWorld(), ctx.getPlayer().getBlockPos()).collect(Collectors.toList());


            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> owners = new ArrayList<>();
                for (UUID owner : claim.getValue().getOwners()) {
                    GameProfile profile = ctx.getServer().getUserCache().getByUuid(owner);

                    if (profile != null) {
                        owners.add(profile.getName());
                    }
                }
                List<String> trusted = new ArrayList<>();
                for (UUID owner : claim.getValue().getTrusted()) {
                    GameProfile profile = ctx.getServer().getUserCache().getByUuid(owner);

                    if (profile != null) {
                        trusted.add(profile.getName());
                    }
                }


                return PlaceholderResult.value(PlaceholderAPI.parsePredefinedText(
                        claim.getValue().hasPermission(ctx.getPlayer()) ? canBuildText : cantBuildText,
                        PlaceholderAPI.PREDEFINED_PLACEHOLDER_PATTERN,
                        Map.of("owners", new LiteralText(String.join(", ", owners)),
                                "trusted", new LiteralText(String.join(", ", trusted)),
                                "anchor", new LiteralText(claim.getValue().getOrigin().toShortString())
                        )));
            }
        });
    }
}