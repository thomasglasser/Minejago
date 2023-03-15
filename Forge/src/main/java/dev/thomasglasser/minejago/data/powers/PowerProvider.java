package dev.thomasglasser.minejago.data.powers;

import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class PowerProvider extends JsonCodecProvider<Power> {

    private static final List<Power> POWERS = new ArrayList<>();

    /**
     * @param output             {@linkplain PackOutput} provided by the {@link DataGenerator}.
     * @param existingFileHelper
     * @param modid
     */
    public PowerProvider(PackOutput output, ExistingFileHelper existingFileHelper, String modid) {
        super(output, existingFileHelper, modid, JsonOps.INSTANCE, PackType.SERVER_DATA, Minejago.MOD_ID +"/power", Power.CODEC, Map.of());
    }

    @Override
    protected final void gather(BiConsumer<ResourceLocation, Power> consumer) {
        generate();
        POWERS.forEach(power ->
        {
            consumer.accept(power.getId(), power);
        });
    }

    protected abstract void generate();

    protected void add(Power... powers)
    {
        Collections.addAll(POWERS, powers);
    }

    protected ResourceLocation modLoc(String path)
    {
        return new ResourceLocation(super.modid, path);
    }
}
