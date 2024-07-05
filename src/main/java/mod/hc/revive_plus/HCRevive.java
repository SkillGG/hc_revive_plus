package mod.hc.revive_plus;

import mod.hc.revive_plus.blocks.ModBlocks;
import mod.hc.revive_plus.effects.ReviveEffect;
import mod.hc.revive_plus.events.PlayerKnockedEvent;
import mod.hc.revive_plus.items.ModItems;
import mod.hc.revive_plus.save.PlayerData;
import mod.hc.revive_plus.save.StateSaverAndLoader;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HCRevive implements ModInitializer {

    public static final String MOD_ID = "hc_revive_plus";
    public static final Logger LOGGER = LoggerFactory.getLogger("hc_revive_plus");

    public static final Identifier PLAYER_KNOCKED = new Identifier(MOD_ID, "player_knocked");
    public static final Identifier INIT_SYNC = new Identifier(MOD_ID, "init_sync");

    public static final StatusEffect REVIVE_EFFECT;

    static {
        REVIVE_EFFECT = Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "revive_effect"), new ReviveEffect());
    }

    @Override
    public void onInitialize() {
        // Add all mod items
        ModItems.init();
        ModBlocks.init();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            LOGGER.info("Connected player: {}", handler.getPlayer().getUuid().toString());
            PlayerData playerState = StateSaverAndLoader.getPlayerData(handler.getPlayer());
            PacketByteBuf data = PacketByteBufs.create();
            data.writeBoolean(playerState.knocked);
            ServerPlayerEntity player = handler.getPlayer();
            server.execute(() -> {
                if(player instanceof KnockablePlayer knock){
                    knock.setKnocked(playerState.knocked, player);
                }
                ServerPlayNetworking.send(handler.getPlayer(), INIT_SYNC, data);
            });
        });
        PlayerKnockedEvent.EVENT.register((w, player) -> {
            MinecraftServer server = w.getServer();
            LOGGER.info("Knocked event!");
            if (server == null) return ActionResult.PASS;

            PlayerData playerState = StateSaverAndLoader.getPlayerData(player);
            playerState.knocked = true;

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(playerState.knocked);
            ServerPlayerEntity sPlayer = server.getPlayerManager().getPlayer(player.getUuid());
            if (sPlayer == null) return ActionResult.PASS;
            server.execute(() -> {
                /* Execute server-dependent code here (tell player he's knocked out) */
                if(player instanceof KnockablePlayer knock){
                    knock.setKnocked(playerState.knocked, sPlayer);
                }
                ServerPlayNetworking.send(sPlayer, PLAYER_KNOCKED, buf);
            });
            return ActionResult.PASS;
        });
    }

}