package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GiSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final GiSubtypeInterpreter INSTANCE = new GiSubtypeInterpreter();

    private GiSubtypeInterpreter() {}

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.get(MinejagoDataComponents.ELEMENT);
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        return "";
    }
}
