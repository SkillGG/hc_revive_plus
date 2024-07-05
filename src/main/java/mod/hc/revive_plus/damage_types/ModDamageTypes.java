package mod.hc.revive_plus.damage_types;

import mod.hc.revive_plus.HCRevive;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> ALTAR_SACRIFICE_DAMAGE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE,
            new Identifier(HCRevive.MOD_ID, "altar_sacrifice")
    );

    public static DamageSource of(World world, RegistryKey<DamageType> key){
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
