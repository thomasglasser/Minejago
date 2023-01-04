package dev.thomasglasser.minejago.client.animation;

import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import net.minecraft.client.player.AbstractClientPlayer;

import java.util.IdentityHashMap;
import java.util.Map;

public class MinejagoPlayerAnimator {

    /**
     * This will map player objects and the animation containers. To retrieve the animation for the player, you'll need that exact player object.
     */
    public static final Map<AbstractClientPlayer, ModifierLayer<IAnimation>> animationData = new IdentityHashMap<>();

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
