package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import dev.thomasglasser.minejago.world.level.block.LeavesSet;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStates.modBlockModel;

public class MinejagoItemModels extends ItemModelProvider
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
        MinejagoArmors.ARMOR_SETS.forEach(armorSet ->
        {
            armorSet.getAll().forEach(item ->
            {
                String nameForSlot = switch (armorSet.getForItem(item.get())) {
                    case FEET -> "boots";
                    case LEGS -> "pants";
                    case CHEST -> "jacket";
                    case HEAD -> "hood";
                    default -> null;
                };

                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + armorSet.getName() + "_" + nameForSlot));
            });
        });
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + ((SkeletalChestplateItem)item.get()).getVariant().getColor().getName())));
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

    protected void basicItemHandheld(ResourceLocation item)
    {
        singleTexture(item.getPath(), mcLoc("item/handheld"), "layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }

    protected void basicItemHandheld(RegistryObject<? extends Item> item)
    {
        basicItemHandheld(item.getId());
    }

    protected void spawnEgg(String path)
    {
        withExistingParent(path, mcLoc("item/template_spawn_egg"));
    }

    protected void spawnEgg(RegistryObject<SpawnEggItem> egg)
    {
        withExistingParent(egg.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    protected void woodSet(WoodSet set)
    {
        withExistingParent(set.planks().getId().getPath(), modBlockModel(set.planks().getId().getPath()));
        withExistingParent(set.log().getId().getPath(), modBlockModel(set.log().getId().getPath()));
//        withExistingParent(BuiltInRegistries.BLOCK.getKey(set.strippedLog().get()).getPath(), modBlockModel(set.strippedLog().get()));
        withExistingParent(set.wood().getId().getPath(), modBlockModel(set.wood().getId().getPath()));
//        withExistingParent(BuiltInRegistries.BLOCK.getKey(set.strippedWood().get()).getPath(), modBlockModel(set.strippedWood().get()));
    }

    protected void leavesSet(LeavesSet set)
    {
        withExistingParent(set.leaves().getId().getPath(), modBlockModel(BuiltInRegistries.BLOCK.getKey(set.leaves().get()).getPath()));
        singleTexture(set.sapling().getId().getPath(), mcItemModel("generated"), "layer0", modBlockModel(set.sapling().getId().getPath()));
    }

    public static ResourceLocation modItemModel(String path)
    {
        return Minejago.modLoc("item/" + path);
    }

    public static ResourceLocation mcItemModel(String path)
    {
        return new ResourceLocation("item/" + path);
    }
}