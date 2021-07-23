package eu.pb4.bof.config.data;

import java.util.ArrayList;
import java.util.List;

public class ConfigData {
    public int CONFIG_VERSION_DONT_TOUCH_THIS = 1;
    public String gomlNoClaimInfo = "<gray><italic>Wilderness";
    public String gomlNoClaimOwners = "<gray><italic>Nobody";
    public String gomlNoClaimTrusted = "<gray><italic>Nobody";

    public String gomlClaimCanBuildInfo = "${owners} <gray>(<green>${anchor}</green>)";
    public String gomlClaimCantBuildInfo = "${owners} <gray>(<red>${anchor}</red>)";


    public String luckpermsInvalidPrefix = "";
    public String luckpermsInvalidSuffix = "";

}
