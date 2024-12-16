package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.WuModel;
import dev.thomasglasser.minejago.world.entity.character.Character;
import java.util.Calendar;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class HolidayCharacterRenderer<T extends Character> extends CharacterRenderer<T> {
    private ResourceLocation holidayTexture;

    public HolidayCharacterRenderer(EntityRendererProvider.Context context) {
        super(context, new WuModel<>());
    }

    @Override
    public ResourceLocation getTextureLocation(T animatable) {
        if (holidayTexture == null) holidayTexture = Minejago.modLoc("textures/entity/character/holiday_" + BuiltInRegistries.ENTITY_TYPE.getKey(animatable.getType()).getPath() + ".png");
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) ? holidayTexture : model.getTextureResource(animatable);
    }
}
