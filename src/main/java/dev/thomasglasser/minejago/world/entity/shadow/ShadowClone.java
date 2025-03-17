package dev.thomasglasser.minejago.world.entity.shadow;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;

public class ShadowClone extends AbstractShadowCopy implements SmartBrainOwner<ShadowClone> {
    public ShadowClone(EntityType<? extends ShadowClone> entityType, Level level) {
        super(entityType, level);
        setCanPickUpLoot(true);
    }

    public ShadowClone(LivingEntity owner) {
        super(MinejagoEntityTypes.SHADOW_CLONE.get(), owner);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
        if (this.getLightLevelDependentMagicValue() < 0.1f)
            kill();
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends ShadowClone>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<ShadowClone>().setPredicate((target, entity) -> {
                    if (target.isAlliedTo(entity)) return false;
                    LivingEntity owner = entity.getOwner();
                    if (owner != null) {
                        if (target.isAlliedTo(owner)) return false;
                        if (target instanceof OwnableEntity ownableEntity && owner.getUUID().equals(ownableEntity.getOwnerUUID()))
                            return false;
                        if (target.getLastHurtByMob() != null && target.getLastHurtByMob().is(owner))
                            return true;
                        if (owner.getLastHurtByMob() != null && owner.getLastHurtByMob().is(target))
                            return true;
                        if (BrainUtils.hasMemory(target.getBrain(), MemoryModuleType.ATTACK_TARGET)) {
                            return BrainUtils.getTargetOfEntity(target) == owner;
                        } else if (target instanceof Mob mob) {
                            return mob.getTarget() == owner;
                        }
                    }
                    return false;
                }),
                new HurtBySensor<>());
    }

    @Override
    public BrainActivityGroup<? extends ShadowClone> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<>(),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()));
    }

    @Override
    public BrainActivityGroup<? extends ShadowClone> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> (target.getUUID() == getOwnerUUID() || target instanceof Player player && player.getAbilities().invulnerable) || (entity.getAttributes().hasAttribute(Attributes.FOLLOW_RANGE) && entity.distanceToSqr(target) >= Math.pow(entity.getAttributeValue(Attributes.FOLLOW_RANGE), 2))),
                new SetWalkTargetToAttackTarget<>(),
                new MoveToWalkTarget<>(),
                new AnimatableMeleeAttack<>(0));
    }
}
