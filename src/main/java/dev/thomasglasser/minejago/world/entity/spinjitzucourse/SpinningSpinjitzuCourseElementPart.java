package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;

public class SpinningSpinjitzuCourseElementPart<T extends AbstractSpinjitzuCourseElement<T>> extends AbstractSpinjitzuCourseElementPart<T> {
    public SpinningSpinjitzuCourseElementPart(T parent, String name, float width, float height, float offsetX, float offsetY, float offsetZ) {
        super(parent, name, width, height, offsetX, offsetY, offsetZ);
    }

    @Override
    public void calculatePosition() {
        double angleRadians = getParent().tickCount * MinejagoServerConfig.get().courseSpeed.get();
        angleRadians += switch (getParent().getDirection().getClockWise()) {
            case DOWN, UP, NORTH -> 0;
            case EAST -> Math.PI * 1.5;
            case SOUTH -> Math.PI;
            case WEST -> Math.PI / 2.0;
        };
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);
        double rotatedX = offsetX * cos + offsetZ * sin;
        double rotatedZ = offsetZ * cos - offsetX * sin;
        double resultingX = rotatedX + getParent().getX();
        double resultingZ = rotatedZ + getParent().getZ();
        this.moveTo(resultingX, getParent().getY() + offsetY, resultingZ);
    }
}
