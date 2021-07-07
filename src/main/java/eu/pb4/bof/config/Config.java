package eu.pb4.bof.config;

import eu.pb4.bof.config.data.ConfigData;
import eu.pb4.placeholders.TextParser;
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
    public final Map<String, Text> staticText;


    public Config(ConfigData data, Map<String, String> staticText) {
        this.configData = data;
        this.staticText = new HashMap<>();
        for (Map.Entry<String, String> entry : staticText.entrySet()) {
            this.staticText.put(entry.getKey(), TextParser.parse(entry.getValue()));
        }
        this.gomlNoClaimInfo = TextParser.parse(data.gomlNoClaimInfo);
        this.gomlNoClaimOwners = TextParser.parse(data.gomlNoClaimOwners);
        this.gomlNoClaimTrusted = TextParser.parse(data.gomlNoClaimTrusted);
        this.gomlClaimCanBuildInfo = TextParser.parse(data.gomlClaimCanBuildInfo);
        this.gomlClaimCantBuildInfo = TextParser.parse(data.gomlClaimCantBuildInfo);
    }
}
