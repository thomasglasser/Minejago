package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.*;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.entity.dragon.EarthDragon;
import dev.thomasglasser.minejago.world.entity.projectile.*;
import dev.thomasglasser.minejago.world.entity.skulkin.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

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
    public static final RegistryObject<EntityType<EarthBlast>> EARTH_BLAST = ENTITY_TYPES.register("earth_blast", () -> EntityType.Builder.of(((EntityType<EarthBlast> entityType, Level level) -> new EarthBlast(entityType, level)), MobCategory.MISC)
            .sized(1.0F, 1.0F)
            .build("earth_blast"));

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
    public static final RegistryObject<EntityType<EarthDragon>> EARTH_DRAGON = ENTITY_TYPES.register("earth_dragon", () -> EntityType.Builder.of(EarthDragon::new, MobCategory.CREATURE)
            .sized(4.8125f, 3.00f)
            .build("earth_dragon"));
    public static final RegistryObject<EntityType<Samukai>> SAMUKAI = ENTITY_TYPES.register("samukai", () -> EntityType.Builder.of(Samukai::new, MobCategory.MONSTER)
            .sized(0.875f, 2.375f)
            .build("samukai"));
    public static final RegistryObject<EntityType<SkullTruck>> SKULL_TRUCK = ENTITY_TYPES.register("skull_truck", () -> EntityType.Builder.of(SkullTruck::new, MobCategory.MISC)
            .sized(3.5f, 3.1875f)
            .build("skull_truck"));

    public static final RegistryObject<EntityType<AbstractSkulkinVehicle>> SKULL_MOTORBIKE = ENTITY_TYPES.register("skull_motorbike", () -> EntityType.Builder.of(AbstractSkulkinVehicle::new, MobCategory.MISC)
            .sized(1.375f, 1.5f)
            .build("skull_motorbike"));

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
        map.put(EARTH_DRAGON.get(), Dragon.createAttributes().build());
        map.put(SAMUKAI.get(), Samukai.createAttributes().build());
        map.put(SKULL_TRUCK.get(), SkullTruck.createAttributes().build());
        map.put(SKULL_MOTORBIKE.get(), AbstractSkulkinVehicle.createAttributes().build());

        return map;
    }

    public static void init() {}
}
