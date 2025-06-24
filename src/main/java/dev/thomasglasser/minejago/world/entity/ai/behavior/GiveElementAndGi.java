package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.util.BrainUtils;

public class GiveElementAndGi<E extends PathfinderMob> extends MoveToWalkTarget<E> {
    private static List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS;

    protected LivingEntity target;
    protected BlockPos originalPos;
    protected ResourceKey<Element> elementKey;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        if (MEMORY_REQUIREMENTS == null) {
            MEMORY_REQUIREMENTS = ObjectArrayList.of(
                    Pair.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MinejagoMemoryModuleTypes.SELECTED_ELEMENT.get(), MemoryStatus.VALUE_PRESENT));
        }

        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getMemory(entity, MemoryModuleType.INTERACTION_TARGET);
        this.originalPos = entity.blockPosition();
        this.elementKey = BrainUtils.getMemory(entity, MinejagoMemoryModuleTypes.SELECTED_ELEMENT.get());

        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.2f, 1));

        return entity.getSensing().hasLineOfSight(this.target) && super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
        new ElementData(elementKey, true).save(target, true);
        Holder<Element> elementHolder = entity.level().holderOrThrow(elementKey);
        Element elementValue = elementHolder.value();
        if (elementValue.hasSets())
            equipGi();
        if (target instanceof Player player)
            player.displayClientMessage(Component.translatable(Wu.ELEMENT_GIVEN_KEY, entity.getDisplayName(), target.getDisplayName(), Element.getFormattedName(elementHolder), elementValue.tagline()), false);

        this.target = null;
        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(originalPos, 1.2f, 1));

        BrainUtils.clearMemory(entity, MemoryModuleType.INTERACTION_TARGET);
        BrainUtils.clearMemory(entity, MemoryModuleType.LOOK_TARGET);
        BrainUtils.clearMemory(entity, MinejagoMemoryModuleTypes.SELECTED_ELEMENT.get());
    }

    protected void equipGi() {
        for (ArmorItem gi : MinejagoArmors.TRAINEE_GI_SET.getAllAsItems()) {
            ItemStack armorStack = gi.getDefaultInstance();
            armorStack.set(MinejagoDataComponents.ELEMENT.get(), elementKey);
            EquipmentSlot slot = MinejagoArmors.TRAINEE_GI_SET.getForItem(gi);
            if (target instanceof Player player) {
                ItemStack oldStack = player.getItemBySlot(slot);
                if (!player.addItem(oldStack)) player.drop(oldStack, true);
                player.setItemSlot(slot, armorStack);
            } else {
                ItemStack oldStack = target.getItemBySlot(slot);
                target.spawnAtLocation(oldStack);
                target.setItemSlot(slot, armorStack);
            }
        }
    }
}
