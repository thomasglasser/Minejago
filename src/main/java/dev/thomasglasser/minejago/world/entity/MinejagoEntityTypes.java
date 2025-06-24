package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.Cole;
import dev.thomasglasser.minejago.world.entity.character.Jay;
import dev.thomasglasser.minejago.world.entity.character.Kai;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.entity.dragon.EarthDragon;
import dev.thomasglasser.minejago.world.entity.projectile.EarthBlast;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownShurikenOfIce;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowClone;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowSource;
import dev.thomasglasser.minejago.world.entity.skulkin.AbstractSkulkinVehicle;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullMotorbike;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.minejago.world.entity.skulkin.Spykor;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.BouncingPoleSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.CenterSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.RockingPoleSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningAxesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningDummiesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningMacesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningPoleSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SwirlingKnivesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.entity.projectile.ThrownSword;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class MinejagoEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Minejago.MOD_ID);

    // Projectiles
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownShurikenOfIce>> THROWN_SHURIKEN_OF_ICE = register("thrown_shuriken_of_ice", EntityType.Builder.<ThrownShurikenOfIce>of(ThrownShurikenOfIce::new, MobCategory.MISC)
            .sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownSword>> THROWN_BAMBOO_STAFF = register("thrown_bamboo_staff", EntityType.Builder.<ThrownSword>of((entityType, level) -> new ThrownSword(entityType, level) {
        @Override
        protected ItemStack getDefaultPickupItem() {
            return MinejagoItems.BAMBOO_STAFF.toStack();
        }
    }, MobCategory.MISC)
            .sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrownBoneKnife>> THROWN_BONE_KNIFE = register("thrown_bone_knife", EntityType.Builder.<ThrownBoneKnife>of(ThrownBoneKnife::new, MobCategory.MISC)
            .sized(0.5F, 0.5F));
    public static final DeferredHolder<EntityType<?>, EntityType<EarthBlast>> EARTH_BLAST = register("earth_blast", EntityType.Builder.<EarthBlast>of(EarthBlast::new, MobCategory.MISC)
            .sized(1.0F, 1.0F));

    // Characters
    public static final DeferredHolder<EntityType<?>, EntityType<Wu>> WU = register("wu", EntityType.Builder.of(Wu::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Kai>> KAI = register("kai", EntityType.Builder.of(Kai::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Character>> NYA = register("nya", EntityType.Builder.of(Character::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Cole>> COLE = register("cole", EntityType.Builder.of(Cole::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Jay>> JAY = register("jay", EntityType.Builder.of(Jay::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Zane>> ZANE = register("zane", EntityType.Builder.of(Zane::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f));

    // Skulkin
    public static final DeferredHolder<EntityType<?>, EntityType<Skulkin>> SKULKIN = register("skulkin", EntityType.Builder.of(Skulkin::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Kruncha>> KRUNCHA = register("kruncha", EntityType.Builder.of(Kruncha::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<Nuckal>> NUCKAL = register("nuckal", EntityType.Builder.of(Nuckal::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f));
    public static final DeferredHolder<EntityType<?>, EntityType<SkulkinHorse>> SKULKIN_HORSE = register("skulkin_horse", EntityType.Builder.of(SkulkinHorse::new, MobCategory.MONSTER)
            .sized(1.3964844f, 1.6f));
    public static final DeferredHolder<EntityType<?>, EntityType<Samukai>> SAMUKAI = register("samukai", EntityType.Builder.of(Samukai::new, MobCategory.MONSTER)
            .sized(0.875f, 2.375f));
    public static final DeferredHolder<EntityType<?>, EntityType<SkullTruck>> SKULL_TRUCK = register("skull_truck", EntityType.Builder.of(SkullTruck::new, MobCategory.MONSTER)
            .sized(3.5f, 3.1875f)
            .passengerAttachments(
                    new Vec3(0.0, 1.2625, 0.0),
                    new Vec3(0.3875, 1.2625, -0.725),
                    new Vec3(-0.3875, 1.2625, -0.725)));
    public static final DeferredHolder<EntityType<?>, EntityType<SkullMotorbike>> SKULL_MOTORBIKE = register("skull_motorbike", EntityType.Builder.of(SkullMotorbike::new, MobCategory.MONSTER)
            .sized(1.375f, 1.5f)
            .passengerAttachments(
                    new Vec3(0.0, 0.45, -0.3)));
    public static final DeferredHolder<EntityType<?>, EntityType<Spykor>> SPYKOR = register("spykor", EntityType.Builder.of(Spykor::new, MobCategory.MONSTER)
            .sized(2F, 1.4375F)
            .passengerAttachments(0.765F));

    // Dragons
    public static final DeferredHolder<EntityType<?>, EntityType<EarthDragon>> EARTH_DRAGON = register("earth_dragon", EntityType.Builder.of(EarthDragon::new, MobCategory.CREATURE)
            .sized(4.8125f, 3.00f)
            .passengerAttachments(
                    new Vec3(0.0, 2.8275, 1.65),
                    new Vec3(0.0, 2.8275, 0.9)));

    // Spinjitzu Course Elements
    public static final DeferredHolder<EntityType<?>, EntityType<CenterSpinjitzuCourseElement>> CENTER_SPINJITZU_COURSE_ELEMENT = register("center_spinjitzu_course_element", EntityType.Builder.of(CenterSpinjitzuCourseElement::new, MobCategory.MISC));
    public static final DeferredHolder<EntityType<?>, EntityType<BouncingPoleSpinjitzuCourseElement>> BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT = register("bouncing_pole_spinjitzu_course_element", EntityType.Builder.of(BouncingPoleSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(0.75f, 0.375f));
    public static final DeferredHolder<EntityType<?>, EntityType<RockingPoleSpinjitzuCourseElement>> ROCKING_POLE_SPINJITZU_COURSE_ELEMENT = register("rocking_pole_spinjitzu_course_element", EntityType.Builder.of(RockingPoleSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(0.75f, 0.375f));
    public static final DeferredHolder<EntityType<?>, EntityType<SpinningPoleSpinjitzuCourseElement>> SPINNING_POLE_SPINJITZU_COURSE_ELEMENT = register("spinning_pole_spinjitzu_course_element", EntityType.Builder.of(SpinningPoleSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(0.625f, 2.0625f));
    public static final DeferredHolder<EntityType<?>, EntityType<SpinningMacesSpinjitzuCourseElement>> SPINNING_MACES_SPINJITZU_COURSE_ELEMENT = register("spinning_maces_spinjitzu_course_element", EntityType.Builder.of(SpinningMacesSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(1f, 4.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<SpinningDummiesSpinjitzuCourseElement>> SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT = register("spinning_dummies_spinjitzu_course_element", EntityType.Builder.of(SpinningDummiesSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(1f, 4.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<SwirlingKnivesSpinjitzuCourseElement>> SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT = register("swirling_knives_spinjitzu_course_element", EntityType.Builder.of(SwirlingKnivesSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(1f, 4.25f));
    public static final DeferredHolder<EntityType<?>, EntityType<SpinningAxesSpinjitzuCourseElement>> SPINNING_AXES_SPINJITZU_COURSE_ELEMENT = register("spinning_axes_spinjitzu_course_element", EntityType.Builder.of(SpinningAxesSpinjitzuCourseElement::new, MobCategory.MISC)
            .sized(1f, 4.25f));

    // Golden Weapon Holders
    public static final DeferredHolder<EntityType<?>, EntityType<GoldenWeaponHolder>> EARTH_DRAGON_HEAD = register("earth_dragon_head", EntityType.Builder.<GoldenWeaponHolder>of(((entityType, level) -> new GoldenWeaponHolder(entityType, level, MinejagoItems.EARTH_DRAGON_HEAD::toStack, EARTH_DRAGON, MinejagoSoundEvents.EARTH_DRAGON_AWAKEN, MinejagoItems.SCYTHE_OF_QUAKES::toStack)), MobCategory.MISC)
            .sized(1.75f, 2.375f));

    // Shadows
    public static final DeferredHolder<EntityType<?>, EntityType<ShadowSource>> SHADOW_SOURCE = register("shadow_source", EntityType.Builder.<ShadowSource>of(ShadowSource::new, MobCategory.MISC)
            .noSave()
            .noSummon()
            .sized(0.6F, 1.8F)
            .eyeHeight(1.62F));
    public static final DeferredHolder<EntityType<?>, EntityType<ShadowClone>> SHADOW_CLONE = register("shadow_clone", EntityType.Builder.<ShadowClone>of(ShadowClone::new, MobCategory.MISC)
            .noSave()
            .noSummon()
            .sized(0.6F, 1.8F)
            .eyeHeight(1.62F));

    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(name));
    }

    public static Map<EntityType<? extends LivingEntity>, AttributeSupplier> getAllAttributes() {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> map = new HashMap<>();

        map.put(WU.get(), Wu.createAttributes().build());
        map.put(KAI.get(), Kai.createAttributes().build());
        map.put(NYA.get(), Character.createAttributes().build());
        map.put(COLE.get(), Cole.createAttributes().build());
        map.put(JAY.get(), Jay.createAttributes().build());
        map.put(ZANE.get(), Zane.createAttributes().build());
        map.put(SKULKIN.get(), AbstractSkeleton.createAttributes().build());
        map.put(KRUNCHA.get(), AbstractSkeleton.createAttributes().build());
        map.put(NUCKAL.get(), AbstractSkeleton.createAttributes().build());
        map.put(SKULKIN_HORSE.get(), SkulkinHorse.createAttributes().build());
        map.put(EARTH_DRAGON.get(), Dragon.createAttributes().build());
        map.put(SAMUKAI.get(), Samukai.createAttributes().build());
        map.put(SKULL_TRUCK.get(), SkullTruck.createAttributes().build());
        map.put(SKULL_MOTORBIKE.get(), AbstractSkulkinVehicle.createAttributes().build());
        map.put(SPYKOR.get(), Spykor.createAttributes().build());
        map.put(SHADOW_CLONE.get(), ShadowClone.createAttributes().build());
        map.put(SHADOW_SOURCE.get(), ShadowSource.createAttributes().build());

        return map;
    }

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Minejago.modLoc(name));
    }

    public static void init() {}
}
