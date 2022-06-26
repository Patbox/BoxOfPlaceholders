package eu.pb4.bof.other;

import eu.pb4.bof.config.Animation;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.SpawnHelper;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class BoPPlaceholders {
    static final int CHUNK_AREA = (int)Math.pow(17.0D, 2.0D);


    public static void register() {
        Placeholders.register(new Identifier("bop", "ram_max"), (ctx, argument) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value((argument != null && argument.equals("gb"))
                    ? String.format("%.1f", (float) heapUsage.getMax() / 1073741824)
                    : String.format("%d", heapUsage.getMax() / 1048576));
        });

        Placeholders.register(new Identifier("bop", "ram_used"), (ctx, argument) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value((argument != null && argument.equals("gb"))
                    ? String.format("%.1f", (float) heapUsage.getUsed() / 1073741824)
                    : String.format("%d", heapUsage.getUsed() / 1048576));
        });

        Placeholders.register(new Identifier("bop", "ram_free"), (ctx, argument) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value((argument != null && argument.equals("gb"))
                    ? String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / 1073741824)
                    : String.format("%d", (heapUsage.getMax() - heapUsage.getUsed()) / 1048576));
        });

        Placeholders.register(new Identifier("bop", "ram_used_percent"), (ctx, argument) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) heapUsage.getUsed() / heapUsage.getMax() * 100));
        });

        Placeholders.register(new Identifier("bop", "ram_free_percent"), (ctx, argument) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / heapUsage.getMax() * 100));

        });

        Placeholders.register(new Identifier("bop", "animation"), (ctx, argument) -> {
            if (argument != null) {
                Animation animation = ConfigManager.getAnimation(argument);
                if (animation != null) {
                    return PlaceholderResult.value(
                            ctx.hasPlayer()
                                    ? animation.getAnimationFrame(ctx.player())
                                    : animation.getAnimationFrame(ctx.server())
                    );
                }
            }

            return PlaceholderResult.invalid("Invalid animation");
        });

        Placeholders.register(new Identifier("bop", "mob_count"), (ctx, argument) -> {
            ServerWorld world;
            if (ctx.hasPlayer()) {
                world = ctx.player().getWorld();
            } else {
                world = ctx.server().getOverworld();
            }

            SpawnHelper.Info info = world.getChunkManager().getSpawnInfo();

            SpawnGroup spawnGroup = null;
            if (argument != null) {
                spawnGroup = SpawnGroup.valueOf(argument);
            }

            if (spawnGroup != null) {
                return PlaceholderResult.value("" + info.getGroupToCount().getInt(spawnGroup));
            } else {
                int x = 0;

                for (int value : info.getGroupToCount().values()) {
                    x += value;
                }
                return PlaceholderResult.value("" + x);
            }
        });

        Placeholders.register(new Identifier("bop", "mob_cap"), (ctx, argument) -> {
            ServerWorld world;
            if (ctx.hasPlayer()) {
                world = ctx.player().getWorld();
            } else {
                world = ctx.server().getOverworld();
            }

            SpawnHelper.Info info = world.getChunkManager().getSpawnInfo();

            SpawnGroup spawnGroup = null;
            if (argument != null) {
                spawnGroup = SpawnGroup.valueOf(argument);
            }

            if (spawnGroup != null) {
                return PlaceholderResult.value("" + spawnGroup.getCapacity() * info.getSpawningChunkCount() / CHUNK_AREA);
            } else {
                int x = 0;

                for (SpawnGroup group : SpawnGroup.values()) {
                    x += group.getCapacity();
                }
                return PlaceholderResult.value("" + x * info.getSpawningChunkCount() / CHUNK_AREA);
            }
        });

        Placeholders.register(new Identifier("bop", "static"), (ctx, argument) -> {
            if (argument != null && ConfigManager.getConfig().staticText.containsKey(argument)) {
                return PlaceholderResult.value(ConfigManager.getConfig().staticText.get(argument));
            } else {
                return PlaceholderResult.invalid();
            }
        });
    }
}
