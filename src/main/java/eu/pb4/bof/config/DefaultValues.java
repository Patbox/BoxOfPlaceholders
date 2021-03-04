package eu.pb4.bof.config;

public class DefaultValues {
    public static AnimationData exampleAnimationData() {
        AnimationData data = new AnimationData();
        data.updateRate = 2;
        data.id = "example";
        data.frames.add("<red>Hello</red>");
        data.frames.add("<yellow>%player:displayname%</yellow>");
        return data;
    }
}
