package mod.hc.revive_plus;

import net.minecraft.server.network.ServerPlayerEntity;

public interface KnockablePlayer {
    public boolean isKnocked();
    public void setKnocked(boolean knocked, ServerPlayerEntity spe);
}
