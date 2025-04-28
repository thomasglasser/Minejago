package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.TeacupItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.models.ExtendedItemModelProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoItemModelProvider extends ExtendedItemModelProvider {
    protected final CompletableFuture<HolderLookup.Provider> provider;

    @Nullable
    protected HolderLookup.Provider registries;

    public MinejagoItemModelProvider(PackOutput output, ExistingFileHelper helper, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Minejago.MOD_ID, helper);
        this.provider = provider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return provider.thenCompose(registries -> {
            this.registries = registries;
            return super.run(cache);
        });
    }

    @Override
    protected void registerModels() {
        handheldItem(MinejagoItems.BONE_KNIFE);
        basicItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN);
        basicItem(MinejagoItems.ICE_CUBE_POTTERY_SHERD);
        basicItem(MinejagoItems.THUNDER_POTTERY_SHERD);
        basicItem(MinejagoItems.PEAKS_POTTERY_SHERD);
        basicItem(MinejagoItems.MASTER_POTTERY_SHERD);
        basicItem(MinejagoItems.YIN_YANG_POTTERY_SHERD);
        basicItem(MinejagoItems.DRAGONS_HEAD_POTTERY_SHERD);
        basicItem(MinejagoItems.DRAGONS_TAIL_POTTERY_SHERD);
        basicItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE);
        basicItem(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE);
        basicItem(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE);
        basicItem(MinejagoBlocks.TEAPOT.asItem());
        basicItem(MinejagoBlocks.JASPOT.asItem());
        basicItem(MinejagoBlocks.GOLD_DISC.asItem());
        basicItem(MinejagoBlocks.TOP_POST.asItem());
        basicItem(MinejagoItems.SCROLL);
        basicItem(MinejagoItems.WRITABLE_SCROLL);
        basicItem(MinejagoItems.WRITTEN_SCROLL);
        basicItem(MinejagoBlocks.DRAGON_BUTTON.asItem());
        basicItem(MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.NINJA_BANNER_PATTERN);
        basicItem(MinejagoItems.EARTH_DRAGON_HEAD);

        basicBlockItem(MinejagoBlocks.SCROLL_SHELF);
        basicBlockItem(MinejagoBlocks.FREEZING_ICE);

        for (TeacupItem teacup : MinejagoItems.allTeacups()) {
            basicItem(teacup);
        }
        withExistingParent(MinejagoItems.FILLED_TEACUP.getId().getPath(), mcItemLoc("generated"))
                .texture("layer0", modItemLoc(MinejagoItems.TEACUP.getId().getPath()))
                .texture("layer1", modItemLoc(MinejagoItems.FILLED_TEACUP.getId().getPath() + "_overlay"));
        for (DyeColor color : DyeColor.values()) {
            withExistingParent(MinejagoItems.FILLED_TEACUPS.get(color).getId().getPath(), mcItemLoc("generated"))
                    .texture("layer0", modItemLoc(MinejagoItems.TEACUPS.get(color).getId().getPath()))
                    .texture("layer1", modItemLoc(MinejagoItems.FILLED_TEACUP.getId().getPath() + "_overlay"));
        }
        withExistingParent(MinejagoItems.FILLED_MINICUP.getId().getPath(), mcItemLoc("generated"))
                .texture("layer0", modItemLoc(MinejagoItems.MINICUP.getId().getPath()))
                .texture("layer1", modItemLoc(MinejagoItems.FILLED_TEACUP.getId().getPath() + "_overlay"));

        MinejagoBlocks.TEAPOTS.forEach((dyeColor, itemRegistryObject) -> basicItem(itemRegistryObject.asItem()));

        spawnEggItem(MinejagoItems.WU_SPAWN_EGG);
        spawnEggItem(MinejagoItems.KAI_SPAWN_EGG);
        spawnEggItem(MinejagoItems.NYA_SPAWN_EGG);
        spawnEggItem(MinejagoItems.COLE_SPAWN_EGG);
        spawnEggItem(MinejagoItems.JAY_SPAWN_EGG);
        spawnEggItem(MinejagoItems.ZANE_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SKULKIN_SPAWN_EGG);
        spawnEggItem(MinejagoItems.KRUNCHA_SPAWN_EGG);
        spawnEggItem(MinejagoItems.NUCKAL_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SKULKIN_HORSE_SPAWN_EGG);
        spawnEggItem(MinejagoItems.EARTH_DRAGON_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SAMUKAI_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SKULL_TRUCK_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SKULL_MOTORBIKE_SPAWN_EGG);
        spawnEggItem(MinejagoItems.SPYKOR_SPAWN_EGG);

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);

        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item -> singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + item.get().getVariant().getSerializedName())));
        basicItem(MinejagoArmors.SAMUKAIS_CHESTPLATE);

        MinejagoArmors.NORMAL_GI_SETS.forEach(set -> set.getAll().forEach(item -> singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getId().getPath()))));
        MinejagoArmors.STANDALONE_GI.forEach(item -> singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + item.getId().getPath())));
        registries.lookupOrThrow(MinejagoRegistries.ELEMENT).listElements().forEach(element -> {
            if (element.value().hasSets()) {
                MinejagoArmors.ELEMENTAL_GI_SETS.forEach(set -> {
                    set.getAllKeys().forEach(item -> {
                        String path = element.getKey().location().getPath() + "_" + item.location().getPath();
                        singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                    });
                });
                MinejagoArmors.STANDALONE_ELEMENTAL_GI.forEach(item -> {
                    String path = element.getKey().location().getPath() + "_" + item.getKey().location().getPath();
                    singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                });
            }
            if (element.value().hasSpecialSets()) {
                MinejagoArmors.SPECIAL_ELEMENTAL_GI_SETS.forEach(set -> {
                    set.getAllKeys().forEach(item -> {
                        String path = element.getKey().location().getPath() + "_" + item.location().getPath();
                        singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                    });
                });
                MinejagoArmors.STANDALONE_SPECIAL_ELEMENTAL_GI.forEach(item -> {
                    String path = element.getKey().location().getPath() + "_" + item.getKey().location().getPath();
                    singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                });
            }
        });
        MinejagoArmors.ELEMENTAL_GI_SETS.forEach(set -> set.getAll().forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT)));
        MinejagoArmors.SPECIAL_ELEMENTAL_GI_SETS.forEach(set -> set.getAll().forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT)));
        MinejagoArmors.STANDALONE_ELEMENTAL_GI.forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT));
        MinejagoArmors.STANDALONE_SPECIAL_ELEMENTAL_GI.forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT));

        withEntityModelInHand(MinejagoItems.BAMBOO_STAFF, withEntityModel(MinejagoItems.BAMBOO_STAFF)
                .texture("particle", itemLoc(MinejagoItems.BAMBOO_STAFF))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).translation(8, 5, 10.5f).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).translation(-8, 5, 10.5f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(-3, 8, 1).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(13, 8, 1).end()
                .end(), basicInventoryItem(MinejagoItems.BAMBOO_STAFF));
        withEntityModelInHand(MinejagoItems.SCYTHE_OF_QUAKES, withEntityModel(MinejagoItems.SCYTHE_OF_QUAKES)
                .texture("particle", itemLoc(MinejagoItems.SCYTHE_OF_QUAKES))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, -90, 0).translation(-8, 25, 10).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0, 90, 0).translation(8, 25, 10).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, -90, 25).translation(-3, 25, 1).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 90, -25).translation(13, 25, 1).end()
                .end(), basicInventoryItem(MinejagoItems.SCYTHE_OF_QUAKES));
        withEntityModelInHand(MinejagoItems.SHURIKEN_OF_ICE, withEntityModel(MinejagoItems.SHURIKEN_OF_ICE)
                .texture("particle", itemLoc(MinejagoItems.SHURIKEN_OF_ICE))
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(30, -90, 0).translation(-8, 19, 20).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(30, 90, 0).translation(8, 19, 20).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 90, 30).translation(2, 23, 10).scale(0.6f).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, -90, -30).translation(-7.6f, 23, 10).scale(0.6f).end()
                .end(), basicInventoryItem(MinejagoItems.SHURIKEN_OF_ICE));
        withEntityModel(MinejagoItems.NUNCHUCKS_OF_LIGHTNING)
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).translation(0, -6, -2).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).translation(0, -6, -2).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).translation(0, -4, 0).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).translation(0, -4, 0).end()
                .end();
        // TODO: 2d nunchucks
//        withEntityModelInHand(MinejagoItems.NUNCHUCKS_OF_LIGHTNING, <entity model>, basicInventoryItem(MinejagoItems.NUNCHUCKS_OF_LIGHTNING));

        withExistingParent(MinejagoBlocks.CHISELED_SCROLL_SHELF.getId().getPath(), MinejagoBlocks.CHISELED_SCROLL_SHELF.getId().withPrefix("block/").withSuffix("_inventory"));
    }
}
