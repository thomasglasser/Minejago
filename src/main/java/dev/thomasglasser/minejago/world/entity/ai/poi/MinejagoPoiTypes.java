package dev.thomasglasser.minejago.world.entity.ai.poi;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.stream.Collectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class MinejagoPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<PoiType, PoiType> TEAPOTS = POI_TYPES.register("teapots", () -> new PoiType(MinejagoBlocks.allPots().stream().flatMap(p_218097_ -> p_218097_.getStateDefinition().getPossibleStates().stream()).collect(Collectors.toSet()), 1, 1));

    public static void init() {}
}
