package mod.hc.revive_plus.blocks;

import com.mojang.datafixers.types.Type;
import mod.hc.revive_plus.HCRevive;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Altar REVIVE_ALTAR = new Altar(FabricBlockSettings.create().dropsNothing().breakInstantly());

    public static final BlockEntityType<AltarBlockEntity> REVIVE_ALTAR_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(HCRevive.MOD_ID, "revive_altar_entity"),
            FabricBlockEntityTypeBuilder.create(AltarBlockEntity::new, REVIVE_ALTAR).build()
    );

    private static void register(Block block, String id) {
        Registry.register(Registries.ITEM, new Identifier(HCRevive.MOD_ID, id), new BlockItem(block, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, new Identifier(HCRevive.MOD_ID, id), block);
    }

    public static void init(){
        register(REVIVE_ALTAR, "revive_altar");
        // register(SOME_BLOCK, "");
    }
}
