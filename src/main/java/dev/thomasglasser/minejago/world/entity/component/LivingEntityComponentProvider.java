package dev.thomasglasser.minejago.world.entity.component;

import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public enum LivingEntityComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (entityAccessor.getEntity() instanceof LivingEntity livingEntity && entityAccessor.getLevel() != null) {
            ResourceKey<Element> elementKey = livingEntity.getData(MinejagoAttachmentTypes.ELEMENT).element();
            Holder<Element> elementHolder = entityAccessor.getLevel().holderOrThrow(elementKey);
            if (elementKey != Elements.NONE) {
                IElement icon = new snownee.jade.api.ui.Element() {
                    private Vec2 size;

                    @Override
                    public Vec2 getSize() {
                        if (size == null) size = new Vec2(10, 10);
                        return size;
                    }

                    @Override
                    public void render(GuiGraphics guiGraphics, float x, float y, float maxX, float maxY) {
                        guiGraphics.pose().pushPose();
                        guiGraphics.blit(Element.getIcon(elementKey), (int) x - 2, (int) y - 1, 10, 10, 32, 32, 32, 32, 32, 32);
                        guiGraphics.pose().popPose();
                    }
                };
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("entity.minejago.living.waila.element", Element.getFormattedName(elementHolder)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MinejagoWailaPlugin.LIVING_ENTITY;
    }
}
