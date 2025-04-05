package dev.thomasglasser.minejago.core;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoBuiltInRegistries;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.datamaps.MinejagoDataMaps;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.tommylib.api.packs.PackInfo;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class MinejagoCoreEvents {
    public static void onAddPackFinders(AddPackFindersEvent event) {
        for (PackInfo info : MinejagoPacks.getPacks()) {
            if (event.getPackType() == info.type()) {
                var resourcePath = ModList.get().getModFileById(Minejago.MOD_ID).getFile().findResource("packs/" + info.knownPack().namespace() + "/" + info.knownPack().id());
                var pack = Pack.readMetaAndCreate(new PackLocationInfo("builtin/" + info.knownPack().id(), Component.translatable(info.titleKey()), info.source(), Optional.of(info.knownPack())), new Pack.ResourcesSupplier() {
                    @Override
                    public PackResources openFull(PackLocationInfo p_326241_, Pack.Metadata p_325959_) {
                        return new PathPackResources(p_326241_, resourcePath);
                    }

                    @Override
                    public PackResources openPrimary(PackLocationInfo p_326301_) {
                        return new PathPackResources(p_326301_, resourcePath);
                    }
                }, info.type(), PackInfo.BUILT_IN_SELECTION_CONFIG);
                event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
            }
        }
    }

    public static void onNewRegistry(NewRegistryEvent event) {
        event.register(MinejagoBuiltInRegistries.FOCUS_MODIFIER_SERIALIZER);
        event.register(MinejagoBuiltInRegistries.SKULKIN_RAID_TYPES);
    }

    public static void onNewDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MinejagoRegistries.POWER, Power.CODEC, Power.CODEC);
        event.dataPackRegistry(MinejagoRegistries.FOCUS_MODIFIER, FocusModifier.DIRECT_CODEC, FocusModifier.DIRECT_CODEC);
    }

    public static void onRegisterDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(MinejagoDataMaps.POTION_FILLABLES);
        event.register(MinejagoDataMaps.POTION_DRAINABLES);
    }
}
