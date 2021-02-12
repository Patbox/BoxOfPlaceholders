package eu.pb4.bof.other;

import eu.pb4.placeholders.PlaceholderAPI;
import eu.pb4.placeholders.PlaceholderResult;
import net.minecraft.util.Identifier;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class BoFPlaceholders {
    public static void register() {
        PlaceholderAPI.register(new Identifier("bof", "ram_max"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) heapUsage.getMax() / 1073741824)
                    : String.format("%d", heapUsage.getMax() / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bof", "ram_used"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) heapUsage.getUsed() / 1073741824)
                    : String.format("%d", heapUsage.getUsed() / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bof", "ram_free"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(ctx.getArgument().equals("gb")
                    ? String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / 1073741824)
                    : String.format("%d", (heapUsage.getMax() - heapUsage.getUsed()) / 1048576));
        });

        PlaceholderAPI.register(new Identifier("bof", "ram_used_percent"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) heapUsage.getUsed() / heapUsage.getMax() * 100));
        });

        PlaceholderAPI.register(new Identifier("bof", "ram_free_percent"), (ctx) -> {
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();

            return PlaceholderResult.value(String.format("%.1f", (float) (heapUsage.getMax() - heapUsage.getUsed()) / heapUsage.getMax() * 100));

        });
    }
}
