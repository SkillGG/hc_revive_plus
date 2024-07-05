package mod.hc.revive_plus.effects;

import mod.hc.revive_plus.HCRevive;
import mod.hc.revive_plus.KnockablePlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

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
        if (entity instanceof KnockablePlayer) {
            if (((KnockablePlayer) entity).isKnocked())
                ((KnockablePlayer) entity).setKnocked(false, null);
            else
                (entity).removeStatusEffect(HCRevive.REVIVE_EFFECT);
        }
    }

}
