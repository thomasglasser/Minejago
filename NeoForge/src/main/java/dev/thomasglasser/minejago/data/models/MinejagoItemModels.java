package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.models.ExtendedItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class MinejagoItemModels extends ExtendedItemModelProvider
{
    public MinejagoItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Minejago.MOD_ID, helper);

    }

    @Override
    protected void registerModels()
    {
        basicItemHandheld(MinejagoItems.BONE_KNIFE);
        basicItemHandheld(MinejagoItems.IRON_SHURIKEN);
        MinejagoArmors.ARMOR_SETS.forEach(armorSet -> armorSet.getAll().forEach(item ->
        {
            String nameForSlot = switch (armorSet.getForItem(item.get())) {
                case FEET -> "boots";
                case LEGS -> "pants";
                case CHEST -> "jacket";
                case HEAD -> "hood";
                default -> null;
            };

            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + armorSet.getName() + "_" + nameForSlot));
        }));
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + item.get().getVariant().getColor().getName())));
        basicItem(MinejagoArmors.SAMUKAIS_CHESTPLATE.get());
        basicItem(MinejagoItems.TEACUP.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get());
        basicItemHandheld(MinejagoItems.IRON_KATANA);
        basicItem(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get());
        basicItem(MinejagoItems.POTTERY_SHERD_THUNDER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_PEAKS.get());
        basicItem(MinejagoItems.POTTERY_SHERD_MASTER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_YIN_YANG.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoBlocks.TEAPOT.get().asItem());
        basicItem(MinejagoBlocks.JASPOT.get().asItem());
        basicItem(MinejagoBlocks.FLAME_TEAPOT.get().asItem());
        basicItem(MinejagoBlocks.GOLD_DISC.get().asItem());
        basicItem(MinejagoBlocks.TOP_POST.get().asItem());
        basicItem(MinejagoItems.SCROLL.get());
        basicItem(MinejagoItems.WRITABLE_SCROLL.get());
        basicItem(MinejagoItems.WRITTEN_SCROLL.get());

        basicInventoryItem(MinejagoItems.WOODEN_NUNCHUCKS);

        MinejagoBlocks.TEAPOTS.forEach((dyeColor, itemRegistryObject) ->
        {
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

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }
}