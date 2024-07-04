package mod.hc.revive_plus.effects;

import mod.hc.revive_plus.HCRevive;
import mod.hc.revive_plus.PlayerKnockedStatusAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class ReviveEffect extends StatusEffect {

    public ReviveEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xe9b8b3);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerKnockedStatusAccessor) {
            if (((PlayerKnockedStatusAccessor) entity).isKnocked())
                ((PlayerKnockedStatusAccessor) entity).setKnocked(false, null);
            else
                (entity).removeStatusEffect(HCRevive.REVIVE_EFFECT);
        }
    }

}
