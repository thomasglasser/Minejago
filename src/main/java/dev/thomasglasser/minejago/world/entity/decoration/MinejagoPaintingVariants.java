package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.MinejagoMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoPaintingVariants
{
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, MinejagoMod.MODID);

    public static final RegistryObject<PaintingVariant> FOUR_WEAPONS = PAINTING_VARIANTS.register("four_weapons", () -> new PaintingVariant(32, 16));

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (!event.getLevel().isClientSide() && event.getHand() == InteractionHand.MAIN_HAND && event.getTarget() instanceof Painting painting && painting.getVariant().is(new ResourceLocation(MinejagoMod.MODID, "four_weapons")))
        {
            ItemStack itemstack = MapItem.create(event.getEntity().level, (int)event.getEntity().getX(), (int)event.getEntity().getZ(), (byte)2, true, true);
            MapItem.renderBiomePreviewMap((ServerLevel) event.getEntity().level, itemstack);
            MapItemSavedData.addTargetDecoration(itemstack, new BlockPos(event.getEntity().getX() + Math.random() * 100, event.getEntity().getY(), event.getEntity().getZ() + Math.random() * 100), "fire", MapDecoration.Type.BANNER_RED);
            MapItemSavedData.addTargetDecoration(itemstack, new BlockPos(event.getEntity().getX() - Math.random() * 100, event.getEntity().getY(), event.getEntity().getZ() - Math.random() * 100), "ice", MapDecoration.Type.BANNER_WHITE);
            MapItemSavedData.addTargetDecoration(itemstack, new BlockPos(event.getEntity().getX() + Math.random() * 100, event.getEntity().getY(), event.getEntity().getZ() + Math.random() * 100), "lightning", MapDecoration.Type.BANNER_BLUE);
            MapItemSavedData.addTargetDecoration(itemstack, new BlockPos(event.getEntity().getX() - Math.random() * 100, event.getEntity().getY(), event.getEntity().getZ() - Math.random() * 100), "earth", MapDecoration.Type.BANNER_BROWN);
            // NEEDS MOONLIGHT LIB FOR CUSTOM MapHelper.addDecorationToMap(itemstack, new BlockPos(event.getEntity().getX() - Math.random() * 1000, event.getEntity().getY(), event.getEntity().getZ() - Math.random() * 1000), ModMapDeco.IGLOO_TYPE, -1);
            itemstack.setHoverName(Component.translatable(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons"));
            event.getEntity().addItem(itemstack);
        }
    }
}
