package mod.hc.revive_plus.mixin;

import mod.hc.revive_plus.KnockablePlayer;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Turn off targeting of knocked enemy
    @Overwrite
    public boolean canTarget(LivingEntity target) {
        return !(target instanceof KnockablePlayer kp && kp.isKnocked()) && (!(target instanceof PlayerEntity) || this.getWorld().getDifficulty() != Difficulty.PEACEFUL) && target.canTakeDamage();
    }

}
