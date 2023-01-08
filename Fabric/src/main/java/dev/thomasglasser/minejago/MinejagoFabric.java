package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.InteractionResult;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();

        registerEvents();
    }

    private void registerEvents()
    {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            MinejagoEntityEvents.onPlayerEntityInteract(player, world, hand, entity);
            return InteractionResult.SUCCESS;
        });

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) ->
        {
            entries.acceptAll(MinejagoItems.getItemsForTab(group));
        });
    }
}
