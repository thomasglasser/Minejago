package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.npc.*;
import dev.thomasglasser.minejago.world.entity.npc.Character;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBambooStaff;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronShuriken;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronSpear;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.Map;

public class MinejagoEntityTypes
{
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPES = RegistrationProvider.get(Registries.ENTITY_TYPE, Minejago.MOD_ID);

    // PROJECTILES
    public static final RegistryObject<EntityType<ThrownBambooStaff>> THROWN_BAMBOO_STAFF = ENTITY_TYPES.register("thrown_bamboo_staff", () -> EntityType.Builder.<ThrownBambooStaff>of(ThrownBambooStaff::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .build("thrown_bamboo_staff"));
    public static final RegistryObject<EntityType<ThrownBoneKnife>> THROWN_BONE_KNIFE = ENTITY_TYPES.register("thrown_bone_knife", () -> EntityType.Builder.<ThrownBoneKnife>of(ThrownBoneKnife::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .build("thrown_bone_knife"));
    public static final RegistryObject<EntityType<ThrownIronSpear>> THROWN_IRON_SPEAR = ENTITY_TYPES.register("thrown_iron_spear", () -> EntityType.Builder.<ThrownIronSpear>of(ThrownIronSpear::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .build("thrown_iron_spear"));
    public static final RegistryObject<EntityType<ThrownIronShuriken>> THROWN_IRON_SHURIKEN = ENTITY_TYPES.register("thrown_iron_shuriken", () -> EntityType.Builder.<ThrownIronShuriken>of(ThrownIronShuriken::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .build("thrown_iron_spear"));

    // MOBS
    public static final RegistryObject<EntityType<Wu>> WU = ENTITY_TYPES.register("wu", () -> EntityType.Builder.of(Wu::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("wu"));
    public static final RegistryObject<EntityType<Kai>> KAI = ENTITY_TYPES.register("kai", () -> EntityType.Builder.of(Kai::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("kai"));
    public static final RegistryObject<EntityType<Character>> NYA = ENTITY_TYPES.register("nya", () -> EntityType.Builder.of(Character::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("nya"));
    public static final RegistryObject<EntityType<Cole>> COLE = ENTITY_TYPES.register("cole", () -> EntityType.Builder.of(Cole::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("cole"));
    public static final RegistryObject<EntityType<Jay>> JAY = ENTITY_TYPES.register("jay", () -> EntityType.Builder.of(Jay::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("jay"));
    public static final RegistryObject<EntityType<Zane>> ZANE = ENTITY_TYPES.register("zane", () -> EntityType.Builder.of(Zane::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .build("zane"));
    public static final RegistryObject<EntityType<Skulkin>> SKULKIN = ENTITY_TYPES.register("skulkin", () -> EntityType.Builder.of(Skulkin::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .build("skulkin"));
    public static final RegistryObject<EntityType<Kruncha>> KRUNCHA = ENTITY_TYPES.register("kruncha", () -> EntityType.Builder.of(Kruncha::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .build("kruncha"));
    public static final RegistryObject<EntityType<Nuckal>> NUCKAL = ENTITY_TYPES.register("nuckal", () -> EntityType.Builder.of(Nuckal::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f)
            .build("nuckal"));
    public static final RegistryObject<EntityType<SkulkinHorse>> SKULKIN_HORSE = ENTITY_TYPES.register("skulkin_horse", () -> EntityType.Builder.of(SkulkinHorse::new, MobCategory.CREATURE)
            .sized(1.3964844f, 1.6f)
            .build("skulkin_horse"));

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAllAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> map = new HashMap<>();

        map.put(WU.get(), Wu.createAttributes().build());
        map.put(KAI.get(), Kai.createAttributes().build());
        map.put(NYA.get(), Character.createAttributes().build());
        map.put(COLE.get(), Cole.createAttributes().build());
        map.put(JAY.get(), Jay.createAttributes().build());
        map.put(ZANE.get(), Zane.createAttributes().build());
        map.put(SKULKIN.get(), Skulkin.createAttributes().build());
        map.put(KRUNCHA.get(), Kruncha.createAttributes().build());
        map.put(NUCKAL.get(), Nuckal.createAttributes().build());
        map.put(SKULKIN_HORSE.get(), SkulkinHorse.createAttributes().build());

        return map;
    }

    public static void init() {}
}
