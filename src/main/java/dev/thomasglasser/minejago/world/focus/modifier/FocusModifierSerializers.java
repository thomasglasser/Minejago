package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;

public class FocusModifierSerializers {
    private static final DeferredRegister<MapCodec<? extends FocusModifier>> FOCUS_MODIFIER_SERIALIZERS = DeferredRegister.create(MinejagoRegistries.FOCUS_MODIFIER_SERIALIZER, Minejago.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends FocusModifier>, MapCodec<BlockFocusModifier>> BLOCK_FOCUS_MODIFIER = FOCUS_MODIFIER_SERIALIZERS.register("block", () -> BlockFocusModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends FocusModifier>, MapCodec<EntityFocusModifier>> ENTITY_FOCUS_MODIFIER = FOCUS_MODIFIER_SERIALIZERS.register("entity", () -> EntityFocusModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends FocusModifier>, MapCodec<ItemFocusModifier>> ITEM_FOCUS_MODIFIER = FOCUS_MODIFIER_SERIALIZERS.register("item", () -> ItemFocusModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends FocusModifier>, MapCodec<LocationFocusModifier>> LOCATION_FOCUS_MODIFIER = FOCUS_MODIFIER_SERIALIZERS.register("location", () -> LocationFocusModifier.CODEC);
    public static final DeferredHolder<MapCodec<? extends FocusModifier>, MapCodec<WorldFocusModifier>> WORLD_FOCUS_MODIFIER = FOCUS_MODIFIER_SERIALIZERS.register("world", () -> WorldFocusModifier.CODEC);

    public static void init() {}
}
