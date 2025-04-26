package dev.thomasglasser.minejago.data.dynamiclights;

import dev.lambdaurora.lambdynlights.api.data.EntityLightSourceDataProvider;
import dev.lambdaurora.lambdynlights.api.entity.EntityLightSource;
import dev.lambdaurora.lambdynlights.api.entity.luminance.ArrowItemDerivedLuminance;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.dynamiclights.entity.GoldenWeaponHolderLuminance;
import dev.thomasglasser.minejago.client.dynamiclights.entity.SpinjitzuLuminance;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class MinejagoEntityLightSourceProvider extends EntityLightSourceDataProvider {
    public MinejagoEntityLightSourceProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryProvider) {
        super(packOutput, registryProvider, Minejago.MOD_ID);
    }

    @Override
    protected void generate(Context context) {
        context.add("spinjitzu", EntityLightSource.EntityPredicate.builder().build(), SpinjitzuLuminance.of(7));
        context.add(MinejagoEntityTypes.THROWN_SHURIKEN_OF_ICE.get(), ArrowItemDerivedLuminance.INSTANCE);
        context.add("skulkin_vehicles", EntityLightSource.EntityPredicate.builder().of(MinejagoEntityTypes.SKULL_TRUCK.get(), MinejagoEntityTypes.SKULL_MOTORBIKE.get()).build(), EntityLuminance.of(5));
        context.add(MinejagoEntityTypeTags.GOLDEN_WEAPON_HOLDERS, GoldenWeaponHolderLuminance.INSTANCE);
    }
}
