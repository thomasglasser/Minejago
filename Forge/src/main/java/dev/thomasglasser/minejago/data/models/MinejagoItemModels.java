package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoItemModels extends ItemModelProvider
{
    public MinejagoItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Minejago.MOD_ID, helper);

    }

    @Override
    protected void registerModels()
    {
        singleTexture(MinejagoItems.BONE_KNIFE.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/bone_knife"));
        singleTexture(MinejagoItems.IRON_SHURIKEN.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_shuriken"));
        MinejagoArmor.BLACK_GI_SET.getAll().forEach(item ->
        {
            String nameForSlot = switch (MinejagoArmor.BLACK_GI_SET.getForItem(item.get())) {
                case FEET -> "boots";
                case LEGS -> "pants";
                case CHEST -> "jacket";
                case HEAD -> "hood";
                default -> null;
            };

            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_" + nameForSlot));
        });
        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + ((SkeletalChestplateItem)item.get()).getVariant().getColor().getName())));
        singleTexture(MinejagoItems.TEACUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teacup"));
        singleTexture(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/four_weapons_banner_pattern"));
        singleTexture(MinejagoBlocks.TEAPOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teapot"));
        singleTexture(MinejagoItems.IRON_KATANA.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_katana"));

        final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        MinejagoPowers.POWERS.addToSet(builder);
        HolderLookup.Provider provider = builder.build(access);

        provider.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
                {
                    if (powerReference.get().doMakeSets())
                    {
                        MinejagoArmor.POWER_SETS.forEach(armorSet ->
                                armorSet.getAll().forEach(item ->
                                {
                                    String path = powerReference.key().location().getPath() + "_" + item.getId().getPath();
                                    singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                                }));
                    }
                });

        withExistingParent(MinejagoItems.WU_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.KAI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.NYA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.COLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.JAY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.ZANE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.UNDERWORLD_SKELETON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.KRUNCHA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.NUCKAL_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
