package dev.thomasglasser.minejago.world.entity.element.tornadoofcreation;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TornadoOfCreationConfigs {
    public static final ResourceKey<TornadoOfCreationConfig> SKULKIN = create("skulkin");

    private static ResourceKey<TornadoOfCreationConfig> create(String name) {
        return ResourceKey.create(MinejagoRegistries.TORNADO_OF_CREATION_CONFIG, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<TornadoOfCreationConfig> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        context.register(SKULKIN, new TornadoOfCreationConfig(structures.getOrThrow(MinejagoStructures.NINJAGO_CITY),
                List.of(new TornadoOfCreationConfig.BlockRequirement(Blocks.BONE_BLOCK, 10)),
                List.of(new TornadoOfCreationConfig.EntityRequirement(MinejagoEntityTypeTags.SKULKINS, 20))));
    }
}
