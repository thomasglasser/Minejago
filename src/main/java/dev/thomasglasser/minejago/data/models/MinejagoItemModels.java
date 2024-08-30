package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.models.ExtendedItemModelProvider;
import dev.thomasglasser.tommylib.api.world.level.block.WoodSet;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MinejagoItemModels extends ExtendedItemModelProvider {
    public MinejagoItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, Minejago.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {
        basicItemHandheld(MinejagoItems.BONE_KNIFE);
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
        basicItem(MinejagoItems.TEACUP.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get());
        basicItem(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get());
        basicItem(MinejagoItems.POTTERY_SHERD_THUNDER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_PEAKS.get());
        basicItem(MinejagoItems.POTTERY_SHERD_MASTER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_YIN_YANG.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoBlocks.TEAPOT.get().asItem());
        basicItem(MinejagoBlocks.JASPOT.get().asItem());
        basicItem(MinejagoBlocks.FLAME_TEAPOT.get().asItem());
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

        withExistingParent(MinejagoItems.FILLED_TEACUP.getId().getPath(), mcItemLoc("generated"))
                .texture("layer0", modItemLoc(MinejagoItems.FILLED_TEACUP.getId().getPath() + "_overlay"))
                .texture("layer1", modItemLoc(MinejagoItems.FILLED_TEACUP.getId().getPath()));

        MinejagoBlocks.TEAPOTS.forEach((dyeColor, itemRegistryObject) -> {
            if (existingFileHelper.exists(Minejago.modLoc("textures/item/" + dyeColor.getName() + "_teapot.png"), PackType.CLIENT_RESOURCES))
                basicItem(itemRegistryObject.get().asItem());
        });

        spawnEgg(MinejagoItems.WU_SPAWN_EGG);
        spawnEgg(MinejagoItems.KAI_SPAWN_EGG);
        spawnEgg(MinejagoItems.NYA_SPAWN_EGG);
        spawnEgg(MinejagoItems.COLE_SPAWN_EGG);
        spawnEgg(MinejagoItems.JAY_SPAWN_EGG);
        spawnEgg(MinejagoItems.ZANE_SPAWN_EGG);
        spawnEgg(MinejagoItems.SKULKIN_SPAWN_EGG);
        spawnEgg(MinejagoItems.KRUNCHA_SPAWN_EGG);
        spawnEgg(MinejagoItems.NUCKAL_SPAWN_EGG);
        spawnEgg(MinejagoItems.SKULKIN_HORSE_SPAWN_EGG);
        spawnEgg(MinejagoItems.EARTH_DRAGON_SPAWN_EGG);
        spawnEgg(MinejagoItems.SAMUKAI_SPAWN_EGG);
        spawnEgg(MinejagoItems.SKULL_TRUCK_SPAWN_EGG);
        spawnEgg(MinejagoItems.SKULL_MOTORBIKE_SPAWN_EGG);

        withExistingParent(MinejagoItems.EMPTY_GOLDEN_WEAPONS_MAP.getId().getPath(), "item/map");

//        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        WoodSet set = MinejagoBlocks.ENCHANTED_WOOD_SET;
        withExistingParent(set.log().getId().getPath(), blockLoc(set.log()));
        withExistingParent(set.strippedLog().getId().getPath(), blockLoc(set.strippedLog()));
        withExistingParent(set.wood().getId().getPath(), blockLoc(set.wood()));
        withExistingParent(set.strippedWood().getId().getPath(), blockLoc(set.strippedWood()));
        withExistingParent(set.planks().getId().getPath(), blockLoc(set.planks()));
        withExistingParent(set.slab().getId().getPath(), blockLoc(set.slab()));
        withExistingParent(set.stairs().getId().getPath(), blockLoc(set.stairs()));
        withExistingParent(set.pressurePlate().getId().getPath(), blockLoc(set.pressurePlate()));
        buttonInventory(set.button().getId().getPath(), blockLoc(set.planks()));
        fenceInventory(set.fence().getId().getPath(), blockLoc(set.planks()));
        withExistingParent(set.fenceGate().getId().getPath(), blockLoc(set.fenceGate()));
        // TODO: Door item texture
//        basicItem(set.door().asItem());
        // TODO: Trapdoor block textures
//        withExistingParent(set.trapdoor().getId().getPath(), blockLoc(set.trapdoor()));
        // TODO: Sign item texture
//        basicItem(set.sign().asItem());
        // TODO: Wall sign item texture
//        basicItem(set.hangingSign().asItem());
        // TODO: Boat item texture
//        basicItem(set.boatItem());
        // TODO: Chest boat item texture
//        basicItem(set.chestBoatItem());

        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);

        MinejagoArmors.TRAINING_GI_SET.getAll().forEach(item -> withEntityModel(item).guiLight(BlockModel.GuiLight.FRONT));

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
