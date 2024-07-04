package mod.hc.revive_plus.save;

import mod.hc.revive_plus.HCRevive;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid,data)->{
            NbtCompound compound = new NbtCompound();
            compound.putBoolean("knocked", data.knocked);
            playersNbt.put(uuid.toString(), compound);
        });
        nbt.put("players", playersNbt);
        return nbt;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag) {
        StateSaverAndLoader state = new StateSaverAndLoader();
        NbtCompound playersData = tag.getCompound("players");
        playersData.getKeys().forEach((key)->{
            PlayerData data = new PlayerData();
            data.knocked = playersData.getCompound(key).getBoolean("knocked");
            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, data);
        });
        return state;
    }
    public static PlayerData getPlayerData(LivingEntity player){
        MinecraftServer server = player.getWorld().getServer();
        if(server == null){
            HCRevive.LOGGER.warn("Server is null!");
            return new PlayerData();
        }
        StateSaverAndLoader state = getServerState(server);
        return state.players.computeIfAbsent(player.getUuid(), uuid->new PlayerData());
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        ServerWorld world = server.getWorld(World.OVERWORLD);
        if (world == null) {
            HCRevive.LOGGER.warn("Overworld is null!");
            return new StateSaverAndLoader();
        }
        PersistentStateManager persistentStateManager = world.getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::createFromNbt, StateSaverAndLoader::new, HCRevive.MOD_ID);
        state.markDirty();
        return state;
    }
}