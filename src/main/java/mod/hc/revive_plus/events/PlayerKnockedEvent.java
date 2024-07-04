package mod.hc.revive_plus.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public interface PlayerKnockedEvent {

    Event<PlayerKnockedEvent> EVENT = EventFactory.createArrayBacked(PlayerKnockedEvent.class,
            (listeners) -> (world, player) -> {
                for (PlayerKnockedEvent listener : listeners) {
                    ActionResult result = listener.interact(world, player);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });
    ActionResult interact(World w, PlayerEntity player);
}
