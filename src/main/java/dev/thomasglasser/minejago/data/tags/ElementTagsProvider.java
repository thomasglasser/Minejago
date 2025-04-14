package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.tags.ElementTags;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ElementTagsProvider extends ExtendedTagsProvider<Element> {
    public ElementTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        this(output, lookupProvider, Minejago.MOD_ID, existingFileHelper);
    }

    public ElementTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, MinejagoRegistries.ELEMENT, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ElementTags.ELEMENTS_OF_CREATION)
                .add(MinejagoElements.EARTH)
                .add(MinejagoElements.FIRE)
                .add(MinejagoElements.LIGHTNING)
                .add(MinejagoElements.ICE);

        tag(ElementTags.ELEMENTAL_REMAINS)
                .add(MinejagoElements.CREATION);
    }
}
