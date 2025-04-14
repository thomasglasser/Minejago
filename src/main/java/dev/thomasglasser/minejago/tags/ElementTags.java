package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.element.Element;
import net.minecraft.tags.TagKey;

public class ElementTags {
    public static final TagKey<Element> ELEMENTS_OF_CREATION = create("elements_of_creation");
    public static final TagKey<Element> ELEMENTAL_REMAINS = create("elemental_remains");

    private static TagKey<Element> create(String name) {
        return TagKey.create(MinejagoRegistries.ELEMENT, Minejago.modLoc(name));
    }
}
