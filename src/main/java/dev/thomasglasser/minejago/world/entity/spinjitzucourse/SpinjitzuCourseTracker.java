package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import net.minecraft.world.entity.player.Player;

public interface SpinjitzuCourseTracker {
    void markVisited(AbstractSpinjitzuCourseElement<?> element, Player visitor);
}
