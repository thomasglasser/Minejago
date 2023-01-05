package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.Objects;

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
            MinejagoPaintingVariants.onInteract(player, world, hand, entity);
            return InteractionResult.SUCCESS;
        });

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) ->
        {
            MinejagoItems.getItemTabs().forEach((tab, itemLikes) -> {
                if (group == tab)
                {
                    itemLikes.forEach((itemLike) -> entries.accept(Objects.requireNonNull(BuiltInRegistries.ITEM.get(itemLike))));
                }
            });

            if (group == CreativeModeTabs.FOOD_AND_DRINKS)
            {
                for (Potion potion : BuiltInRegistries.POTION) {
                    if (potion != Potions.EMPTY) {
                        entries.accept(PotionUtils.setPotion(new ItemStack(BuiltInRegistries.ITEM.get(MinejagoItems.FILLED_TEACUP.getId())), potion));
                    }
                }
            }

            if (group == CreativeModeTabs.COMBAT)
            {
                for (RegistryObject<Item> item : MinejagoArmor.ARMOR.getEntries())
                {
                    entries.accept(BuiltInRegistries.ITEM.get(item.getId()));
                }
            }
        });
    }
}
