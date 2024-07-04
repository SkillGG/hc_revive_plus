package mod.hc.revive_plus.mixin;

import mod.hc.revive_plus.HCRevive;
import mod.hc.revive_plus.PlayerKnockedStatusAccessor;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerMixin {

    @Inject(method="update", at=@At(value="HEAD"))
    private void hungerUpdateNoRegenIfKnocked(PlayerEntity player, CallbackInfo info) {
        boolean isKnocked = false;
        if(player instanceof PlayerKnockedStatusAccessor){
            isKnocked = ((PlayerKnockedStatusAccessor) player).isKnocked();
        }
    }
}
