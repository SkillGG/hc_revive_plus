package mod.hc.revive_plus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;

public class HCReviveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(HCRevive.PLAYER_KNOCKED, ((client, handler, buf, responseSender) -> {
            boolean knocked = buf.readBoolean();
            client.execute(()->{
                client.player.sendMessage(Text.literal("Player knocked state!" + Boolean.toString(knocked)));
            });
        }));
        ClientPlayNetworking.registerGlobalReceiver(HCRevive.INIT_SYNC, ((client, handler, buf, responseSender) -> {
            boolean knocked = buf.readBoolean();
            client.execute(()->{
                client.player.sendMessage(Text.literal("Player's initial knocked state: " + Boolean.toString(knocked)));
            });
        }));
    }
}
