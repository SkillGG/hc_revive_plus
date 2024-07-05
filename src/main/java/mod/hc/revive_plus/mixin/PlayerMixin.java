package mod.hc.revive_plus.mixin;

import mod.hc.revive_plus.HCRevive;
import mod.hc.revive_plus.KnockablePlayer;
import mod.hc.revive_plus.events.PlayerKnockedEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity implements KnockablePlayer {
    private static final Logger LOGGER = LoggerFactory.getLogger("hc_revive_death");

    private boolean knocked = false;

    private PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isKnocked() {
        return knocked;
    }

    @Shadow
    public abstract HungerManager getHungerManager();

    public void setKnocked(boolean v, ServerPlayerEntity spe) {
        knocked = v;
        HCRevive.LOGGER.info("Setting knocked to: {}", v);
        ServerPlayerEntity sPlayer = (spe == null) ? getServerPlayer() : spe;
        if (sPlayer == null) {
            HCRevive.LOGGER.error("Player is null!");
            return;
        }
        EntityAttributeInstance walkingSpeed = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (v) {
            sPlayer.changeGameMode(GameMode.ADVENTURE);
            setHealth(1);
            HungerManager hunger = getHungerManager();
            hunger.setFoodLevel(2);
            if (walkingSpeed != null) {
                walkingSpeed.setBaseValue(0.01f);
            }
        } else {
            sPlayer.changeGameMode(GameMode.SURVIVAL);
            if (walkingSpeed != null) {
                walkingSpeed.setBaseValue(0.1f);
            }
        }
    }

    public ServerPlayerEntity getServerPlayer() {
        MinecraftServer server = getServer();
        if (server == null) {
            LOGGER.warn("NO SERVER");
            return null;
        }
        PlayerManager pManager = server.getPlayerManager();
        if (pManager == null) {
            LOGGER.warn("NO P_MANAGER");
            return null;
        }
        ServerPlayerEntity serverPlayer = pManager.getPlayer(getUuid());
        if (serverPlayer == null) {
            LOGGER.warn("NO SERVER PLAYER");
            return null;
        }
        return serverPlayer;
    }

    @ModifyVariable(method = "applyDamage(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setAbsorptionAmount(F)V"), argsOnly = true)
    private float applyDamage(float damageAmount, DamageSource damageSource) {
        LOGGER.info("Recieved {} damage from {}", damageAmount, damageSource.getName());
        PlayerEntity player = (PlayerEntity) (Object) this;
        float health = player.getHealth();
        LOGGER.info("Player would've died!");
        ServerPlayerEntity serverPlayer = getServerPlayer();
        GameMode curGM = serverPlayer.interactionManager.getGameMode();
        if (isKnocked()) {
            LOGGER.warn("IS KNOCKED OUT");
            serverPlayer.changeGameMode(GameMode.SURVIVAL);
            return damageAmount;
        }
        PlayerKnockedEvent.EVENT.invoker().interact(player.getWorld(), player);
        setKnocked(true, getServerPlayer());
        return 0f;
    }

}