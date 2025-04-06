package dev.thomasglasser.minejago.world.entity.skulkin.raid;

public interface StolenItemsHolder {
    StolenItems minejago$getStolenItems();

    static StolenItemsHolder of(Object object) {
        if (object instanceof StolenItemsHolder stolenItemsHolder) {
            return stolenItemsHolder;
        }
        throw new IllegalArgumentException("Object" + object + " is not a StolenItemsHolder");
    }
}
