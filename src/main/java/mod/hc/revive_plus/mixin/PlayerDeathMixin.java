package mod.hc.revive_plus.mixin;

import mod.hc.revive_plus.HCRevive;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class PlayerDeathMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger("hc_revive_death");
    @Inject(at=@At("HEAD"), method = "onDeath")
    private void onDeath(CallbackInfo info){
        LOGGER.info("Player died!");
    }
}