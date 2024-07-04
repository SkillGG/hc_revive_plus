package mod.hc.revive_plus.items;

import mod.hc.revive_plus.HCRevive;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier(HCRevive.MOD_ID, id);
        // Return the registered item!
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item REVIVE_TOKEN = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new Item.Settings().maxCount(1).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(HCRevive.REVIVE_EFFECT, 1), 1f).alwaysEdible().build())),
            "revive_token"
    );

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
            itemGroup.add(ModItems.REVIVE_TOKEN);
        });
    }

}
