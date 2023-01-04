package dev.thomasglasser.minejago.mixin.net.minecraft.world.entity;

import dev.thomasglasser.minejago.world.entity.IDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class EntityMixin implements IDataHolder
{
    private CompoundTag persistentData = new CompoundTag();

    @Intrinsic
    @Override
    public CompoundTag getPersistentData() {
        return persistentData;
    }
}
