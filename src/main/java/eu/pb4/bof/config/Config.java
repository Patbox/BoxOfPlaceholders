package eu.pb4.bof.config;

import eu.pb4.bof.config.data.ConfigData;
import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class Config {
    public final ConfigData configData;
    public final Text gomlNoClaimInfo;
    public final Text gomlNoClaimOwners;
    public final Text gomlNoClaimTrusted;

    public final Text gomlClaimCanBuildInfo;
    public final Text gomlClaimCantBuildInfo;

    public final Text luckpermsInvalidPrefix;
    public final Text luckpermsInvalidSuffix;

    public final Map<String, Text> staticText;


    public Config(ConfigData data, Map<String, String> staticText) {
        this.configData = data;
        this.staticText = new HashMap<>();
        for (Map.Entry<String, String> entry : staticText.entrySet()) {
            this.staticText.put(entry.getKey(), TextParserUtils.formatText(entry.getValue()));
        }
        this.gomlNoClaimInfo = TextParserUtils.formatText(data.gomlNoClaimInfo);
        this.gomlNoClaimOwners = TextParserUtils.formatText(data.gomlNoClaimOwners);
        this.gomlNoClaimTrusted = TextParserUtils.formatText(data.gomlNoClaimTrusted);
        this.gomlClaimCanBuildInfo = TextParserUtils.formatText(data.gomlClaimCanBuildInfo);
        this.gomlClaimCantBuildInfo = TextParserUtils.formatText(data.gomlClaimCantBuildInfo);
        this.luckpermsInvalidPrefix = TextParserUtils.formatText(data.luckpermsInvalidPrefix);
        this.luckpermsInvalidSuffix = TextParserUtils.formatText(data.luckpermsInvalidSuffix);
    }
}
