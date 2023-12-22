package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SpinjitzuAttachment
{
    public static final Codec<SpinjitzuAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("unlocked").forGetter(SpinjitzuAttachment::isUnlocked),
            Codec.BOOL.fieldOf("active").forGetter(SpinjitzuAttachment::isActive))
            .apply(instance, SpinjitzuAttachment::new));

    private boolean active;
    private boolean unlocked;

    public SpinjitzuAttachment()
    {
        this(false, false);
    }

    public SpinjitzuAttachment(boolean unlocked, boolean active)
    {
        this.unlocked = unlocked;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
