package dev.thomasglasser.minejago.client.animation;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import static dev.thomasglasser.minejago.Minejago.MOD_ID;

public class MinejagoPlayerAnimator {

    /**
     * This will map player objects and the animation containers. To retrieve the animation for the player, you'll need that exact player object.
     */
    public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> animationData = new IdentityHashMap<>();


    /**
     * This is where we'll store the loaded animations.
     * The identifier has to be hash-able, but can be anything.
     * (String/ResourceLocation both should work)
     */
    public static Map<ResourceLocation, KeyframeAnimation> animations = new HashMap<>();

    //This method will set your mods animation into the library.
    public static void registerPlayerAnimation(AbstractClientPlayer player, AnimationStack stack) {
        //This will be invoked for every new player
        var layer = new ModifierLayer<>();
        stack.addAnimLayer(1000, layer); //Register the layer with a priority
        //The priority will tell, how important is this animation compared to other mods. Higher number means higher priority
        //Mods with higher priority will override the lower priority mods (if they want to animation anything)

        //If you want to map with Players, you have to use IdentityHashMap. that doesn't require hashCode function but does require the exact same object.
        MinejagoPlayerAnimator.animationData.put(player, layer);

        //Alternative solution is to Mixin the mod animation container into the player class. But that requires knowing how to use Mixins.
    }
}
