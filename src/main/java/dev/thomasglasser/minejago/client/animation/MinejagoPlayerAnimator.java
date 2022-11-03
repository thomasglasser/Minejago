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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import static dev.thomasglasser.minejago.Minejago.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //Set the player construct callback. It can be a lambda function.
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MinejagoPlayerAnimator::registerPlayerAnimation);
    }

    //This method will set your mods animation into the library.
    private static void registerPlayerAnimation(AbstractClientPlayer player, AnimationStack stack) {
        //This will be invoked for every new player
        var layer = new ModifierLayer<>();
        stack.addAnimLayer(1000, layer); //Register the layer with a priority
        //The priority will tell, how important is this animation compared to other mods. Higher number means higher priority
        //Mods with higher priority will override the lower priority mods (if they want to animation anything)

        //If you want to map with Players, you have to use IdentityHashMap. that doesn't require hashCode function but does require the exact same object.
        MinejagoPlayerAnimator.animationData.put(player, layer);

        //Alternative solution is to Mixin the mod animation container into the player class. But that requires knowing how to use Mixins.
    }

    /**
     * Resource loading, it will allow animation loading from resourcePacks (animations work on client-side, use client-side resources)
     */
    @SubscribeEvent
    public static void reloadResources(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ResourceManagerReloadListener() {
            //This function will be called every time the user reloads the resources (and once when the game starts)
            @Override
            public void onResourceManagerReload(@NotNull ResourceManager manager) {
                var map = new HashMap<ResourceLocation, KeyframeAnimation>();
                for (
                        var res : manager.listResources("animation",
                        new Predicate<ResourceLocation>() {
                            @Override
                            public boolean test(ResourceLocation path) {
                                return path.toString().endsWith(".json");
                            }
                        }
                ).entrySet()) {
                    try {
                        //Use this commented code to use the filename as the resource ID instead of the name in the file.
                        //var oldKey =  res.getKey().getPath();
                        //var id = new ResourceLocation(res.getKey().getNamespace(), oldKey.substring(oldKey.lastIndexOf('/')+1, oldKey.lastIndexOf(".json")));
                        //map.put(id, AnimationSerializing.deserializeAnimation(res.getValue().open()).get(0));

                        for (var animation : AnimationSerializing.deserializeAnimation(res.getValue().open())) {
                            map.put(new ResourceLocation(res.getKey().getNamespace(), serializeComponentToString((String) animation.extraData.get("name"))), animation);
                        }
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                MinejagoPlayerAnimator.animations = map;
            }
        });
    }

    /**
     * Emotecraft emotes has a component as their name.
     * This is just a helper stuff
     * @param arg Component as json
     * @return  The String
     */
    public static String serializeComponentToString(String arg) {
        var component = Component.Serializer.fromJson(arg);
        if (component != null) {
            return component.getString();
        } else {
            return arg.replace("\"", "");
        }
    }

}
