package dev.thomasglasser.minejago.advancements.criterion;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Set;

public class PowerPredicate {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final PowerPredicate ANY = new PowerPredicate();
    @Nullable
    private final Set<ResourceKey<Power>> powers;
    @Nullable
    private final TagKey<Power> tag;

    public PowerPredicate(@Nullable Set<ResourceKey<Power>> resourceKey, @Nullable TagKey<Power> tagKey)
    {
        powers = resourceKey;
        tag = tagKey;
    }

    public PowerPredicate()
    {
        this(null, null);
    }


    public boolean matches(ResourceKey<Power> key, Registry<Power> registry) {
        if (this == ANY) {
            return true;
        } else if (this.tag != null && !registry.getOrThrow(key).is(this.tag, registry)) {
            return false;
        } else {
            return this.powers != null && !this.powers.contains(key);
        }
    }

    public JsonElement serializeToJson() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        } else {
            JsonObject jsonObject = new JsonObject();

            if (this.powers != null) {
                JsonArray jsonArray = new JsonArray();

                for(ResourceKey<Power> power : this.powers) {
                    jsonArray.add(power.location().toString());
                }

                jsonObject.add("powers", jsonArray);
            }

            if (this.tag != null) {
                jsonObject.addProperty("tag", this.tag.location().toString());
            }

            return jsonObject;
        }
    }

    public static PowerPredicate fromJson(@Nullable JsonElement pJson) {
        if (pJson != null && !pJson.isJsonNull()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(pJson, "power");

            Set<ResourceKey<Power>> set = null;
            JsonArray jsonArray = GsonHelper.getAsJsonArray(jsonObject, "powers", null);
            if (jsonArray != null) {
                ImmutableSet.Builder<ResourceKey<Power>> builder = ImmutableSet.builder();

                for(JsonElement jsonElement : jsonArray) {
                    ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.convertToString(jsonElement, "power"));
                    builder.add(
                            ResourceKey.create(MinejagoRegistries.POWER, resourceLocation)
                    );
                }

                set = builder.build();
            }

            TagKey<Power> tagKey = null;
            if (jsonObject.has("tag")) {
                ResourceLocation resourceLocation2 = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
                tagKey = TagKey.create(MinejagoRegistries.POWER, resourceLocation2);
            }

            return new PowerPredicate(set, tagKey);
        } else {
            return ANY;
        }
    }
}