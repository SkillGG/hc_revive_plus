package mod.hc.revive_plus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HCReviveClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(HCRevive.PLAYER_KNOCKED, ((client, handler, buf, responseSender) -> {
            client.execute(()->{
                HCRevive.LOGGER.warn("Player knocked event");
                // Client got information, he's been knocked out
                HCReviveGUI.drawKnocked(20*15);
            });
        }));
        HudRenderCallback.EVENT.register(HCReviveGUI::render);
        ClientPlayNetworking.registerGlobalReceiver(HCRevive.INIT_SYNC, ((client, handler, buf, responseSender) -> {
            boolean knocked = buf.readBoolean();
            client.execute(()->{
                HCRevive.LOGGER.warn("Knocked on init: {}", knocked);
                HCReviveGUI.setPlayer(client.player);
                // Client got information that the player's at login passed out
                if(knocked) {
                    HCReviveGUI.drawKnocked(20*15);
                }
            });
        }));
    }
}
