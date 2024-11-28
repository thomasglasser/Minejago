package dev.thomasglasser.minejago.world.entity.skill;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public enum Skill implements StringRepresentable {
    AGILITY,
    STEALTH,
    DEXTERITY,
    TOOL_PROFICIENCY;

    private final ResourceLocation icon = Minejago.modLoc("textures/gui/skill/" + getSerializedName() + ".png");
    private final String languageKey = Minejago.modLoc(getSerializedName()).toLanguageKey("skill");

    public String toLanguageKey() {
        return languageKey;
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
