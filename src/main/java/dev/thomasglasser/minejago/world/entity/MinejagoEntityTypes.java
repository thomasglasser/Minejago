package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
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
}
