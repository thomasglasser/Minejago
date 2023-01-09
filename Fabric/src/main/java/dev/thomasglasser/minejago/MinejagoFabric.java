package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();

        registerEvents();

        registerEntityAttributes();

        MinejagoPotionBrewing.addMixes();
    }

    private void registerEvents()
    {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            MinejagoEntityEvents.onPlayerEntityInteract(player, world, hand, entity);
            return InteractionResult.PASS;
        });

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) ->
                entries.acceptAll(MinejagoItems.getItemsForTab(group)));
        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) ->
        {
            MinejagoEntityEvents.onLivingAttack(DamageSource.playerAttack(player));
            return InteractionResult.PASS;
        }));
    }

    private void registerEntityAttributes()
    {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet())
        {
            FabricDefaultAttributeRegistry.register(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }
}
