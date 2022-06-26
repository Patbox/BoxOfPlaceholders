package eu.pb4.bof.mods;

import com.jamieswhiteshirt.rtree3i.Entry;
import com.mojang.authlib.GameProfile;
import draylar.goml.api.ClaimUtils;
import eu.pb4.bof.config.Config;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.stream.Collectors;
//(argument != null && argument.equals("gb"))
public class GOMLPlaceholders {
    public static void register() {
        Placeholders.register(new Identifier("goml", "claim_owners"), (ctx, argument) -> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimOwners;
            if (argument != null) {
                wildnessText = TextParserUtils.formatText(argument);
            }

            var claims = ClaimUtils.getClaimsAt(ctx.player().getWorld(), ctx.player().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> owners = new ArrayList<>();
                for (UUID owner : claim.getValue().getOwners()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);
                    
                    if (profile.isPresent()) {
                        owners.add(profile.get().getName());
                    }
                }


                return PlaceholderResult.value(owners.size() > 0 ? Text.literal(String.join(", ", owners)) : wildnessText);
            }
        });

        Placeholders.register(new Identifier("goml", "claim_owners"), (ctx, argument)-> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }
            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimOwners;
            if (argument != null) {
                wildnessText = TextParserUtils.formatText(argument);
            }

            var claims = ClaimUtils.getClaimsAt(ctx.player().getWorld(), ctx.player().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> owners = new ArrayList<>();
                for (UUID owner : claim.getValue().getOwners()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);

                    if (profile.isPresent()) {
                        owners.add(profile.get().getId().toString());
                    }
                }


                return PlaceholderResult.value(owners.size() > 0 ? Text.literal(String.join(", ", owners)) : wildnessText);
            }
        });

        Placeholders.register(new Identifier("goml", "claim_trusted"), (ctx, argument)-> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }

            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimTrusted;
            if (argument != null) {
                wildnessText = TextParserUtils.formatText(argument);
            }

            var claims = ClaimUtils.getClaimsAt(ctx.player().getWorld(), ctx.player().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> trusted = new ArrayList<>();
                for (UUID owner : claim.getValue().getTrusted()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);

                    if (profile.isPresent()) {
                        trusted.add(profile.get().getName());
                    }
                }


                return PlaceholderResult.value(trusted.size() > 0 ? Text.literal(String.join(", ", trusted)) : wildnessText);
            }
        });

        Placeholders.register(new Identifier("goml", "claim_trusted_uuid"), (ctx, argument)-> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }

            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimTrusted;
            if (argument != null) {
                wildnessText = TextParserUtils.formatText(argument);
            }

            var claims = ClaimUtils.getClaimsAt(ctx.player().getWorld(), ctx.player().getBlockPos()).collect(Collectors.toList());

            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> trusted = new ArrayList<>();
                for (UUID owner : claim.getValue().getTrusted()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);

                    if (profile.isPresent()) {
                        trusted.add(profile.get().getId().toString());
                    }
                }


                return PlaceholderResult.value(trusted.size() > 0 ? Text.literal(String.join(", ", trusted)) : wildnessText);
            }
        });

        Placeholders.register(new Identifier("goml", "claim_info"), (ctx, argument)-> {
            if (!ctx.hasPlayer()) {
                return PlaceholderResult.invalid("No player!");
            }

            Config config = ConfigManager.getConfig();

            Text wildnessText = config.gomlNoClaimInfo;
            Text canBuildText = config.gomlClaimCanBuildInfo;
            Text cantBuildText = config.gomlClaimCantBuildInfo;

            if (argument != null) {
                String[] texts = argument.replace("\\/", "&bslsh;").split("/");

                if (texts.length > 0) {
                    wildnessText = TextParserUtils.formatText(texts[0].replace("&bslsh;", "/"));
                }
                if (texts.length > 1) {
                    canBuildText = TextParserUtils.formatText(texts[1].replace("&bslsh;", "/"));
                }
                if (texts.length > 2) {
                    cantBuildText = TextParserUtils.formatText(texts[2].replace("&bslsh;", "/"));
                }
            }

            var claims = ClaimUtils.getClaimsAt(ctx.player().getWorld(), ctx.player().getBlockPos()).collect(Collectors.toList());


            if (claims.size() == 0) {
                return PlaceholderResult.value(wildnessText);
            } else {
                var claim = claims.get(0);

                List<String> owners = new ArrayList<>();
                List<String> ownersUuid = new ArrayList<>();

                for (UUID owner : claim.getValue().getOwners()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);

                    if (profile.isPresent()) {
                        owners.add(profile.get().getName());
                        ownersUuid.add(profile.get().getId().toString());
                    }
                }
                List<String> trusted = new ArrayList<>();
                List<String> trustedUuid = new ArrayList<>();
                for (UUID owner : claim.getValue().getTrusted()) {
                    Optional<GameProfile> profile = ctx.server().getUserCache().getByUuid(owner);

                    if (profile.isPresent()) {
                        trusted.add(profile.get().getName());
                        trustedUuid.add(profile.get().getId().toString());
                    }
                }


                return PlaceholderResult.value(Placeholders.parseText(
                        claim.getValue().hasPermission(ctx.player()) ? canBuildText : cantBuildText,
                        Placeholders.PREDEFINED_PLACEHOLDER_PATTERN,
                        Map.of("owners", Text.literal(String.join(", ", owners)),
                                "owners_uuid", Text.literal(String.join(", ", ownersUuid)),
                                "trusted", Text.literal(String.join(", ", trusted)),
                                "trusted_uuid", Text.literal(String.join(", ", trustedUuid)),
                                "anchor", Text.literal(claim.getValue().getOrigin().toShortString())
                        )));
            }
        });
    }
}