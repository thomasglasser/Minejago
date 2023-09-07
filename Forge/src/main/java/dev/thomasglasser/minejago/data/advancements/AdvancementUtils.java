package dev.thomasglasser.minejago.data.advancements;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;

public class AdvancementUtils
{
    private final Consumer<Advancement> saver;
    private final ExistingFileHelper existingFileHelper;
    private final LanguageProvider enUs;
    private final String category;

    public AdvancementUtils(Consumer<Advancement> saver, ExistingFileHelper existingFileHelper, LanguageProvider enUs, String category)
    {
        this.saver = saver;
        this.existingFileHelper = existingFileHelper;
        this.enUs = enUs;
        this.category = category;
    }

    public static Component title(String category, String path)
    {
        return Component.translatable("advancement." + Minejago.MOD_ID + "." + category + "." + path + ".title");
    }

    public static Component desc(String category, String path)
    {
        return Component.translatable("advancement." + Minejago.MOD_ID + "." + category + "." + path + ".desc");
    }

    public Advancement root(ItemLike displayItem, String id, ResourceLocation background, FrameType frameType, boolean toast, boolean announce, boolean hidden, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers, String title, String desc)
    {
        Component titleKey = title(category, id);
        Component descKey = desc(category, id);

        add(titleKey, title);
        add(descKey, desc);

        Advancement.Builder builder = Advancement.Builder.advancement()
                .display(displayItem, titleKey, descKey, background, frameType, toast, announce, hidden);

        return makeInternal(builder, id, rewards, triggers);
    }

    public Advancement make(Advancement root, ItemLike displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers, String title, String desc)
    {
        Component titleKey = title(category, id);
        Component descKey = desc(category, id);

        add(titleKey, title);
        add(descKey, desc);

        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, titleKey, descKey, null, frameType, toast, announce, hidden);

        return makeInternal(builder, id, rewards, triggers);
    }

    public Advancement make(Advancement root, ItemStack displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers, String title, String desc)
    {
        Component titleKey = title(category, id);
        Component descKey = desc(category, id);

        add(titleKey, title);
        add(descKey, desc);

        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, titleKey, descKey, null, frameType, toast, announce, hidden);

        return makeInternal(builder, id, rewards, triggers);
    }

    public Advancement make(ResourceLocation root, ItemLike displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers, String title, String desc)
    {
        Component titleKey = title(category, id);
        Component descKey = desc(category, id);

        add(titleKey, title);
        add(descKey, desc);

        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, titleKey, descKey, null, frameType, toast, announce, hidden);

        return makeInternal(builder, id, rewards, triggers);
    }

    public Advancement make(ResourceLocation root, ItemStack displayItem, String id, FrameType frameType, boolean toast, boolean announce, boolean hidden, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers, String title, String desc)
    {
        Component titleKey = title(category, id);
        Component descKey = desc(category, id);

        add(titleKey, title);
        add(descKey, desc);

        Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(root)
                .display(displayItem, titleKey, descKey, null, frameType, toast, announce, hidden);

        return makeInternal(builder, id, rewards, triggers);
    }

    private Advancement makeInternal(Advancement.Builder builder, String id, @Nullable AdvancementRewards rewards, Map<String, AbstractCriterionTriggerInstance> triggers)
    {
        if (rewards != null)
            builder.rewards(rewards);

        SortedMap<String, AbstractCriterionTriggerInstance> sm = new TreeMap<>(triggers);
        sm.forEach(builder::addCriterion);

        return builder.save(saver, Minejago.modLoc(category + "/" + id), existingFileHelper);
    }

    public void add(Component component, String name)
    {
        enUs.add(((TranslatableContents)component.getContents()).getKey(), name);
    }
}
