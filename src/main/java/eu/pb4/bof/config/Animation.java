package eu.pb4.bof.config;

import eu.pb4.bof.Helper;
import eu.pb4.placeholders.PlaceholderAPI;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    public final String id;
    public final List<Text> frames = new ArrayList<>();
    public final int animationRate;

    public final Object2IntArrayMap<Object> animationStatus = new Object2IntArrayMap<>();

    public Animation(AnimationData data) {
        id = data.id;
        animationRate = data.updateRate > 0 ? data.updateRate : 1;

        for (String frame : data.frames) {
            Text text = Helper.parseMiniMessage(frame);
            frames.add(text);
        }
    }



    public Text getAnimationFrame(Object object) {
        int frame = animationStatus.getOrDefault(object, 0);
        int realFrame = frame / animationRate;

        frame += 1;

        if (frames.size() * animationRate <= frame) {
            frame = 0;
        }

        animationStatus.put(object, frame);


        if (object instanceof ServerPlayerEntity) {
            return PlaceholderAPI.parseText(frames.get(realFrame), (ServerPlayerEntity) object);
        } else if (object instanceof MinecraftServer) {
            return PlaceholderAPI.parseText(frames.get(realFrame), (MinecraftServer) object);
        } else {
            return frames.get(realFrame);
        }
    }
}
