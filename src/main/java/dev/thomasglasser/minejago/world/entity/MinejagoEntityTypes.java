package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.npc.Character;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBambooStaff;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronShuriken;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronSpear;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoEntityTypes
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Minejago.MOD_ID);

    // PROJECTILES
    public static final RegistryObject<EntityType<ThrownBambooStaff>> THROWN_BAMBOO_STAFF = ENTITY_TYPES.register("thrown_bamboo_staff", () -> EntityType.Builder.<ThrownBambooStaff>of(ThrownBambooStaff::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .setShouldReceiveVelocityUpdates(true)
            .build("thrown_bamboo_staff"));
    public static final RegistryObject<EntityType<ThrownBoneKnife>> THROWN_BONE_KNIFE = ENTITY_TYPES.register("thrown_bone_knife", () -> EntityType.Builder.<ThrownBoneKnife>of(ThrownBoneKnife::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .setShouldReceiveVelocityUpdates(true)
            .build("thrown_bone_knife"));
    public static final RegistryObject<EntityType<ThrownIronSpear>> THROWN_IRON_SPEAR = ENTITY_TYPES.register("thrown_iron_spear", () -> EntityType.Builder.<ThrownIronSpear>of(ThrownIronSpear::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .setShouldReceiveVelocityUpdates(true)
            .build("thrown_iron_spear"));
    public static final RegistryObject<EntityType<ThrownIronShuriken>> THROWN_IRON_SHURIKEN = ENTITY_TYPES.register("thrown_iron_shuriken", () -> EntityType.Builder.<ThrownIronShuriken>of(ThrownIronShuriken::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .setShouldReceiveVelocityUpdates(true)
            .build("thrown_iron_spear"));

    // MOBS
    public static final RegistryObject<EntityType<Character>> WU = ENTITY_TYPES.register("wu", () -> EntityType.Builder.of(Character::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("wu"));
    public static final RegistryObject<EntityType<Character>> KAI = ENTITY_TYPES.register("kai", () -> EntityType.Builder.of(Character::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("kai"));
    public static final RegistryObject<EntityType<Character>> NYA = ENTITY_TYPES.register("nya", () -> EntityType.Builder.of(Character::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("nya"));
    public static final RegistryObject<EntityType<UnderworldSkeleton>> UNDERWORLD_SKELETON = ENTITY_TYPES.register("underworld_skeleton", () -> EntityType.Builder.of(UnderworldSkeleton::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("underworld_skeleton"));
    public static final RegistryObject<EntityType<Kruncha>> KRUNCHA = ENTITY_TYPES.register("kruncha", () -> EntityType.Builder.of(Kruncha::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("kruncha"));
    public static final RegistryObject<EntityType<Nuckal>> NUCKAL = ENTITY_TYPES.register("nuckal", () -> EntityType.Builder.of(Nuckal::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("nuckal"));
}
