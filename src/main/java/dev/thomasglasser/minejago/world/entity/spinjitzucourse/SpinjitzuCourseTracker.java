package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;

public interface SpinjitzuCourseTracker {
    void markVisited(AbstractSpinjitzuCourseElement<?> element, Player visitor);

    class SpinjitzuCourseData {
        private final List<AbstractSpinjitzuCourseElement<?>> visited = new ArrayList<>();
        private int runTicks = 0;

        public List<AbstractSpinjitzuCourseElement<?>> getVisited() {
            return visited;
        }

        public int getRunTicks() {
            return runTicks;
        }

        public void tick() {
            runTicks++;
        }

        public void markVisited(AbstractSpinjitzuCourseElement<?> element) {
            visited.add(element);
        }

        public void resetRun() {
            visited.clear();
            runTicks = 0;
        }
    }
}
