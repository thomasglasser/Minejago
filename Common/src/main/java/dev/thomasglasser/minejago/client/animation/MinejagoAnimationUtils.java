package dev.thomasglasser.minejago.client.animation;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class MinejagoAnimationUtils
{
	public static void startAnimation(KeyframeAnimation startAnim, @Nullable KeyframeAnimation goAnim, Player player, FirstPersonMode firstPersonMode)
	{
		var animation = MinejagoPlayerAnimator.animationData.get(player);
		//Get the animation for that player
		if (animation != null) {
			//You can set an animation from anywhere ON THE CLIENT
			//Do not attempt to do this on a server, that will only fail
			animation.setAnimation(new KeyframeAnimationPlayer(startAnim).setFirstPersonMode(firstPersonMode));
			if (goAnim != null)
				animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(20, Ease.CONSTANT), new KeyframeAnimationPlayer(goAnim).setFirstPersonMode(firstPersonMode));
		}

	}

	public static void startAnimation(KeyframeAnimation anim, Player player, FirstPersonMode firstPersonMode)
	{
		startAnimation(anim, null, player, firstPersonMode);
	}

	public static void stopAnimation(AbstractClientPlayer player)
	{
		var animation = MinejagoPlayerAnimator.animationData.get(player);
		animation.setAnimation(null);
	}
}
