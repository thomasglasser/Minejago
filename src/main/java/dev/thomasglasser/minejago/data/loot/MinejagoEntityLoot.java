package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class MinejagoEntityLoot extends EntityLootSubProvider {
    public MinejagoEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        add(MinejagoEntityTypes.WU.get(), noDrop());
        add(MinejagoEntityTypes.KAI.get(), noDrop());
        add(MinejagoEntityTypes.NYA.get(), noDrop());
        add(MinejagoEntityTypes.JAY.get(), noDrop());
        add(MinejagoEntityTypes.COLE.get(), noDrop());
        add(MinejagoEntityTypes.ZANE.get(), noDrop());

        add(MinejagoEntityTypes.SKULKIN.get(), skulkin());
        add(MinejagoEntityTypes.KRUNCHA.get(), skulkin());
        add(MinejagoEntityTypes.NUCKAL.get(), skulkin());
        add(MinejagoEntityTypes.SKULKIN_HORSE.get(), skulkin());
        add(MinejagoEntityTypes.SAMUKAI.get(), skulkin());
        add(MinejagoEntityTypes.SKULL_TRUCK.get(), skulkin());
        add(MinejagoEntityTypes.SKULL_MOTORBIKE.get(), skulkin());
        add(MinejagoEntityTypes.SPYKOR.get(), skulkin()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.STRING)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.SPIDER_EYE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(-1.0F, 1.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())));

        add(MinejagoEntityTypes.EARTH_DRAGON.get(), noDrop());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return MinejagoEntityTypes.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
    }

    public static LootTable.Builder noDrop() {
        return BlockLootSubProvider.noDrop();
    }

    protected LootTable.Builder skulkin() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))));
    }
}
