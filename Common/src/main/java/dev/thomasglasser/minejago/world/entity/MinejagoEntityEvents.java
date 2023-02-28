package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPacket;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPacket;
import dev.thomasglasser.minejago.network.ServerboundStartSpinjitzuPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorations;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.mehvahdjukaar.moonlight.api.map.MapHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.Set;

public class MinejagoEntityEvents
{
    public static void onPlayerTick(Player player)
    {
        SpinjitzuData spinjitzu = Services.DATA.getSpinjitzuData(player);

        if (player instanceof ServerPlayer serverPlayer)
        {
            if (spinjitzu.unlocked()) {
                if (spinjitzu.active()) {
                    if (serverPlayer.isCrouching() || serverPlayer.getVehicle() != null || serverPlayer.isVisuallySwimming() || serverPlayer.isUnderWater()) {
                        stopSpinjitzu(spinjitzu, serverPlayer);
                        return;
                    }
                    if (player.tickCount % 20 == 0)
                    {
                        serverPlayer.level.playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundSource.PLAYERS);
                    }
                    Power power = player.level.registryAccess().registryOrThrow(MinejagoRegistries.POWER).getHolderOrThrow(Services.DATA.getPowerData(player).power()).value();
                    if (!power.is(MinejagoPowers.NONE)) {
                        MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, power.getMainSpinjitzuColor(), power.getAltSpinjitzuColor(), 10.5, false);
                        if (power.getBorderParticle() != null)
                            MinejagoParticleUtils.renderPlayerSpinjitzuBorder(power.getBorderParticle(), serverPlayer, 4, false);
                    } else if (serverPlayer.getTeam() != null) {
                        switch (serverPlayer.getTeam().getColor()) {
                            case RED -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_RED, SpinjitzuParticleOptions.TEAM_RED, 10.5, false);
                            }
                            case AQUA -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_AQUA, SpinjitzuParticleOptions.TEAM_AQUA, 10.5, false);
                            }
                            case BLUE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_BLUE, SpinjitzuParticleOptions.TEAM_BLUE, 10.5, false);
                            }
                            case GOLD -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GOLD, SpinjitzuParticleOptions.TEAM_GOLD, 10.5, false);
                            }
                            case GRAY -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GRAY, SpinjitzuParticleOptions.TEAM_GRAY, 10.5, false);
                            }
                            case BLACK -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_BLACK, SpinjitzuParticleOptions.TEAM_BLACK, 10.5, false);
                            }
                            case GREEN -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GREEN, SpinjitzuParticleOptions.TEAM_GREEN, 10.5, false);
                            }
                            case WHITE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_WHITE, SpinjitzuParticleOptions.TEAM_WHITE, 10.5, false);
                            }
                            case YELLOW -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_YELLOW, SpinjitzuParticleOptions.TEAM_YELLOW, 10.5, false);
                            }
                            case DARK_RED -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_RED, SpinjitzuParticleOptions.TEAM_DARK_RED, 10.5, false);
                            }
                            case DARK_AQUA -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_AQUA, SpinjitzuParticleOptions.TEAM_DARK_AQUA, 10.5, false);
                            }
                            case DARK_BLUE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_BLUE, SpinjitzuParticleOptions.TEAM_DARK_BLUE, 10.5, false);
                            }
                            case DARK_GRAY -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_GRAY, SpinjitzuParticleOptions.TEAM_DARK_GRAY, 10.5, false);
                            }
                            case DARK_GREEN -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_GREEN, SpinjitzuParticleOptions.TEAM_DARK_GREEN, 10.5, false);
                            }
                            case DARK_PURPLE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, 10.5, false);
                            }
                            case LIGHT_PURPLE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, 10.5, false);
                            }
                            default -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, 10.5, false);
                            }
                        }
                    } else {
                        MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, 10.5, false);
                    }
                }
            } else if (spinjitzu.active()) {
                stopSpinjitzu(spinjitzu, serverPlayer);
            }
        } else if (!spinjitzu.active() && MinejagoKeyMappings.ACTIVATE_SPINJITZU.isDown()) {
            Services.NETWORK.sendToServer(ServerboundStartSpinjitzuPacket.class);
        }
    }

    public static void onServerPlayerLoggedIn(Player player)
    {
        for (ServerPlayer serverPlayer : ((ServerLevel) player.getLevel()).getPlayers(serverPlayer -> true))
        {
            Services.NETWORK.sendToAllClients(ClientboundRefreshVipDataPacket.class, serverPlayer);
        }
    }

    public static void onLivingAttack(DamageSource source)
    {
        if (source.getEntity() instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().is(MinejagoItems.WOODEN_NUNCHUCKS.get()))
            WoodenNunchucksItem.resetDamage(livingEntity.getMainHandItem());
    }

    public static void onLivingTick(LivingEntity entity)
    {
        if (entity instanceof Player player)
        {
            Inventory i = player.getInventory();

            if (i.contains(MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance()) /*&& i.contains(MinejagoElements.SHURIKENS_OF_ICE.get().getDefaultInstance()) && i.contains(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get().getDefaultInstance()) && i.contains(MinejagoElements.SWORD_OF_FIRE.get().getDefaultInstance())*/)
                GoldenWeaponItem.overload(entity);
        }
        else if (entity instanceof InventoryCarrier carrier)
        {
            boolean f = false, e = false, i = false, l = false;

            for (ItemStack stack : entity.getAllSlots())
            {
                if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get())
                {
                    e = true;
                }
                /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                {
                    f = true;
                }
                else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                {
                    l = true;
                }
                else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                {
                    i = true;
                }*/
            }

            SimpleContainer inventory = carrier.getInventory();

            if ((inventory.hasAnyOf(Set.of(MinejagoItems.SCYTHE_OF_QUAKES.get())) || e) /*&& (inventory.hasAnyOf(Set.of(MinejagoElements.SWORD_OF_FIRE.get())) || f) && (inventory.hasAnyOf(Set.of(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())) || l) && (inventory.hasAnyOf(Set.of(MinejagoElements.SHURIKENS_OF_ICE.get())) || i)*/)
                GoldenWeaponItem.overload(entity);
        }
        else {
            boolean f = false, e = false, i = false, l = false;

            for (ItemStack stack : entity.getAllSlots())
            {
                if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get())
                {
                    e = true;
                }
                /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                {
                    f = true;
                }
                else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                {
                    l = true;
                }
                else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                {
                    i = true;
                }*/
            }

            if (f && e && i && l)
                GoldenWeaponItem.overload(entity);
        }
    }

    public static void onPlayerEntityInteract(Player player, Level world, InteractionHand hand, Entity entity)
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

    private static void stopSpinjitzu(SpinjitzuData spinjitzu, ServerPlayer serverPlayer)
    {
        Services.DATA.setSpinjitzuData(new SpinjitzuData(spinjitzu.unlocked(), false), serverPlayer);
        Services.NETWORK.sendToAllClients(ClientboundStopAnimationPacket.class, ClientboundStopAnimationPacket.toBytes(serverPlayer.getUUID()), serverPlayer);
        AttributeInstance speed = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.hasModifier(SpinjitzuData.SPEED_MODIFIER)) speed.removeModifier(SpinjitzuData.SPEED_MODIFIER);
        AttributeInstance kb = serverPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (kb != null && kb.hasModifier(SpinjitzuData.KNOCKBACK_MODIFIER)) kb.removeModifier(SpinjitzuData.KNOCKBACK_MODIFIER);
        serverPlayer.level.playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_STOP.get(), SoundSource.PLAYERS);
    }
}
