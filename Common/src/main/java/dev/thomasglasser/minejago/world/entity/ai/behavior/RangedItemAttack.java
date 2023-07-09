package dev.thomasglasser.minejago.world.entity.ai.behavior;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.BowAttack;

public class RangedItemAttack<E extends LivingEntity & RangedAttackMob> extends BowAttack<E> {
    private Item item;

    public RangedItemAttack(int delayTicks, Item item) {
        super(delayTicks);
        this.item = item;
    }

    @Override
    protected void start(E entity) {
        BehaviorUtils.lookAtEntity(entity, this.target);
        entity.startUsingItem(ProjectileUtil.getWeaponHoldingHand(entity, item));
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
