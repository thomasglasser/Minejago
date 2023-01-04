package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.IDataHolder;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorations;
import net.mehvahdjukaar.moonlight.api.map.MapHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class MinejagoPaintingVariants
{
    public static final RegistrationProvider<PaintingVariant> PAINTING_VARIANTS = RegistrationProvider.get(Registries.PAINTING_VARIANT, Minejago.MOD_ID);

    public static final RegistryObject<PaintingVariant> FOUR_WEAPONS = PAINTING_VARIANTS.register("four_weapons", () -> new PaintingVariant(32, 16));

    public static void onInteract(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult)
    {
        if (world instanceof ServerLevel serverLevel && hand == InteractionHand.MAIN_HAND && entity instanceof Painting painting && painting.getVariant().is(new ResourceLocation(Minejago.MOD_ID, "four_weapons")) && !((IDataHolder)painting).getPersistentData().getBoolean("MapTaken"))
        {
            ItemStack itemstack = MapItem.create(world, (int)entity.getX(), (int)entity.getZ(), (byte)4, true, true);
            MapItem.renderBiomePreviewMap((ServerLevel) world, itemstack);
            /* TODO: Find lightning */ BlockPos pos1 = serverLevel.findNearestMapStructure(StructureTags.VILLAGE, player.getOnPos(), Integer.MAX_VALUE, false);
            /* TODO: Find fire */ BlockPos pos2 = serverLevel.findNearestMapStructure(StructureTags.RUINED_PORTAL, player.getOnPos(), Integer.MAX_VALUE, false);
            /* TODO: Find earth */ BlockPos pos3 = serverLevel.findNearestMapStructure(StructureTags.SHIPWRECK, player.getOnPos(), Integer.MAX_VALUE, false);
            /* TODO: Find ice */ BlockPos pos4 = serverLevel.findNearestMapStructure(StructureTags.MINESHAFT, player.getOnPos(), Integer.MAX_VALUE, false);
            if (Services.PLATFORM.isModLoaded(Minejago.Dependencies.MOONLIGHT_LIB.getModId()))
            {
                MapHelper.addDecorationToMap(itemstack, pos1, MinejagoMapDecorations.NUNCHUCKS, -1);
                MapHelper.addDecorationToMap(itemstack, pos2, MinejagoMapDecorations.SWORD, -1);
                MapHelper.addDecorationToMap(itemstack, pos3, MinejagoMapDecorations.SCYTHE, -1);
                MapHelper.addDecorationToMap(itemstack, pos4, MinejagoMapDecorations.SHURIKENS, -1);
            }
            else
            {
                MapItemSavedData.addTargetDecoration(itemstack, pos1, "lightning", MapDecoration.Type.BANNER_BLUE);
                MapItemSavedData.addTargetDecoration(itemstack, pos2, "fire", MapDecoration.Type.BANNER_RED);
                MapItemSavedData.addTargetDecoration(itemstack, pos3, "earth", MapDecoration.Type.BANNER_BROWN);
                MapItemSavedData.addTargetDecoration(itemstack, pos4, "ice", MapDecoration.Type.BANNER_WHITE);
            }
            itemstack.setHoverName(Component.translatable(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons"));
            player.addItem(itemstack);
            ((IDataHolder)painting).getPersistentData().putBoolean("MapTaken", true);
        }
    }

    public static void init() {}
}
