package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;

public class StolenItems extends SavedData {
    private static final String FILE_ID = "stolen_items";
    private final List<ItemStack> currentlyStolen = new ArrayList<>();
    private final List<ItemStack> previouslyStolen = new ArrayList<>();

    public static SavedData.Factory<StolenItems> factory(ServerLevel level) {
        return new SavedData.Factory<>(StolenItems::new, (compoundTag, provider) -> StolenItems.load(level, compoundTag), DataFixTypes.SAVED_DATA_RAIDS);
    }

    public boolean hasCurrentlyStolen(Predicate<ItemStack> predicate) {
        return currentlyStolen.stream().anyMatch(predicate);
    }

    public boolean hasPreviouslyStolen(Predicate<ItemStack> predicate) {
        return previouslyStolen.stream().anyMatch(predicate);
    }

    public boolean hasEverStolen(Predicate<ItemStack> predicate) {
        return hasCurrentlyStolen(predicate) || hasPreviouslyStolen(predicate);
    }

    public List<ItemStack> getCurrentlyStolenMatching(Predicate<ItemStack> predicate) {
        return currentlyStolen.stream().filter(predicate).toList();
    }

    public List<ItemStack> getPreviouslyStolenMatching(Predicate<ItemStack> predicate) {
        return previouslyStolen.stream().filter(predicate).toList();
    }

    public List<ItemStack> getEverStolenMatching(Predicate<ItemStack> predicate) {
        ArrayList<ItemStack> matching = new ArrayList<>();
        matching.addAll(getCurrentlyStolenMatching(predicate));
        matching.addAll(getPreviouslyStolenMatching(predicate));
        return matching;
    }

    public List<ItemStack> removeCurrentlyStolenMatching(Predicate<ItemStack> predicate) {
        List<ItemStack> removed = new ArrayList<>();
        for (ItemStack stack : List.copyOf(currentlyStolen)) {
            if (predicate.test(stack)) {
                removed.add(stack.copy());
                remove(stack);
            }
        }
        return removed;
    }

    public void add(ItemStack item) {
        currentlyStolen.add(item);
        setDirty();
    }

    public void remove(ItemStack item) {
        currentlyStolen.remove(item);
        previouslyStolen.add(item);
        setDirty();
    }

    public static StolenItems load(ServerLevel level, CompoundTag tag) {
        StolenItems stolenItems = new StolenItems();
        ListTag currentlyStolen = tag.getList("CurrentlyStolen", Tag.TAG_COMPOUND);
        for (int i = 0; i < currentlyStolen.size(); i++) {
            CompoundTag compoundTag = currentlyStolen.getCompound(i);
            stolenItems.currentlyStolen.add(ItemStack.parseOptional(level.registryAccess(), compoundTag));
        }
        ListTag previouslyStolen = tag.getList("PreviouslyStolen", Tag.TAG_COMPOUND);
        for (int i = 0; i < previouslyStolen.size(); i++) {
            CompoundTag compoundTag = previouslyStolen.getCompound(i);
            stolenItems.previouslyStolen.add(ItemStack.parseOptional(level.registryAccess(), compoundTag));
        }
        return stolenItems;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag listTag = new ListTag();
        for (ItemStack item : currentlyStolen) {
            CompoundTag compoundTag = new CompoundTag();
            item.save(registries, compoundTag);
            listTag.add(compoundTag);
        }
        tag.put("CurrentlyStolen", listTag);
        listTag = new ListTag();
        for (ItemStack item : previouslyStolen) {
            CompoundTag compoundTag = new CompoundTag();
            item.save(registries, compoundTag);
            listTag.add(compoundTag);
        }
        tag.put("PreviouslyStolen", listTag);
        return tag;
    }

    public static String getFileId() {
        return FILE_ID;
    }
}
