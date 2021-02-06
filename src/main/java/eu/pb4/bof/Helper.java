package eu.pb4.bof;

import net.minecraft.text.Text;

import java.time.Duration;

public class Helper {
    public static String durationToString(Duration duration) {
        long x = duration.getSeconds();

        long seconds = x % 60;
        long minutes = (x / 60) % 60;
        long hours = (x / (60 * 60)) % 24;
        long days = x / (60 * 60 * 24);

        if (days > 0) {
            return String.format("%dd%dh%dm%ds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%dh%dm%ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm%ds", minutes, seconds);
        } else if (seconds > 0) {
            return String.format("%ds", seconds);
        } else {
            return "---";
        }
    }

    public static Text parseMiniMessage(String string) {
        return BoxOfPlaceholders.getAdventure().toNative(BoxOfPlaceholders.miniMessage.parse(string));
    }
}
