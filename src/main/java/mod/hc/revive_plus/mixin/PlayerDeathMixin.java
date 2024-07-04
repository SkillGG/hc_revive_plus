package mod.hc.revive_plus.mixin;

import mod.hc.revive_plus.HCRevive;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class PlayerDeathMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("hc_revive_death");
    @Inject(at = @At("HEAD"), method = "die")
    private void init(CallbackInfo info) {
        LOGGER.info("net.minecraft.world.entity.player.die() called!");
    }
}