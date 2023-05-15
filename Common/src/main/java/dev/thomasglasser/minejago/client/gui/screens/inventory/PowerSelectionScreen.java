package dev.thomasglasser.minejago.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ServerboundSetPowerDataPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PowerSelectionScreen extends Screen
{
    public static final String TITLE = "gui.power_selection.title";
    public static final String POWER_GIVEN_KEY = "gui.power_selection.power_given";

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/power_selection_screen.png");
    private List<Power> POWERS = new ArrayList<>();
    private final List<PowerButton> shownPowerButtons = new ArrayList<>();
    private final List<PowerButton> allPowerButtons = new ArrayList<>();

    @Nullable
    Power selectedPower;
    
    private boolean canScroll = false;
    private boolean scrolling = false;
    private List<FormattedCharSequence> descLines = new ArrayList<>();
    private int descStart = 0;
    
    private final int BACKGROUND_WIDTH = 248;
    private final int BACKGROUND_HEIGHT = 166;
    
    private final int BACKGROUND_VERTICAL_START = BACKGROUND_HEIGHT + 50;

    private Button selectOrDone;

    public PowerSelectionScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    protected void init() {
        POWERS.clear();
        if (minecraft != null) POWERS = minecraft.level.registryAccess().registry(MinejagoRegistries.POWER).map(powers -> powers.stream().toList()).orElseGet(List::of);

        clearWidgets();
        POWERS.forEach(power1 -> {
            if (power1.isSelectable())
            {
                PowerButton button = new PowerButton(((this.width - BACKGROUND_WIDTH) / 2) + 41 + ((POWERS.indexOf(power1) % 5) * 34), ((this.height - BACKGROUND_VERTICAL_START) / 2) + 7, power1);
                if (POWERS.indexOf(power1) < 5) {
                    addRenderableWidget(button);
                    shownPowerButtons.add(button);
                }
                allPowerButtons.add(button);
            }
        });

        selectOrDone = Button.builder(Component.translatable("gui.done"), button ->
        {
            if (selectedPower != null & minecraft != null && minecraft.player != null) {
                minecraft.player.displayClientMessage(Component.translatable(POWER_GIVEN_KEY), true);
                Services.NETWORK.sendToServer(ServerboundSetPowerDataPacket.class, ServerboundSetPowerDataPacket.toBytes(minecraft.level.registryAccess().registry(MinejagoRegistries.POWER).get().getResourceKey(selectedPower).get()));
            }
            onClose();
        }).build();
        selectOrDone.setX(((this.width - BACKGROUND_WIDTH) / 2) + 50);
        selectOrDone.setY(((this.height - BACKGROUND_VERTICAL_START) / 2) + 175);
        addRenderableWidget(selectOrDone);

        super.init();
    }

    public void renderBg(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = (this.width - BACKGROUND_WIDTH) / 2;
        int j = (this.height - BACKGROUND_VERTICAL_START) / 2;
        blit(poseStack, i, j, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 256, 256);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBg(poseStack);
        if (selectedPower != null) this.renderPowerInfo(poseStack);
        this.renderScrollBar(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderSelectOrDoneButton();
    }

    public void renderPowerInfo(PoseStack poseStack)
    {
        Component title = selectedPower.getFormattedName();

        Power.Display display = selectedPower.getDisplay();
        Component lore = display.lore();
        Component sub = display.subtitle();
        Component desc = display.description();

        int x = (this.width - BACKGROUND_WIDTH) / 2;
        int y = (this.height - BACKGROUND_VERTICAL_START) / 2;
        poseStack.pushPose();
        poseStack.scale(1.5f, 1.5f, 1f);
        poseStack.translate(-73f, -20f, 0f);
        FormattedCharSequence cutTitle = this.font.split(title, 60).get(0);
        this.font.draw(poseStack, cutTitle, x + 131, y + 49, title.getStyle().getColor().getValue());
        poseStack.popPose();
        final int[] j = {0};
        this.font.split(lore, 89).forEach(formattedCharSequence ->
        {
            if (j[0] < 9) this.font.draw(poseStack, formattedCharSequence, x + 17, y + 59 + (j[0] * 10), lore.getStyle().getColor() != null ? lore.getStyle().getColor().getValue() : title.getStyle().getColor().getValue());
            j[0]++;
        });
        final int[] k = {0};
        this.font.split(sub, 89).forEach(formattedCharSequence ->
        {
            if (k[0] < 2) this.font.draw(poseStack, formattedCharSequence, x + 131, y + 64 + (k[0] * 10), sub.getStyle().getColor() != null ? sub.getStyle().getColor().getValue() : title.getStyle().getColor().getValue());
            k[0]++;
        });
        final int[] l = {0};
        descLines = this.font.split(desc, 89);
        descLines.forEach(formattedCharSequence ->
        {
            if (l[0] < descStart + 7 && l[0] >= descStart) this.font.draw(poseStack, formattedCharSequence, x + 131, y + 86 + ((l[0] - descStart) * 10), desc.getStyle().getColor() != null ? desc.getStyle().getColor().getValue() : ChatFormatting.BLACK.getColor());
            l[0]++;
        });
        canScroll = l[0] > 7;
    }

    protected void renderScrollBar(PoseStack poseStack)
    {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 228;
        int l = j + 48;
        RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
        blit(poseStack, k, l + (descStart), 232 + (canScroll ? 0 : 12), 0, 12, 15);
    }

    class PowerButton extends AbstractButton {
        private boolean selected;
        private Power power;
        private final ResourceLocation textureLoc;

        protected PowerButton(int i, int j, Power power) {
            super(i, j, 32, 32, power.getFormattedName());
            textureLoc = new ResourceLocation(power.getId().getNamespace(), "textures/power/" + power.getId().getPath() + ".png");
            this.power = power;
            setTooltip(Tooltip.create(power.getFormattedName()));
        }

        @Override
        public void onPress() {
            shownPowerButtons.forEach(powerButton -> powerButton.setSelected(false));
            setSelected(!selected);
            if (power != selectedPower)
                descStart = 0;
            if (selected)
            {
                selectedPower = power;
            }
        }

        @Override
        public void renderWidget(PoseStack poseStack, int i, int j, float f) {
            this.renderButton(poseStack, isSelected(), isHovered());
            this.renderIcon(poseStack);
        }

        protected void renderButton(PoseStack poseStack, boolean selected, boolean hoveringOver)
        {
            RenderSystem.setShaderTexture(0, BACKGROUND);

            int u = 0;
            int v = 167;

            if (hoveringOver) v = 199;
            if (!selected) u = 32;

            blit(poseStack, getX(), getY(), u, v, 32, 32, 256, 256);
        }

        protected void renderIcon(PoseStack poseStack)
        {
            RenderSystem.setShaderTexture(0, textureLoc);
            int x = getX() - 1;
            int y = getY() - 1;
            blit(poseStack, x, y, 0, 0, 0, 32, 32, 32, 32);
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    protected boolean insideScrollbar(double mouseX, double mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 227;
        int l = j + 47;
        int m = i + 240;
        int n = j + 157;
        return mouseX >= (double)k && mouseY >= (double)l && mouseX < (double)m && mouseY < (double)n;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && descStart >= 0 && descStart <= 95) {
            int j = ((this.height - BACKGROUND_VERTICAL_START) / 2) + 48;
            descStart = (int)mouseY - j;
            if (descStart < 0) descStart = 0;
            else if (descStart > 95) descStart = 95;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (this.insideScrollbar(mouseX, mouseY)) {
                this.scrolling = this.canScroll;
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (canScroll && descStart >= 0 && descStart <= 95) {
            if (delta < 0) this.descStart++;
            else if (delta > 0) this.descStart--;
            if (descStart < 0) descStart = 0;
            else if (descStart > 95) descStart = 95;
            return true;
        } else {
            return false;
        }
    }

    protected void renderSelectOrDoneButton()
    {
        if (selectedPower == null) selectOrDone.setMessage(Component.translatable("gui.done"));
        else selectOrDone.setMessage(Component.translatable("gui.choose"));
    }
}
