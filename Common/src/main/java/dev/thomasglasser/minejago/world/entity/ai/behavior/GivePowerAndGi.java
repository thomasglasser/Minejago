package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.power.PowerUtils;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
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

import java.util.List;

public class GivePowerAndGi<E extends PathfinderMob> extends MoveToWalkTarget<E> {
    private static List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS;

    protected LivingEntity target;
    protected BlockPos originalPos;
    protected ResourceKey<Power> power;

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        if (MEMORY_REQUIREMENTS == null)
        {
            MEMORY_REQUIREMENTS = ObjectArrayList.of(
                    Pair.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT),
                    Pair.of(MinejagoMemoryModuleTypes.SELECTED_POWER.get(), MemoryStatus.VALUE_PRESENT));
        }

        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.target = BrainUtils.getMemory(entity, MemoryModuleType.INTERACTION_TARGET);
        this.originalPos = entity.blockPosition();
        this.power = (ResourceKey<Power>) BrainUtils.getMemory(entity, MinejagoMemoryModuleTypes.SELECTED_POWER.get());

        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(target, 1.2f, 1));

        return entity.getSensing().hasLineOfSight(this.target) && super.checkExtraStartConditions(level, entity);
    }

    @Override
    protected void stop(E entity) {
        super.stop(entity);
        Services.DATA.setPowerData(new PowerData(power, true), target);
        Power power1 = entity.level().registryAccess().registry(MinejagoRegistries.POWER).orElseThrow().get(power);
        if (power1 != null && power1.hasSets()) equipGi();
        if (power1 != null) target.sendSystemMessage(Component.translatable(Wu.POWER_GIVEN_KEY, entity.getDisplayName(), target.getDisplayName(), power1.getFormattedName(), power1.getTagline()));

        this.target = null;
        BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(originalPos, 1.2f, 1));

        BrainUtils.clearMemory(entity, MemoryModuleType.INTERACTION_TARGET);
        BrainUtils.clearMemory(entity, MemoryModuleType.LOOK_TARGET);
        BrainUtils.clearMemory(entity, MinejagoMemoryModuleTypes.SELECTED_POWER.get());
    }

    protected void equipGi()
    {
        MinejagoArmors.ArmorSet set = MinejagoArmors.TRAINING_GI_SET;
        for (ArmorItem.Type value : ArmorItem.Type.values()) {
            ArmorItem armor = set.getForSlot(value.getSlot()).get();
            if (target instanceof Player player)
            {
                ItemStack oldStack = player.getItemBySlot(value.getSlot());
                if (!player.addItem(oldStack)) player.drop(oldStack, true);
                player.setItemSlot(value.getSlot(), PowerUtils.setPower(armor.getDefaultInstance(), power));
            }
            else
            {
                ItemStack oldStack = target.getItemBySlot(value.getSlot());
                target.spawnAtLocation(oldStack);
                target.setItemSlot(value.getSlot(), PowerUtils.setPower(armor.getDefaultInstance(), power));
            }
        }
    }
}
