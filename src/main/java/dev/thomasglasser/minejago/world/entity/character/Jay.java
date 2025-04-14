package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Jay extends Character {
    public Jay(EntityType<? extends Jay> entityType, Level level) {
        super(entityType, level);
        new ElementData(MinejagoElements.LIGHTNING, true).save(this, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        populateDefaultEquipmentSlots(this.random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.CHEST, Items.ELYTRA.getDefaultInstance());
    }
}
