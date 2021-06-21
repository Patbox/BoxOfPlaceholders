package eu.pb4.bof.other;

import eu.pb4.bof.config.Animation;
import eu.pb4.bof.config.ConfigManager;
import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
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
        PlaceholderAPI.register(new Identifier("bop", "ram_max"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) heapUsage.getMax() / 1073741824)
                    : String.format("%d", heapUsage.getMax() / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bop", "ram_used"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) heapUsage.getUsed() / 1073741824)
                    : String.format("%d", heapUsage.getUsed() / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bop", "ram_free"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / 1073741824)
                    : String.format("%d", (heapUsage.getMax() - heapUsage.getUsed()) / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bop", "ram_used_percent"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) heapUsage.getUsed() / heapUsage.getMax() * 100));
        });

        PlaceholderAPI.register(new Identifier("bop", "ram_free_percent"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / heapUsage.getMax() * 100));

        });

        PlaceholderAPI.register(new Identifier("bop", "animation"), (ctx) -> {
            if (ctx.hasArgument()) {
                Animation animation = ConfigManager.getAnimation(ctx.getArgument());
                if (animation != null) {
                    return PlaceholderResult.value(
                            ctx.hasPlayer()
                                    ? animation.getAnimationFrame(ctx.getPlayer())
                                    : animation.getAnimationFrame(ctx.getServer())
                    );
                }
            }

            return PlaceholderResult.invalid("Invalid animation");
        });

        PlaceholderAPI.register(new Identifier("bop", "mob_count"), (ctx) -> {
            ServerWorld world;
            if (ctx.hasPlayer()) {
                world = ctx.getPlayer().getServerWorld();
            } else {
                world = ctx.getServer().getOverworld();
            }

            SpawnHelper.Info info = world.getChunkManager().getSpawnInfo();

            SpawnGroup spawnGroup = null;
            if (ctx.hasArgument()) {
                spawnGroup = SpawnGroup.byName(ctx.getArgument());
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

        PlaceholderAPI.register(new Identifier("bop", "mob_cap"), (ctx) -> {
            ServerWorld world;
            if (ctx.hasPlayer()) {
                world = ctx.getPlayer().getServerWorld();
            } else {
                world = ctx.getServer().getOverworld();
            }

            SpawnHelper.Info info = world.getChunkManager().getSpawnInfo();

            SpawnGroup spawnGroup = null;
            if (ctx.hasArgument()) {
                spawnGroup = SpawnGroup.byName(ctx.getArgument());
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
    }
}
