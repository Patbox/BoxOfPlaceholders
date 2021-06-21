package eu.pb4.bof.config;

import eu.pb4.bof.config.data.ConfigData;
import eu.pb4.placeholders.TextParser;
import net.minecraft.text.Text;

public class Config {
    public final ConfigData configData;
    public final Text gomlNoClaimInfo;
    public final Text gomlNoClaimOwners;
    public final Text gomlNoClaimTrusted;

    public final Text gomlClaimCanBuildInfo;
    public final Text gomlClaimCantBuildInfo;


    public Config(ConfigData data) {
        this.configData = data;
        this.gomlNoClaimInfo = TextParser.parse(data.gomlNoClaimInfo);
        this.gomlNoClaimOwners = TextParser.parse(data.gomlNoClaimOwners);
        this.gomlNoClaimTrusted = TextParser.parse(data.gomlNoClaimTrusted);
        this.gomlClaimCanBuildInfo = TextParser.parse(data.gomlClaimCanBuildInfo);
        this.gomlClaimCantBuildInfo = TextParser.parse(data.gomlClaimCantBuildInfo);
    }
}
