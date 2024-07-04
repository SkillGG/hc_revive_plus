package mod.hc.revive_plus;

import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerKnockedStatusAccessor {
    public boolean isKnocked();
    public void setKnocked(boolean knocked, ServerPlayerEntity spe);
}
