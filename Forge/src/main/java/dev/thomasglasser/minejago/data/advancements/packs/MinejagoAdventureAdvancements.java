package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.UnderworldSkeleton;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider.desc;
import static dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider.title;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.*;

public class MinejagoAdventureAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement killASkulkin = Advancement.Builder.advancement()
                .parent(new ResourceLocation("adventure/kill_a_mob"))
                .display(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.SPEED).get(), title(CATEGORY, KILL_A_SKULKIN), desc(CATEGORY, KILL_A_SKULKIN), null, FrameType.TASK, true, true, false)
                .addCriterion("kill_skulkin", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.SKULKINS)))
                .save(saver, Minejago.modLoc(CATEGORY + "/" + KILL_A_SKULKIN), existingFileHelper);

        Advancement collectAllSkeletalChestplates = Advancement.Builder.advancement()
                .parent(killASkulkin).display(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.STRENGTH).get(), title(CATEGORY, COLLECT_ALL_SKELETAL_CHESTPLATES), desc(CATEGORY, COLLECT_ALL_SKELETAL_CHESTPLATES), null, FrameType.CHALLENGE, true, true, false)
                .addCriterion("collect_strength", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.STRENGTH).get()).build()))
                .addCriterion("collect_speed", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.SPEED).get()).build()))
                .addCriterion("collect_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.BOW).get()).build()))
                .addCriterion("collect_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.KNIFE).get()).build()))
                .rewards(AdvancementRewards.Builder.experience(25))
                .save(saver, Minejago.modLoc(CATEGORY + "/" + COLLECT_ALL_SKELETAL_CHESTPLATES), existingFileHelper);
    }
}
