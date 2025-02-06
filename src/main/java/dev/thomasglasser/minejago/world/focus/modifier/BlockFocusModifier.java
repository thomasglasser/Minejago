package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;

public record BlockFocusModifier(BlockPredicate block, Operation operation, double modifier) implements FocusModifier {

    public static final MapCodec<BlockFocusModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockPredicate.CODEC.fieldOf("block").forGetter(BlockFocusModifier::block),
            Operation.CODEC.fieldOf("operation").forGetter(BlockFocusModifier::operation),
            Codec.DOUBLE.fieldOf("modifier").forGetter(BlockFocusModifier::modifier)).apply(instance, BlockFocusModifier::new));
    @Override
    public MapCodec<? extends FocusModifier> codec() {
        return MinejagoFocusModifierSerializers.BLOCK_FOCUS_MODIFIER.get();
    }

    public static double checkAndApply(ServerLevel level, BlockPos pos, double oldValue) {
        return FocusModifier.checkAndApply(level.holderLookup(MinejagoRegistries.FOCUS_MODIFIER).listElements().map(Holder.Reference::value).filter(modifier -> modifier instanceof BlockFocusModifier blockFocusModifier && blockFocusModifier.block().matches(level, pos)).toList(), oldValue);
    }
}
