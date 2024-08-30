package dev.thomasglasser.minejago.world.focus.modifier.itemstack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

public class ItemStackFocusModifiers {
    private static final List<ItemStackFocusModifier> ITEM_STACK_FOCUS_MODIFIERS = new ArrayList<>();

    private ItemStackFocusModifiers() {
        throw new UnsupportedOperationException("ItemFocusModifiers only contains static definitions.");
    }

    public static void load(ResourceManager resourceManager) {
        ITEM_STACK_FOCUS_MODIFIERS.clear();
        resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/item", (path) -> path.getPath().endsWith(".json"))
                .forEach(ItemStackFocusModifiers::load);
    }

    private static void load(ResourceLocation resourceId, Resource resource) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

        try {
            InputStreamReader reader = new InputStreamReader(resource.open());

            try {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                ItemStackFocusModifier.fromJson(id, json).ifPresent(ITEM_STACK_FOCUS_MODIFIERS::add);
            } catch (Throwable var7) {
                try {
                    reader.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            reader.close();
        } catch (IllegalStateException | IOException var8) {
            Minejago.LOGGER.warn("Failed to load item stack focus modifier \"" + id + "\".");
        }
    }

    public static double applyModifier(ItemStack stack, double oldValue) {
        List<ItemStackFocusModifier> data = ITEM_STACK_FOCUS_MODIFIERS.stream().filter(modifier -> {
            if (!modifier.getStack().isEmpty()) {
                AtomicBoolean sameComponents = new AtomicBoolean(true);
                modifier.getStack().getComponents().forEach(component -> {
                    if (!component.equals(stack.get(component.type()))) {
                        sameComponents.set(false);
                    }
                });
                return modifier.getStack().getItem() == stack.getItem() && sameComponents.get();
            } else
                return modifier.getItem() == stack.getItem();
        }).toList();
        if (!data.isEmpty()) {
            double newValue = oldValue;
            for (ItemStackFocusModifier modifier : data) {
                newValue = modifier.apply(newValue);
            }
            return newValue;
        }
        return oldValue;
    }
}
