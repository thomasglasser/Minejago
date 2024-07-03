package dev.thomasglasser.minejago.plugins;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.component.DragonComponentProvider;
import dev.thomasglasser.minejago.world.entity.component.LivingEntityComponentProvider;
import dev.thomasglasser.minejago.world.entity.component.PaintingComponentProvider;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.component.TeapotBlockComponentProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.Painting;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class MinejagoWailaPlugin implements IWailaPlugin {
    public static final ResourceLocation TEAPOT_BLOCK = Minejago.modLoc("teapot_block");
    public static final ResourceLocation LIVING_ENTITY = Minejago.modLoc("living_entity");
    public static final ResourceLocation DRAGON = Minejago.modLoc("dragon");
    public static final ResourceLocation PAINTING = Minejago.modLoc("painting");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TeapotBlockComponentProvider.INSTANCE, TeapotBlock.class);
        registration.registerEntityComponent(LivingEntityComponentProvider.INSTANCE, LivingEntity.class);
        registration.registerEntityComponent(DragonComponentProvider.INSTANCE, Dragon.class);
        registration.registerEntityComponent(PaintingComponentProvider.INSTANCE, Painting.class);
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(PaintingComponentProvider.INSTANCE, Painting.class);
        registration.registerEntityDataProvider(DragonComponentProvider.INSTANCE, Dragon.class);
    }
}
