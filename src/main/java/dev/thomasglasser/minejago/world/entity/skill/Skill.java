package dev.thomasglasser.minejago.world.entity.skill;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public enum Skill implements StringRepresentable {
    AGILITY,
    STEALTH,
    DEXTERITY,
    PROFICIENCY;

    private final ResourceLocation icon = Minejago.modLoc("textures/gui/skill/" + getSerializedName() + ".png");
    private final Component displayName = Component.translatable(Minejago.modLoc(getSerializedName()).toLanguageKey("skill"));

    public Component displayName() {
        return displayName;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }

    public static Skill fromSerializedName(String serializedName) {
        return Skill.valueOf(serializedName.toUpperCase());
    }
}
