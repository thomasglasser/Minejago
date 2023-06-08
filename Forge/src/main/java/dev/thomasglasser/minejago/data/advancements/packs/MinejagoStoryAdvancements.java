package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider.desc;
import static dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider.title;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.*;

public class MinejagoStoryAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    @ParametersAreNonnullByDefault
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement()
                .display(MinejagoItems.SCYTHE_OF_QUAKES.get(), title(CATEGORY, ROOT), desc(CATEGORY, ROOT), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false)
                .addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE))
                .save(saver, Minejago.modLoc(CATEGORY + "/" + ROOT), existingFileHelper);

        Advancement interactWithMainSix = Advancement.Builder.advancement()
                .parent(root)
                .display(MinejagoItems.IRON_KATANA.get(), title(CATEGORY, INTERACT_WITH_MAIN_SIX), desc(CATEGORY, INTERACT_WITH_MAIN_SIX), null, FrameType.GOAL, true, true, false)
                .addCriterion("interact_wu", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.WU.get()).build())))
                .addCriterion("interact_nya", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.NYA.get()).build())))
                .addCriterion("interact_jay", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.JAY.get()).build())))
                .addCriterion("interact_kai", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.KAI.get()).build())))
                .addCriterion("interact_cole", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.COLE.get()).build())))
                .addCriterion("interact_zane", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.ZANE.get()).build())))
                .save(saver, Minejago.modLoc(CATEGORY + "/" + INTERACT_WITH_MAIN_SIX), existingFileHelper);
    }
}
