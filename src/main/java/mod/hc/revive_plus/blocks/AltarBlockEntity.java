package mod.hc.revive_plus.blocks;

import mod.hc.revive_plus.HCRevive;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AltarBlockEntity extends BlockEntity {

    private PlayerEntity knocked;
    private LivingEntity sacrifice;

    public void setKnocked(PlayerEntity knocked) {
        this.knocked = knocked;
    }

    public void setSacrifice(LivingEntity sacrifice) {
        this.sacrifice = sacrifice;
    }

    public AltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.REVIVE_ALTAR_ENTITY, pos, state);
    }

    public boolean canRevive() {
        return knocked != null && sacrifice != null;
    }

    public void resetPlayers() {
        knocked = null;
        sacrifice = null;
    }

    public void tryRevive() {
        if (canRevive()) {
            knocked.addStatusEffect(new StatusEffectInstance(HCRevive.REVIVE_EFFECT, 1));
            sacrifice.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 2));
            resetPlayers();
        }
    }

    public int tick = 0;

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (entity instanceof AltarBlockEntity altarEntity) {
            altarEntity.tick++;
            altarEntity.tryRevive();
            altarEntity.resetPlayers();
        }
    }

}
