package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.TeacupItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.models.ExtendedItemModelProvider;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MinejagoItemModelProvider extends ExtendedItemModelProvider {
    public MinejagoItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Minejago.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        handheldItem(MinejagoItems.BONE_KNIFE);
        MinejagoArmors.ARMOR_SETS.forEach(armorSet -> armorSet.getAll().forEach(item -> {
            String nameForSlot = switch (armorSet.getForItem(item.get())) {
                case FEET -> "boots";
                case LEGS -> "pants";
                case CHEST -> "jacket";
                case HEAD -> "hood";
                default -> null;
            };

            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + armorSet.getName() + "_" + nameForSlot));
        }));
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item -> singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + item.get().getVariant().getColor().getName())));
        basicItem(MinejagoArmors.SAMUKAIS_CHESTPLATE.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get());
        basicItem(MinejagoItems.ICE_CUBE_POTTERY_SHERD.get());
        basicItem(MinejagoItems.THUNDER_POTTERY_SHERD.get());
        basicItem(MinejagoItems.PEAKS_POTTERY_SHERD.get());
        basicItem(MinejagoItems.MASTER_POTTERY_SHERD.get());
        basicItem(MinejagoItems.YIN_YANG_POTTERY_SHERD.get());
        basicItem(MinejagoItems.DRAGONS_HEAD_POTTERY_SHERD.get());
        basicItem(MinejagoItems.DRAGONS_TAIL_POTTERY_SHERD.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoBlocks.TEAPOT.get().asItem());
        basicItem(MinejagoBlocks.JASPOT.get().asItem());
        basicItem(MinejagoBlocks.GOLD_DISC.get().asItem());
        basicItem(MinejagoBlocks.TOP_POST.get().asItem());
        basicItem(MinejagoItems.SCROLL.get());
        basicItem(MinejagoItems.WRITABLE_SCROLL.get());
        basicItem(MinejagoItems.WRITTEN_SCROLL.get());
        basicItem(MinejagoBlocks.DRAGON_BUTTON.get().asItem());
        basicItem(MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT);
        basicItem(MinejagoBlocks.EARTH_DRAGON_HEAD.get().asItem());
        basicItem(MinejagoItems.NINJA_BANNER_PATTERN.get());

        basicBlockItem(MinejagoBlocks.SCROLL_SHELF);

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

        MinejagoBlocks.TEAPOTS.forEach((dyeColor, itemRegistryObject) -> basicItem(itemRegistryObject.get().asItem()));

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

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);

        MinejagoArmors.TRAINEE_GI_SET.getAll().forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT));

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

        withExistingParent(MinejagoBlocks.CHISELED_SCROLL_SHELF.getId().getPath(), MinejagoBlocks.CHISELED_SCROLL_SHELF.getId().withPrefix("block/").withSuffix("_inventory"));
    }
}
