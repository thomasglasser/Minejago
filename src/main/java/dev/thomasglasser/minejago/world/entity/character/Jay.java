package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Jay extends Character {
    public Jay(EntityType<? extends Jay> entityType, Level level) {
        super(entityType, level);
        new PowerData(MinejagoPowers.LIGHTNING, true).save(this, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, EntitySpawnReason p_363316_, @Nullable SpawnGroupData p_146749_) {
        populateDefaultEquipmentSlots(this.random, p_146747_);
        return super.finalizeSpawn(p_146746_, p_146747_, p_363316_, p_146749_);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.CHEST, Items.ELYTRA.getDefaultInstance());
    }
}
