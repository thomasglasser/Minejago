package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
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
        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
        {
            if (item.get() instanceof SkeletalChestplateItem chestplate)
                singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_" + chestplate.getVariant().getColor().getName()));
        });
        singleTexture(MinejagoItems.TEACUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teacup"));
        singleTexture(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/four_weapons_banner_pattern"));
        singleTexture(MinejagoBlocks.TEAPOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teapot"));
        MinejagoArmor.SETS.forEach(set ->
                {
                    set.getAll().forEach(item ->
                    {
                        String nameForSlot = switch (set.getForItem(item)) {

                                case MAINHAND, OFFHAND -> null;
                                case FEET -> "boots";
                                case LEGS -> "leggings";
                                case CHEST -> "chestplate";
                                case HEAD -> "helmet";
                            };
                            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + set.getName() + "_" + nameForSlot));
                    });
                });
//        MinejagoArmor.POWERED_SETS.forEach(set ->
//                {
//                    set.getAll().forEach(item ->
//                    {
//                        String nameForSlot = switch (set.getForItem(item)) {
//
//                                case MAINHAND, OFFHAND -> null;
//                                case FEET -> "boots";
//                                case LEGS -> "leggings";
//                                case CHEST -> "chestplate";
//                                case HEAD -> "helmet";
//                            };
//                            singleTexture(item.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/" + set.getName() + "_" + nameForSlot));
//                    });
//                });
        singleTexture(MinejagoItems.IRON_KATANA.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_katana"));

        withExistingParent(MinejagoItems.WU_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.KAI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.NYA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.UNDERWORLD_SKELETON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.KRUNCHA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.NUCKAL_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
