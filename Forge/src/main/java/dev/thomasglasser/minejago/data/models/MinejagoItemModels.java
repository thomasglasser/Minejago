package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class MinejagoItemModels extends ItemModelProvider
{
    public MinejagoItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Minejago.MOD_ID, helper);

    }

    @Override
    protected void registerModels()
    {
        basicItemHandheld(MinejagoItems.BONE_KNIFE.get());
        basicItemHandheld(MinejagoItems.IRON_SHURIKEN.get());
        MinejagoArmors.BLACK_GI_SET.getAll().forEach(item ->
        {
            String nameForSlot = switch (MinejagoArmors.BLACK_GI_SET.getForItem(item.get())) {
                case FEET -> "boots";
                case LEGS -> "pants";
                case CHEST -> "jacket";
                case HEAD -> "hood";
                default -> null;
            };

            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_" + nameForSlot));
        });
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + ((SkeletalChestplateItem)item.get()).getVariant().getColor().getName())));
        basicItem(MinejagoItems.TEACUP.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get());
        basicItemHandheld(MinejagoItems.IRON_KATANA.get());
        basicItem(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get());
        basicItem(MinejagoItems.POTTERY_SHERD_THUNDER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_PEAKS.get());
        basicItem(MinejagoItems.POTTERY_SHERD_MASTER.get());
        basicItem(MinejagoItems.POTTERY_SHERD_YIN_YANG.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get());
        basicItem(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get());
        basicItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        basicItem(MinejagoItems.TEAPOT.get());
        basicItem(MinejagoItems.JASPOT.get());
        basicItem(MinejagoItems.GOLD_DISC.get());
        basicItem(MinejagoItems.TOP_POST.get());
        basicItem(MinejagoItems.SCROLL.get());
        basicItem(MinejagoItems.WRITABLE_SCROLL.get());
        basicItem(MinejagoItems.WRITTEN_SCROLL.get());

        MinejagoItems.TEAPOTS.forEach((dyeColor, itemRegistryObject) ->
        {
            if (existingFileHelper.exists(Minejago.modLoc("textures/item/" + dyeColor.getName() + "_teapot.png"), PackType.CLIENT_RESOURCES))
                basicItem(itemRegistryObject.get());
        });

        final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        MinejagoPowers.POWERS.addToSet(builder);
        HolderLookup.Provider provider = builder.build(access);

        provider.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
        {
            if (powerReference.get().hasSets())
            {
                MinejagoArmors.POWER_SETS.forEach(armorSet ->
                        armorSet.getAll().forEach(item ->
                        {
                            String path = powerReference.key().location().getPath() + "_" + item.getId().getPath();
                            singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                        }));
            }
        });

        spawnEgg(MinejagoItems.WU_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.KAI_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.NYA_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.COLE_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.JAY_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.ZANE_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.SKULKIN_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.KRUNCHA_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.NUCKAL_SPAWN_EGG.getId().getPath());
        spawnEgg(MinejagoItems.SKULKIN_HORSE_SPAWN_EGG.getId().getPath());


    }

    protected void basicItemHandheld(ResourceLocation item)
    {
        singleTexture(item.getPath(), mcLoc("item/handheld"), "layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }

    protected void basicItemHandheld(Item item)
    {
        basicItemHandheld(ForgeRegistries.ITEMS.getKey(item));
    }

    protected void spawnEgg(String path)
    {
        withExistingParent(path, mcLoc("item/template_spawn_egg"));
    }
}