package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

public record ItemFocusModifier(ItemPredicate item, Operation operation, double modifier) implements FocusModifier {

    public static final MapCodec<ItemFocusModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemPredicate.CODEC.fieldOf("item").forGetter(ItemFocusModifier::item),
            Operation.CODEC.fieldOf("operation").forGetter(ItemFocusModifier::operation),
            Codec.DOUBLE.fieldOf("modifier").forGetter(ItemFocusModifier::modifier)).apply(instance, ItemFocusModifier::new));
    @Override
    public MapCodec<? extends FocusModifier> codec() {
        return FocusModifierSerializers.ITEM_FOCUS_MODIFIER.get();
    }

    public static double checkAndApply(ServerLevel level, ItemStack stack, double oldValue) {
        return FocusModifier.checkAndApply(level.holderLookup(MinejagoRegistries.FOCUS_MODIFIER).listElements().map(Holder.Reference::value).filter(modifier -> modifier instanceof ItemFocusModifier itemFocusModifier && itemFocusModifier.item().test(stack)).toList(), oldValue);
    }
}
