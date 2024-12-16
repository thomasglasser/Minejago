package dev.thomasglasser.minejago.data.patches;

import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.enderturret.patchedmod.data.PatchProvider;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class MinejagoDataPatchProvider extends PatchProvider {
    List<EntityType<?>> MOD_MOBS_TO_KILL = List.of(
            MinejagoEntityTypes.SKULKIN.get(),
            MinejagoEntityTypes.KRUNCHA.get(),
            MinejagoEntityTypes.NUCKAL.get(),
            MinejagoEntityTypes.SKULKIN_HORSE.get(),
            MinejagoEntityTypes.SAMUKAI.get(),
            MinejagoEntityTypes.SKULL_TRUCK.get(),
            MinejagoEntityTypes.SKULL_MOTORBIKE.get());

    public MinejagoDataPatchProvider(DataGenerator generator) {
        super(generator, PackOutput.Target.DATA_PACK, Minejago.MOD_ID);
    }

    @Override
    public void registerPatches() {
        HolderLookup.Provider provider = VanillaRegistries.createLookup();
        HolderGetter<EntityType<?>> entities = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        Map<String, Criterion<?>> mobsToKill = getMobsToKill(entities, MOD_MOBS_TO_KILL);
        OperationBuilder killAMob = patch(mcLoc("advancement/adventure/kill_a_mob"))
                .compound();
        OperationBuilder killAllMobs = patch(mcLoc("advancement/adventure/kill_all_mobs"))
                .compound();
        mobsToKill.forEach((string, element) -> {
            killAMob.add("/criteria/" + string, element, Criterion.CODEC, provider);
            killAMob.add("/requirements/0/-", string);

            killAllMobs.add("/criteria/" + string, element, Criterion.CODEC, provider);
            killAllMobs.add("/requirements/-", new String[] { string });
        });
        killAMob.end();
        killAllMobs.end();

        OperationBuilder allPotions = patch(mcLoc("advancement/nether/all_potions"))
                .compound();
        OperationBuilder allEffects = patch(mcLoc("advancement/nether/all_effects"))
                .compound();
        List.of(
                MinejagoMobEffects.HYPERFOCUS).forEach(potion -> {
                    allPotions.add("/criteria/all_effects/conditions/effects/" + potion.getId(), new JsonObject());
                    allEffects.add("/criteria/all_effects/conditions/effects/" + potion.getId(), new JsonObject());
                });
        allPotions.end();
        allEffects.end();
    }

    protected static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    private static Map<String, Criterion<?>> getMobsToKill(HolderGetter<EntityType<?>> entities, List<EntityType<?>> mobs) {
        HashMap<String, Criterion<?>> criterion = new HashMap<>();
        mobs.forEach(
                entityType -> criterion.put(
                        BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString(),
                        KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityType))));
        return criterion;
    }
}
