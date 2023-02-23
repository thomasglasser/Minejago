package dev.thomasglasser.minejago.data.powers;

import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class PowerProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final Map<ResourceLocation, Power> powers = new HashMap<>();
    private final String modId;

    public PowerProvider(String modId, PackOutput pOutput) {
        this.modId = modId;
        this.pathProvider = pOutput.createPathProvider(PackOutput.Target.DATA_PACK, "powers");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        this.generate();
        return CompletableFuture.allOf(powers.entrySet().stream().map((entry) -> {
                ResourceLocation resourcelocation1 = entry.getKey();
                Power power = entry.getValue();
                Path path = this.pathProvider.json(resourcelocation1);
                return DataProvider.saveStable(pOutput, Power.CODEC.encodeStart(JsonOps.INSTANCE, power).getOrThrow(false, LOGGER::error), path);
            }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Powers";
    }

    protected void add(Power... power)
    {
        for (Power power1 : power)
        {
            powers.put(power1.getId(), power1);
        }
    }

    protected abstract void generate();

    protected ResourceLocation modLoc(String path)
    {
        return new ResourceLocation(modId, path);
    }
}
