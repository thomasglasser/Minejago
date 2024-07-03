package dev.thomasglasser.minejago.world.entity.component;

import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.IElement;

public enum LivingEntityComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (entityAccessor.getEntity() instanceof LivingEntity livingEntity && entityAccessor.getLevel() != null) {
            Power power = entityAccessor.getLevel().holderOrThrow(livingEntity.getData(MinejagoAttachmentTypes.POWER).power()).value();
            IElement icon = new Element() {
                private Vec2 size;

                @Override
                public Vec2 getSize() {
                    if (size == null) size = new Vec2(16, 16);
                    return size;
                }

                @Override
                public void render(GuiGraphics guiGraphics, float x, float y, float maxX, float maxY) {
                    guiGraphics.blit(power.getIcon(), (int) x, (int) y, 16, 16, 0, 0, 32, 32, 32, 32);
                }
            };
            iTooltip.add(icon);
            iTooltip.append(Component.translatable("entity.minejago.living.waila.power", power.getFormattedName()));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MinejagoWailaPlugin.LIVING_ENTITY;
    }
}
