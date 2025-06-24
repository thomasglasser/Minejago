package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.gui.screens.inventory.DragonInventoryScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ElementSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.SkillScreen;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.network.ServerboundChangeVipDataPayload;
import dev.thomasglasser.minejago.world.entity.ElementGiver;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.inventory.DragonInventoryMenu;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.entity.player.SpecialPlayerUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class MinejagoClientUtils {
    private static final HashMap<Player, VipData> vipData = new HashMap<>();
    private static final MinejagoBlockEntityWithoutLevelRenderer bewlr = new MinejagoBlockEntityWithoutLevelRenderer();

    public static boolean shouldRenderBetaTesterLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, betaChoice(player).slot()) && vipData.get(player).displayBeta();
    }

    public static BetaTesterCosmeticOptions betaChoice(AbstractClientPlayer player) {
        return vipData.get(player) != null && vipData.get(player).choice() != null ? vipData.get(player).choice() : BetaTesterCosmeticOptions.BAMBOO_HAT;
    }

    public static boolean renderDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD/*TODO:Figure out slot*/) && vipData.get(player).displayDev();
    }

    public static boolean renderLegacyDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD) && vipData.get(player).displayLegacyDev();
    }

    public static void refreshVip() {
        if (Minecraft.getInstance().player != null) {
            UUID uuid = Minecraft.getInstance().player.getUUID();

            boolean displayBeta;
            boolean displayDev;
            boolean displayLegacyDev;

            displayBeta = MinejagoClientConfig.get().displayBetaTesterCosmetic.get();
            displayDev = MinejagoClientConfig.get().displayDevTeamCosmetic.get();
            displayLegacyDev = MinejagoClientConfig.get().displayLegacyDevTeamCosmetic.get();

            TommyLibServices.NETWORK.sendToServer(new ServerboundChangeVipDataPayload(uuid, new VipData(MinejagoClientConfig.get().betaTesterCosmeticChoice.get(), displayBeta, displayDev, displayLegacyDev)));
        }
    }

    public static boolean verifyBetaTester(UUID uuid) {
        return SpecialPlayerUtils.getSpecialTypes(VipData.GIST, uuid).contains(SpecialPlayerUtils.BETA_TESTER_KEY);
    }

    public static void setVipData(Player player, VipData data) {
        vipData.put(player, data);
    }

    public static MinejagoBlockEntityWithoutLevelRenderer getBewlr() {
        return bewlr;
    }

    public static void openElementSelectionScreen(List<Holder<Element>> elements, Optional<Integer> giverId) {
        Minecraft.getInstance().setScreen(new ElementSelectionScreen(elements, giverId.isPresent() && ClientUtils.getEntityById(giverId.get()) instanceof ElementGiver giver ? giver : null));
    }

    public static void openScrollScreen(BookViewScreen.BookAccess bookAccess) {
        Minecraft.getInstance().setScreen(new ScrollViewScreen(bookAccess));
    }

    public static void openDragonInventoryScreen(DragonInventoryMenu dragonInventoryMenu, Player player, Dragon dragon, int columns) {
        Minecraft.getInstance().setScreen(new DragonInventoryScreen(dragonInventoryMenu, player.getInventory(), dragon, columns));
    }

    public static void openSkillScreen() {
        Minecraft.getInstance().setScreen(new SkillScreen(MinejagoKeyMappings.OPEN_SKILL_SCREEN.getKey().getValue()));
    }
}
