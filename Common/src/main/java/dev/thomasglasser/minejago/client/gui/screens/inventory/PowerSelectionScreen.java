package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ServerboundSetPowerDataPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PowerSelectionScreen extends Screen
{
    public static final String TITLE = "gui.power_selection.title";

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/power_selection_screen.png");
    private final List<ResourceKey<Power>> powers;
    private final List<PowerButton> shownPowerButtons = new ArrayList<>();
    private final List<PowerButton> allPowerButtons = new ArrayList<>();
    private int shownStart = 0;
    private boolean showLeftArrow = false;
    private boolean showRightArrow = false;

    @Nullable
    Power selectedPower;
    @Nullable
    ResourceKey<Power> selectedPowerKey;
    @Nullable
    PowerButton selectedPowerButton;

    private boolean canScroll = false;
    private boolean scrolling = false;
    private int descStart = 0;

    private final int BACKGROUND_WIDTH = 248;
    private final int BACKGROUND_HEIGHT = 166;
    
    private final int BACKGROUND_VERTICAL_START = BACKGROUND_HEIGHT + 50;

    private Button selectOrDone;

    @Nullable
    private final Wu wuForList;

    public PowerSelectionScreen(Component pTitle, List<ResourceKey<Power>> powers, @Nullable Wu wu) {
        super(pTitle);
        this.powers = powers;
        wuForList = wu;
    }

    @Override
    protected void init() {
        clearWidgets();
        if (minecraft != null && minecraft.level != null)
        {
            Registry<Power> registry = minecraft.level.registryAccess().registry(MinejagoRegistries.POWER).orElseThrow();
            powers.forEach(powerLoc -> {
                Power power1 = registry.get(powerLoc);
                if (power1 != null) {
                    PowerButton button = new PowerButton(((this.width - BACKGROUND_WIDTH) / 2) + 41 + ((powers.indexOf(powerLoc) % 5) * 34), ((this.height - BACKGROUND_VERTICAL_START) / 2) + 7, power1);
                    if (powers.indexOf(powerLoc) < 5) {
                        addRenderableWidget(button);
                        shownPowerButtons.add(button);
                    }
                    allPowerButtons.add(button);
                    showRightArrow = powers.size() > 5;
                }
            });

            selectOrDone = Button.builder(Component.translatable("gui.done"), button ->
            {
                if (selectedPower != null && minecraft.player != null) {
                    if (wuForList != null)
                    {
                        if (wuForList.distanceTo(minecraft.player) > 1.0f)
                            Services.NETWORK.sendToServer(ServerboundSetPowerDataPacket.class, ServerboundSetPowerDataPacket.toBytes(registry.getResourceKey(selectedPower).orElseThrow(), true, wuForList == null ? null : wuForList.getId()));
                    }
                    else
                    {
                        Services.NETWORK.sendToServer(ServerboundSetPowerDataPacket.class, ServerboundSetPowerDataPacket.toBytes(registry.getResourceKey(selectedPower).orElseThrow(), true, wuForList == null ? null : wuForList.getId()));
                    }
                }
                onClose();
            }).build();
            selectOrDone.setX(((this.width - BACKGROUND_WIDTH) / 2) + 50);
            selectOrDone.setY(((this.height - BACKGROUND_VERTICAL_START) / 2) + 175);
            addRenderableWidget(selectOrDone);
        }
        super.init();
    }

    public void renderBase(GuiGraphics guiGraphics) {
        int i = (this.width - BACKGROUND_WIDTH) / 2;
        int j = (this.height - BACKGROUND_VERTICAL_START) / 2;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 256, 256);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderBase(guiGraphics);
        this.renderArrows(guiGraphics, mouseX, mouseY);
        this.renderPowerInfo(guiGraphics);
        this.renderScrollBar(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderSelectOrDoneButton();
    }

    public void renderPowerInfo(GuiGraphics guiGraphics)
    {
        if (selectedPower != null)
        {
            Component title = selectedPower.getFormattedName();

            Power.Display display = selectedPower.getDisplay();
            Component lore = display.lore();
            Component sub = selectedPower.getTagline();
            Component desc = display.description();

            int x = (this.width - BACKGROUND_WIDTH) / 2;
            int y = (this.height - BACKGROUND_VERTICAL_START) / 2;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5f, 1.5f, 1f);
            guiGraphics.pose().translate(-73f, -20f, 0f);
            FormattedCharSequence cutTitle = this.font.split(title, 60).get(0);
            guiGraphics.drawString(this.font, cutTitle, x + 131, y + 49, Optional.ofNullable(title.getStyle().getColor()).orElseThrow().getValue());
            guiGraphics.pose().popPose();
            final int[] j = {0};
            this.font.split(lore, 89).forEach(formattedCharSequence ->
            {
                if (j[0] < 9)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 17, y + 59 + (j[0] * 10), lore.getStyle().getColor() != null ? lore.getStyle().getColor().getValue() : title.getStyle().getColor().getValue());
                j[0]++;
            });
            final int[] k = {0};
            this.font.split(sub, 89).forEach(formattedCharSequence ->
            {
                if (k[0] < 2)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 131, y + 64 + (k[0] * 10), sub.getStyle().getColor() != null ? sub.getStyle().getColor().getValue() : title.getStyle().getColor().getValue());
                k[0]++;
            });
            final int[] l = {0};
            List<FormattedCharSequence> descLines = this.font.split(desc, 89);
            descLines.forEach(formattedCharSequence ->
            {
                if (l[0] < descStart + 7 && l[0] >= descStart)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 131, y + 86 + ((l[0] - descStart) * 10), desc.getStyle().getColor() != null ? desc.getStyle().getColor().getValue() : Optional.ofNullable(ChatFormatting.BLACK.getColor()).orElseThrow());
                l[0]++;
            });
            canScroll = l[0] > 7;
        }
    }

    protected void renderScrollBar(GuiGraphics guiGraphics)
    {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 228;
        int l = j + 48;
        guiGraphics.blit(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"), k, l + (descStart), 232 + (canScroll ? 0 : 12), 0, 12, 15);
    }

    protected void renderArrows(GuiGraphics guiGraphics, int mouseX, int mouseY)
    {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 20;
        int l = j + 12;
        if (showLeftArrow) guiGraphics.blit(BACKGROUND, k, l, 156 + (!insideLeftArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20, 256, 256);
        k = i + 216;
        if (showRightArrow) guiGraphics.blit(BACKGROUND, k, l, 169 + (!insideRightArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20, 256, 256);
    }

    class PowerButton extends AbstractButton {
        private boolean selected;
        private final Power power;
        private final ResourceLocation textureLoc;

        protected PowerButton(int i, int j, Power power) {
            super(i, j, 32, 32, power.getFormattedName());
            textureLoc = power.getIcon();
            this.power = power;
            setTooltip(Tooltip.create(power.getFormattedName()));
        }

        @Override
        public void onPress() {
            shownPowerButtons.forEach(powerButton -> powerButton.setSelected(false));
            setSelected(!selected);
            if (power != selectedPower)
                descStart = 0;
            if (selected && power != null && minecraft != null && minecraft.level != null)
            {
                selectedPower = power;
                selectedPowerKey = minecraft.level.registryAccess().registryOrThrow(MinejagoRegistries.POWER).getResourceKey(selectedPower).orElseThrow();
            }
            selectedPowerButton = this;
        }

        @Override
        public void renderWidget(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
            this.renderButton(guiGraphics, isSelected(), isHovered());
            this.renderIcon(guiGraphics);
        }

        protected void renderButton(GuiGraphics guiGraphics, boolean selected, boolean hoveringOver)
        {
            int u = 0;
            int v = 167;

            if (hoveringOver) v = 199;
            if (!selected) u = 32;

            guiGraphics.blit(BACKGROUND, getX(), getY(), u, v, 32, 32, 256, 256);
        }

        protected void renderIcon(GuiGraphics guiGraphics)
        {
            int x = getX() - 1;
            int y = getY() - 1;
            guiGraphics.blit(textureLoc, x, y, 0, 0, 0, 32, 32, 32, 32);
        }

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
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

    protected boolean insideLeftArrow(double mouseX, double mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 17;
        int l = j + 12;
        int m = k + 18;
        int n = l + 20;
        return mouseX >= (double)k && mouseY >= (double)l && mouseX < (double)m && mouseY < (double)n;
    }

    protected boolean insideRightArrow(double mouseX, double mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 213;
        int l = j + 12;
        int m = k + 18;
        int n = l + 20;
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
            else if (this.insideLeftArrow(mouseX, mouseY) && showLeftArrow)
            {
                previousPage();
            }
            else if (this.insideRightArrow(mouseX, mouseY) && showRightArrow)
            {
                nextPage();
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        if (canScroll && descStart >= 0 && descStart <= 95) {
            if (scrollY < 0) this.descStart++;
            else if (scrollY > 0) this.descStart--;
            if (descStart < 0) descStart = 0;
            else if (descStart > 95) descStart = 95;
            return true;
        } else {
            return false;
        }
    }

    public boolean mouseScrolled(double delta)
    {
        return mouseScrolled(0, 0, 0, delta);
    }

    protected void renderSelectOrDoneButton()
    {
        if (selectedPower == null) selectOrDone.setMessage(Component.translatable("gui.done"));
        else selectOrDone.setMessage(Component.translatable("gui.choose"));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER)
        {
            selectOrDone.onPress();
            return true;
        }
        else if (keyCode == GLFW.GLFW_KEY_LEFT)
            return previousPower();
        else if (keyCode == GLFW.GLFW_KEY_RIGHT)
            return nextPower();
        else if (keyCode == GLFW.GLFW_KEY_PAGE_UP)
            return previousPage();
        else if (keyCode == GLFW.GLFW_KEY_PAGE_DOWN)
            return nextPage();
        else if (keyCode == GLFW.GLFW_KEY_UP)
            return mouseScrolled(10);
        else if (keyCode == GLFW.GLFW_KEY_DOWN)
            return mouseScrolled(-10);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected boolean previousPage()
    {
        if (showLeftArrow)
        {
            shownStart -= 5;
            shownPowerButtons.forEach(this::removeWidget);
            shownPowerButtons.clear();
            for (int i = 0; i < 5; i++) {
                PowerButton b = allPowerButtons.get(shownStart + i);
                addRenderableWidget(b);
                shownPowerButtons.add(b);
            }
        }

        refreshArrows();

        return showLeftArrow;
    }

    protected boolean nextPage()
    {
        if (showRightArrow)
        {
            shownStart += 5;
            shownPowerButtons.forEach(this::removeWidget);
            shownPowerButtons.clear();
            for (int i = 0; i < 5; i++)
            {
                if (shownStart + i >= allPowerButtons.size()) break;
                PowerButton b = allPowerButtons.get(shownStart + i);
                addRenderableWidget(b);
                shownPowerButtons.add(b);
            }
        }

        refreshArrows();

        return showRightArrow;
    }

    protected void refreshArrows()
    {
        showLeftArrow = shownPowerButtons.get(0) != allPowerButtons.get(0);
        showRightArrow = shownPowerButtons.get(shownPowerButtons.size() - 1) != allPowerButtons.get(allPowerButtons.size() - 1);
    }

    protected boolean nextPower()
    {
        if (shownPowerButtons.size() > shownPowerButtons.indexOf(selectedPowerButton) + 1)
        {
            shownPowerButtons.get(shownPowerButtons.indexOf(selectedPowerButton) + 1).onPress();
            return true;
        }
        else if (nextPage())
        {
            shownPowerButtons.get(0).onPress();
            return true;
        }
        return false;
    }

    protected boolean previousPower()
    {
        if (shownPowerButtons.indexOf(selectedPowerButton) - 1 >= 0)
        {
            shownPowerButtons.get(shownPowerButtons.indexOf(selectedPowerButton) - 1).onPress();
            return true;
        }
        else if (previousPage())
        {
            shownPowerButtons.get(0).onPress();
            return true;
        }

        return false;
    }
}
