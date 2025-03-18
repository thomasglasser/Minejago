package dev.thomasglasser.minejago.client.dynamiclights.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Range;

public record SpinjitzuLuminance(@Range(from = 0, to = 15) int luminance) implements EntityLuminance {
    public static final MapCodec<SpinjitzuLuminance> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("luminance").forGetter(SpinjitzuLuminance::luminance)).apply(instance, SpinjitzuLuminance::new));

    public static SpinjitzuLuminance of(int luminance) {
        return new SpinjitzuLuminance(luminance);
    }

    @Override
    public Type type() {
        return MinejagoEntityLuminanceTypes.SPINJITZU;
    }

    @Override
    public @Range(from = 0L, to = 15L) int getLuminance(ItemLightSourceManager itemLightSourceManager, Entity entity) {
        return entity.getData(MinejagoAttachmentTypes.SPINJITZU).active() ? this.luminance : 0;
    }
}
