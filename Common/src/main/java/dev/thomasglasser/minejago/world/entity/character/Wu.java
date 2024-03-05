package dev.thomasglasser.minejago.world.entity.character;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundOpenPowerSelectionScreenPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.ai.behavior.GivePowerAndGi;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Wu extends Character
{
    public static final String POWER_GIVEN_KEY = "gui.power_selection.power_given";
    public static final String NO_POWER_GIVEN_KEY = "gui.power_selection.no_power_given";
    protected List<ResourceKey<Power>> powersToGive = new ArrayList<>();
    protected boolean givingPower;

    public Wu(EntityType<? extends Wu> entityType, Level level) {
        super(entityType, level);
        Services.DATA.setPowerData(new PowerData(MinejagoPowers.CREATION, true), this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.MAX_HEALTH, ((RangedAttribute)Attributes.MAX_HEALTH).getMaxValue())
                .add(Attributes.ATTACK_KNOCKBACK, ((RangedAttribute)Attributes.ATTACK_KNOCKBACK).getMaxValue())
                .add(Attributes.ATTACK_DAMAGE, 20);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BAMBOO_STAFF.get().getDefaultInstance());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        SpawnGroupData data = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        populateDefaultEquipmentSlots(random, pDifficulty);
        setCanPickUpLoot(false);
        return data;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.FILLED_MAP) && itemStack.getOrCreateTag().getBoolean("IsFourWeaponsMap")))
        {
            Registry<Power> registry = level().registryAccess().registryOrThrow(MinejagoRegistries.POWER);

            if (!MinejagoServerConfig.drainPool || (powersToGive.size() <= 1)) {
                powersToGive = new ArrayList<>(registry.registryKeySet());
                if (!MinejagoServerConfig.enableNoPower)
                    removePowersToGive(MinejagoPowers.NONE);
                powersToGive.removeIf(key -> registry.get(key) != null && registry.get(key).isSpecial());
            }

            if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND) {
                givingPower = !Services.DATA.getPowerData(serverPlayer).given() || MinejagoServerConfig.allowChange;
                if (givingPower) {
                    if (MinejagoServerConfig.allowChoose) {
                        TommyLibServices.NETWORK.sendToClient(ClientboundOpenPowerSelectionScreenPacket.class, ClientboundOpenPowerSelectionScreenPacket.write(powersToGive, this.getId()), serverPlayer);
                    } else if (this.distanceTo(serverPlayer) > 1.0f) {
                        ResourceKey<Power> oldPower = Services.DATA.getPowerData(serverPlayer).power();
                        if (Services.DATA.getPowerData(serverPlayer).given() && oldPower != MinejagoPowers.NONE && MinejagoServerConfig.drainPool)
                            addPowersToGive(oldPower);
                        ResourceKey<Power> newPower = powersToGive.get(random.nextInt(powersToGive.size()));
                        if (newPower != MinejagoPowers.NONE) removePowersToGive(newPower);
                        if (newPower == MinejagoPowers.NONE) {
                            Services.DATA.setPowerData(new PowerData(newPower, true), serverPlayer);
                            serverPlayer.displayClientMessage(Component.translatable(Wu.NO_POWER_GIVEN_KEY), true);
                            givingPower = false;
                        } else {
                            BrainUtils.setMemory(this, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
                            BrainUtils.setMemory(this, MinejagoMemoryModuleTypes.SELECTED_POWER.get(), newPower);
                        }
                    }
                }
            }
        }
        else if (!player.getInventory().hasAnyOf(Set.copyOf(MinejagoArmors.BLACK_GI_SET.getAllAsItems())))
        {
            MinejagoArmors.BLACK_GI_SET.getAllAsItems().forEach(armorItem ->
                    player.addItem(armorItem.getDefaultInstance()));
        }

        return super.mobInteract(player, hand);
    }

    @SafeVarargs
    public final List<ResourceKey<Power>> addPowersToGive(ResourceKey<Power>... keys)
    {
        Arrays.stream(keys).forEach(key ->
        {
            if (!powersToGive.contains(key)) powersToGive.add(key);
        });

        return powersToGive;
    }

    @SafeVarargs
    public final List<ResourceKey<Power>> removePowersToGive(ResourceKey<Power>... keys)
    {
        Arrays.stream(keys).forEach(key ->
        {
            while (powersToGive.contains(key)) powersToGive.remove(key);
        });

        return powersToGive;
    }

    @Override
    public BrainActivityGroup<Character> getIdleTasks() {
        return super.getIdleTasks().behaviours(
                new FirstApplicableBehaviour<Character>(
                        Pair.of(new GivePowerAndGi<Character>()
                                .startCondition(character -> character instanceof Wu wu && wu.givingPower)
                                .whenStarting(character -> character.setDoingSpinjitzu(true))
                                .whenStopping(character -> character.setDoingSpinjitzu(false)), 0)
        ));
    }
}
