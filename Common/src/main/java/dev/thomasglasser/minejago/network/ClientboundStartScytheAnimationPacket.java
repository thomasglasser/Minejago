package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundStartScytheAnimationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_start_scythe_animation");

    UUID uuid;
    ItemAnimations.ScytheOfQuakes startAnim;
    ItemAnimations.ScytheOfQuakes goAnim;

    public ClientboundStartScytheAnimationPacket(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.startAnim = buf.readEnum(ItemAnimations.ScytheOfQuakes.class);
        this.goAnim = buf.readEnum(ItemAnimations.ScytheOfQuakes.class);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeEnum(startAnim);
        buf.writeEnum(goAnim);
    }

    public static FriendlyByteBuf write(UUID uuid, ItemAnimations.ScytheOfQuakes startAnim, ItemAnimations.ScytheOfQuakes goAnim) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeEnum(startAnim);
        buf.writeEnum(goAnim);

        return buf;
    }

    // ON CLIENT
    public void handle(@Nullable Player player) {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) AnimationUtils.startAnimation(startAnim.getAnimation(), goAnim != null ? goAnim.getAnimation() : null, ClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.THIRD_PERSON_MODEL);
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
