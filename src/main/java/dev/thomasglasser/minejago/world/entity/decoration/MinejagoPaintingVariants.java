package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorations;
import net.mehvahdjukaar.moonlight.api.map.MapHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoPaintingVariants
{
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Minejago.MOD_ID);

    public static final RegistryObject<PaintingVariant> FOUR_WEAPONS = PAINTING_VARIANTS.register("four_weapons", () -> new PaintingVariant(32, 16));

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getLevel() instanceof ServerLevel serverLevel && event.getHand() == InteractionHand.MAIN_HAND && event.getTarget() instanceof Painting painting && painting.getVariant().is(new ResourceLocation(Minejago.MOD_ID, "four_weapons")))
        {
            ItemStack itemstack = MapItem.create(event.getEntity().level, (int)event.getEntity().getX(), (int)event.getEntity().getZ(), (byte)4, true, true);
            MapItem.renderBiomePreviewMap((ServerLevel) event.getEntity().level, itemstack);
            /* TODO: Find lightning */ BlockPos pos1 = serverLevel.findNearestMapStructure(StructureTags.VILLAGE, event.getPos(), Integer.MAX_VALUE, false);
            MapHelper.addDecorationToMap(itemstack, pos1, MinejagoMapDecorations.NUNCHUCKS, -1);
            /* TODO: Find fire */ BlockPos pos2 = serverLevel.findNearestMapStructure(StructureTags.RUINED_PORTAL, event.getPos(), Integer.MAX_VALUE, false);
            MapHelper.addDecorationToMap(itemstack, pos2, MinejagoMapDecorations.SWORD, -1);
            /* TODO: Find earth */ BlockPos pos3 = serverLevel.findNearestMapStructure(StructureTags.SHIPWRECK, event.getPos(), Integer.MAX_VALUE, false);
            MapHelper.addDecorationToMap(itemstack, pos3, MinejagoMapDecorations.SCYTHE, -1);
            /* TODO: Find ice */ BlockPos pos4 = serverLevel.findNearestMapStructure(StructureTags.MINESHAFT, event.getPos(), Integer.MAX_VALUE, false);
            MapHelper.addDecorationToMap(itemstack, pos4, MinejagoMapDecorations.SHURIKENS, -1);
            itemstack.setHoverName(Component.translatable(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons"));
            event.getEntity().addItem(itemstack);
        }
    }
}
