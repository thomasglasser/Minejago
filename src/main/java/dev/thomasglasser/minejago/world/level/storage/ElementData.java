package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundSyncElementDataPayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public record ElementData(ResourceKey<Element> element, boolean given) {
    public static final Codec<ElementData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(MinejagoRegistries.ELEMENT).fieldOf("element").forGetter(ElementData::element),
            Codec.BOOL.fieldOf("given").forGetter(ElementData::given))
            .apply(instance, ElementData::new));
    public static final StreamCodec<ByteBuf, ElementData> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(MinejagoRegistries.ELEMENT), ElementData::element,
            ByteBufCodecs.BOOL, ElementData::given,
            ElementData::new);

    public ElementData() {
        this(MinejagoElements.NONE, false);
    }

    public ElementData(ResourceLocation location, boolean given) {
        this(ResourceKey.create(MinejagoRegistries.ELEMENT, location), given);
    }

    @Override
    public ResourceKey<Element> element() {
        return element == null ? MinejagoElements.NONE : element;
    }

    public void save(LivingEntity entity, boolean syncToClient) {
        entity.setData(MinejagoAttachmentTypes.ELEMENT, this);
        if (element != MinejagoElements.NONE && entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GOT_ELEMENT.get().trigger(serverPlayer, this.element());
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncElementDataPayload(entity.getId(), this), entity.getServer());
    }
}
