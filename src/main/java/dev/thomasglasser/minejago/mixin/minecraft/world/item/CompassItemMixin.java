package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.MinejagoLevels;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CompassItem.class)
public class CompassItemMixin {
    @Unique
    private static final GlobalPos UNDERWORLD_CENTER = new GlobalPos(MinejagoLevels.UNDERWORLD, BlockPos.ZERO);

    @ModifyReturnValue(method = "getSpawnPosition", at = @At(value = "RETURN"))
    private static GlobalPos getSpawnPosition(GlobalPos original, Level level) {
        if (level.dimension().equals(MinejagoLevels.UNDERWORLD))
            return UNDERWORLD_CENTER;
        return original;
    }
}
