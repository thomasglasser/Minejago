package dev.thomasglasser.minejago.world.entity.component;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
            ResourceKey<Power> powerKey = livingEntity.getData(MinejagoAttachmentTypes.POWER).power();
            if (powerKey != MinejagoPowers.NONE) {
                Registry<Power> registry = entityAccessor.getLevel().registryAccess().lookupOrThrow(MinejagoRegistries.POWER);
                Power power = registry.getValue(powerKey);
                IElement icon = new Element() {
                    private Vec2 size;

                    @Override
                    public Vec2 getSize() {
                        if (size == null) size = new Vec2(10, 10);
                        return size;
                    }

                    @Override
                    public void render(GuiGraphics guiGraphics, float x, float y, float maxX, float maxY) {
                        guiGraphics.blit(RenderType::guiTextured, power.getIcon(registry), (int) x - 2, (int) y - 1, 0, 0, 10, 10, 32, 32, 32, 32);
                    }
                };
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("entity.minejago.living.waila.power", power.getFormattedName(registry)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MinejagoWailaPlugin.LIVING_ENTITY;
    }
}
