package eu.pb4.bof.mixin;

import eu.pb4.bof.config.Animation;
import eu.pb4.bof.config.ConfigManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "onDisconnect", at = @At("TAIL"))
    private void clearAnimations(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        for (Animation animation : ConfigManager.ANIMATIONS.values()) {
            animation.animationStatus.removeInt(player.getUuid());
        }

    }
}
