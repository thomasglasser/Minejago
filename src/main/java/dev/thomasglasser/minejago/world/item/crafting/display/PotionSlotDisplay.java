package dev.thomasglasser.minejago.world.item.crafting.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record PotionSlotDisplay(Holder<Potion> potion, Holder<Item> container) implements SlotDisplay {
    public static final MapCodec<PotionSlotDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(
            p_379658_ -> p_379658_.group(
                    Potion.CODEC.fieldOf("potion").forGetter(PotionSlotDisplay::potion),
                    Item.CODEC.fieldOf("container").forGetter(PotionSlotDisplay::container)).apply(p_379658_, PotionSlotDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PotionSlotDisplay> STREAM_CODEC = StreamCodec.composite(
            Potion.STREAM_CODEC, PotionSlotDisplay::potion,
            ByteBufCodecs.holderRegistry(Registries.ITEM), PotionSlotDisplay::container,
            PotionSlotDisplay::new);
    public static final SlotDisplay.Type<PotionSlotDisplay> TYPE = new SlotDisplay.Type<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public SlotDisplay.Type<PotionSlotDisplay> type() {
        return TYPE;
    }

    @Override
    public <T> Stream<T> resolve(ContextMap p_381143_, DisplayContentsFactory<T> p_381015_) {
        return p_381015_ instanceof DisplayContentsFactory.ForStacks<T> forstacks ? Stream.of(forstacks.forStack(PotionContents.createItemStack(container.value(), potion))) : Stream.empty();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof PotionSlotDisplay otherPotion && this.potion.equals(otherPotion.potion) && this.container.equals(otherPotion.container) || other instanceof ItemStackSlotDisplay otherStack && this.potion.equals(otherStack.stack().get(DataComponents.POTION_CONTENTS).potion()) && this.container.equals(otherStack.stack().getItem());
    }

    @Override
    public boolean isEnabled(FeatureFlagSet p_380009_) {
        return this.potion.value().isEnabled(p_380009_) && this.container.value().isEnabled(p_380009_);
    }
}
