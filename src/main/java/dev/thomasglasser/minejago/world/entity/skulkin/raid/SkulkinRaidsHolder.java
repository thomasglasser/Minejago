package dev.thomasglasser.minejago.world.entity.skulkin.raid;

public interface SkulkinRaidsHolder {
    SkulkinRaids minejago$getSkulkinRaids();

    static SkulkinRaidsHolder of(Object object) {
        if (object instanceof SkulkinRaidsHolder skulkinRaidsHolder) {
            return skulkinRaidsHolder;
        }
        throw new IllegalArgumentException("Object" + object + " is not a SkulkinRaidsHolder");
    }
}
