package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.DoSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GetPowerTrigger;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.PowerUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
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

        powers(root, saver, existingFileHelper);
        people(root, saver, existingFileHelper);
    }

    protected Advancement make(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Advancement root, ItemLike displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, Map<String, AbstractCriterionTriggerInstance> triggers)
    {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, title(CATEGORY, id), desc(CATEGORY, id), null, frameType, toast, announce, hidden);

        triggers.forEach(builder::addCriterion);

        return builder.save(saver, Minejago.modLoc(CATEGORY + "/" + id), existingFileHelper);
    }

    protected Advancement make(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, Advancement root, ItemStack displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, Map<String, AbstractCriterionTriggerInstance> triggers)
    {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, title(CATEGORY, id), desc(CATEGORY, id), null, frameType, toast, announce, hidden);

        triggers.forEach(builder::addCriterion);

        return builder.save(saver, Minejago.modLoc(CATEGORY + "/" + id), existingFileHelper);
    }

    private void powers(Advancement root, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper)
    {
        List<ItemStack> armors = MinejagoPowers.getArmorForAll(MinejagoPowers.getBasePowers());

        Advancement doSpinjitzu = make(saver, existingFileHelper, root, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.REGULAR_TEA.get()), DO_SPINJITZU, FrameType.TASK, true, true, false, Map.of(
                "do_spinjitzu", DoSpinjitzuTrigger.TriggerInstance.didSpinjitzu()
        ));

        Advancement getPower = make(saver, existingFileHelper, root, PowerUtils.setPower(MinejagoArmors.TRAINING_GI_SET.HEAD.get().getDefaultInstance(), MinejagoPowers.FIRE), GET_POWER, FrameType.TASK, true, true, false, Map.of(
                "get_power", GetPowerTrigger.TriggerInstance.gotAnyPower()
        ));
    }

    private void people(Advancement root, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper)
    {
        Advancement interactWithMainSix = make(saver, existingFileHelper, root, MinejagoItems.IRON_KATANA.get(), INTERACT_WITH_MAIN_SIX, FrameType.GOAL, true, true, false,
                Map.of(
                        "interact_wu", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.WU.get()).build())),
                        "interact_nya", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.NYA.get()).build())),
                        "interact_jay", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.JAY.get()).build())),
                        "interact_kai", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.KAI.get()).build())),
                        "interact_cole", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.COLE.get()).build())),
                        "interact_zane", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.ZANE.get()).build()))
                ));
    }
}
