package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import java.util.Optional;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record EntityFocusModifier(EntityPredicate entity, Optional<Boolean> self, Operation operation, double modifier) implements FocusModifier {

    public static final MapCodec<EntityFocusModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EntityPredicate.CODEC.fieldOf("entity").forGetter(EntityFocusModifier::entity),
            Codec.BOOL.optionalFieldOf("self").forGetter(EntityFocusModifier::self),
            Operation.CODEC.fieldOf("operation").forGetter(EntityFocusModifier::operation),
            Codec.DOUBLE.fieldOf("modifier").forGetter(EntityFocusModifier::modifier)).apply(instance, EntityFocusModifier::new));
    @Override
    public MapCodec<? extends FocusModifier> codec() {
        return FocusModifierSerializers.ENTITY_FOCUS_MODIFIER.get();
    }

    public static double checkAndApply(ServerLevel level, Vec3 pos, Entity entity, boolean self, double oldValue) {
        return FocusModifier.checkAndApply(level.holderLookup(MinejagoRegistries.FOCUS_MODIFIER).listElements().map(Holder.Reference::value).filter(modifier -> modifier instanceof EntityFocusModifier entityFocusModifier && entityFocusModifier.self().map(s -> s == self).orElse(true) && entityFocusModifier.entity().matches(level, pos, entity)).toList(), oldValue);
    }
}
