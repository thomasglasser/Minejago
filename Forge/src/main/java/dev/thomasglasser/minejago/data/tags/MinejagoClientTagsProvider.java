package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dhyces.trimmed.api.data.tags.ClientTagDataProvider;
import dhyces.trimmed.api.data.tags.appenders.ClientTagAppender;
import dhyces.trimmed.impl.client.tags.ClientTagKey;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoClientTagsProvider extends ClientTagDataProvider {
    public MinejagoClientTagsProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Minejago.MOD_ID, existingFileHelper);
    }

    public static final ClientTagKey CUSTOM_TRIM_PATTERN_TEXTURES = ClientTagKey.of(new ResourceLocation(Minejago.Dependencies.TRIMMED.getModId(), "custom_armor_trim_pattern_textures"));

    @Override
    protected void addTags() {
        ClientTagAppender customTrimPatternTextures = clientTag(CUSTOM_TRIM_PATTERN_TEXTURES);
        pattern(customTrimPatternTextures, MinejagoTrimPatterns.FOUR_WEAPONS.location());
    }

    private void pattern(ClientTagAppender appender, ResourceLocation pattern)
    {
        appender
                .add(new ResourceLocation(pattern.getNamespace(), "trims/models/armor/" + pattern.getPath()))
                .add(new ResourceLocation(pattern.getNamespace(), "trims/models/armor/" + pattern.getPath() + "_leggings"));
    }
}
