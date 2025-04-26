package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedIntrinsicHolderTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoEntityTypeTagsProvider extends ExtendedIntrinsicHolderTagsProvider<EntityType<?>> {
    public MinejagoEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.ENTITY_TYPE, pProvider, (entityType) -> entityType.builtInRegistryHolder().key(), Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(EntityTypeTags.SKELETONS)
                .addTag(MinejagoEntityTypeTags.SKULKINS);

        tag(EntityTypeTags.IMPACT_PROJECTILES)
                .add(MinejagoEntityTypes.THROWN_SHURIKEN_OF_ICE.get())
                .add(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get())
                .add(MinejagoEntityTypes.THROWN_BONE_KNIFE.get())
                .add(MinejagoEntityTypes.EARTH_BLAST.get());

        tag(MinejagoEntityTypeTags.DRAGONS)
                .add(MinejagoEntityTypes.EARTH_DRAGON.get());

        tag(MinejagoEntityTypeTags.NINJA_FRIENDS)
                .add(MinejagoEntityTypes.WU.get())
                .add(MinejagoEntityTypes.KAI.get())
                .add(MinejagoEntityTypes.NYA.get())
                .add(MinejagoEntityTypes.JAY.get())
                .add(MinejagoEntityTypes.COLE.get())
                .add(MinejagoEntityTypes.ZANE.get());

        tag(MinejagoEntityTypeTags.SKULKINS)
                .add(MinejagoEntityTypes.SKULKIN.get())
                .add(MinejagoEntityTypes.NUCKAL.get())
                .add(MinejagoEntityTypes.KRUNCHA.get())
                .add(MinejagoEntityTypes.SAMUKAI.get());

        tag(MinejagoEntityTypeTags.SKULL_TRUCK_RIDERS)
                .add(MinejagoEntityTypes.SAMUKAI.get())
                .add(MinejagoEntityTypes.NUCKAL.get())
                .add(MinejagoEntityTypes.KRUNCHA.get());

        tag(MinejagoEntityTypeTags.GOLDEN_WEAPON_HOLDERS)
                .add(MinejagoEntityTypes.EARTH_DRAGON_HEAD.get());
    }
}
