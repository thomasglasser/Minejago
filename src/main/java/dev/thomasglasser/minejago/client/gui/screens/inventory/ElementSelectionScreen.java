package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ServerboundSetElementDataPayload;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class ElementSelectionScreen extends Screen {
    public static final String TITLE = "gui.element_selection.title";
    public static final ResourceLocation SCROLLER_SPRITE = ResourceLocation.withDefaultNamespace("container/creative_inventory/scroller");
    public static final ResourceLocation SCROLLER_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/creative_inventory/scroller_disabled");

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/element_selection.png");
    private static final int BACKGROUND_WIDTH = 248;
    private static final int BACKGROUND_HEIGHT = 166;
    private final int BACKGROUND_VERTICAL_START = BACKGROUND_HEIGHT + 50;

    private final List<ResourceKey<Element>> elements;
    private final List<ElementButton> shownElementButtons = new ArrayList<>();
    private final List<ElementButton> allElementButtons = new ArrayList<>();

    @Nullable
    private final Wu wuForList;

    private int shownStart = 0;
    private boolean showLeftArrow = false;
    private boolean showRightArrow = false;
    private boolean canScroll = false;
    private boolean scrolling = false;
    private int descStart = 0;
    private Button selectOrDone;

    @Nullable
    private Holder<Element> selectedElement;
    @Nullable
    private ElementSelectionScreen.ElementButton selectedElementButton;

    public ElementSelectionScreen(Component pTitle, List<ResourceKey<Element>> elements, @Nullable Wu wu) {
        super(pTitle);
        this.elements = elements;
        wuForList = wu;
    }

    @Override
    protected void init() {
        clearWidgets();
        if (minecraft != null && minecraft.level != null) {
            elements.forEach(key -> {
                minecraft.level.holder(key).ifPresent(holder -> {
                    ElementButton button = new ElementButton(((this.width - BACKGROUND_WIDTH) / 2) + 41 + ((elements.indexOf(key) % 5) * 34), ((this.height - BACKGROUND_VERTICAL_START) / 2) + 7, holder);
                    if (elements.indexOf(key) < 5) {
                        addRenderableWidget(button);
                        shownElementButtons.add(button);
                    }
                    allElementButtons.add(button);
                    showRightArrow = elements.size() > 5;
                });
            });

            selectOrDone = Button.builder(Component.translatable("gui.done"), button -> {
                if (selectedElement != null && minecraft.player != null) {
                    if (wuForList != null) {
                        if (wuForList.distanceTo(minecraft.player) > 1.0f)
                            TommyLibServices.NETWORK.sendToServer(new ServerboundSetElementDataPayload(selectedElement.getKey(), true, Optional.of(wuForList.getId())));
                    } else {
                        TommyLibServices.NETWORK.sendToServer(new ServerboundSetElementDataPayload(selectedElement.getKey(), true, Optional.empty()));
                    }
                }
                onClose();
            }).build();
            selectOrDone.setX(((this.width - BACKGROUND_WIDTH) / 2) + 50);
            selectOrDone.setY(((this.height - BACKGROUND_VERTICAL_START) / 2) + 175);
            addRenderableWidget(selectOrDone);
        }
    }

    public void renderBase(GuiGraphics guiGraphics) {
        int i = (this.width - BACKGROUND_WIDTH) / 2;
        int j = (this.height - BACKGROUND_VERTICAL_START) / 2;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderBase(guiGraphics);
        this.renderArrows(guiGraphics, mouseX, mouseY);
        this.renderElementInfo(guiGraphics);
        this.renderScrollBar(guiGraphics);
        this.renderSelectOrDoneButton();
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    public void renderElementInfo(GuiGraphics guiGraphics) {
        if (selectedElement != null && minecraft != null && minecraft.level != null) {
            Component title = Element.getFormattedName(selectedElement);

            Element.Display display = selectedElement.value().display();
            Component lore = display.lore();
            Component tagline = selectedElement.value().tagline();
            Component desc = display.description();

            int x = (this.width - BACKGROUND_WIDTH) / 2;
            int y = (this.height - BACKGROUND_VERTICAL_START) / 2;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5f, 1.5f, 1f);
            guiGraphics.pose().translate(-73f, -20f, 0f);
            FormattedCharSequence cutTitle = this.font.split(title, 60).get(0);
            guiGraphics.drawString(this.font, cutTitle, x + 131, y + 49, Optional.ofNullable(title.getStyle().getColor()).orElseThrow().getValue(), false);
            guiGraphics.pose().popPose();
            final int[] j = { 0 };
            this.font.split(lore, 89).forEach(formattedCharSequence -> {
                if (j[0] < 9)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 17, y + 59 + (j[0] * 10), lore.getStyle().getColor() != null ? lore.getStyle().getColor().getValue() : title.getStyle().getColor().getValue(), false);
                j[0]++;
            });
            final int[] k = { 0 };
            this.font.split(tagline, 89).forEach(formattedCharSequence -> {
                if (k[0] < 2)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 131, y + 64 + (k[0] * 10), tagline.getStyle().getColor() != null ? tagline.getStyle().getColor().getValue() : title.getStyle().getColor().getValue(), false);
                k[0]++;
            });
            final int[] l = { 0 };
            List<FormattedCharSequence> descLines = this.font.split(desc, 89);
            descLines.forEach(formattedCharSequence -> {
                if (l[0] < descStart + 7 && l[0] >= descStart)
                    guiGraphics.drawString(this.font, formattedCharSequence, x + 131, y + 86 + ((l[0] - descStart) * 10), desc.getStyle().getColor() != null ? desc.getStyle().getColor().getValue() : Optional.ofNullable(ChatFormatting.BLACK.getColor()).orElseThrow(), false);
                l[0]++;
            });
            canScroll = l[0] > 7;
        }
    }

    protected void renderScrollBar(GuiGraphics guiGraphics) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 228;
        int l = j + 48;
        ResourceLocation resourceLocation = this.canScroll ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        guiGraphics.blitSprite(resourceLocation, k, l + this.descStart, 12, 15);
    }

    protected void renderArrows(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 20;
        int l = j + 12;
        if (showLeftArrow) guiGraphics.blit(BACKGROUND, k, l, 156 + (!insideLeftArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20);
        k = i + 216;
        if (showRightArrow) guiGraphics.blit(BACKGROUND, k, l, 169 + (!insideRightArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20);
    }

    class ElementButton extends AbstractButton {
        private static final Registry<Element> REGISTRY = Minecraft.getInstance().level.registryAccess().registryOrThrow(MinejagoRegistries.ELEMENT);

        private boolean selected;
        private final Holder<Element> element;
        private final ResourceLocation textureLoc;

        protected ElementButton(int i, int j, Holder<Element> element) {
            super(i, j, 32, 32, Element.getFormattedName(element));
            this.element = element;
            this.textureLoc = Element.getIcon(element);
            setTooltip(Tooltip.create(Element.getFormattedName(element)));
        }

        @Override
        public void onPress() {
            allElementButtons.forEach(elementButton -> elementButton.setSelected(false));
            setSelected(!selected);
            if (element != selectedElement)
                descStart = 0;
            if (selected && element != null && minecraft != null && minecraft.level != null) {
                selectedElement = element;
            }
            selectedElementButton = this;
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            this.renderButton(guiGraphics, isSelected(), isHovered());
            this.renderIcon(guiGraphics);
        }

        protected void renderButton(GuiGraphics guiGraphics, boolean selected, boolean hoveringOver) {
            int u = 0;
            int v = 167;

            if (hoveringOver) v = 199;
            if (!selected) u = 32;

            guiGraphics.blit(BACKGROUND, getX(), getY(), u, v, 32, 32);
        }

        protected void renderIcon(GuiGraphics guiGraphics) {
            int x = getX() - 1;
            int y = getY() - 1;
            guiGraphics.blit(textureLoc, x, y, 0, 0, 32, 32, 32, 32);
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
        return mouseX >= (double) k && mouseY >= (double) l && mouseX < (double) m && mouseY < (double) n;
    }

    protected boolean insideLeftArrow(double mouseX, double mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 17;
        int l = j + 12;
        int m = k + 18;
        int n = l + 20;
        return mouseX >= (double) k && mouseY >= (double) l && mouseX < (double) m && mouseY < (double) n;
    }

    protected boolean insideRightArrow(double mouseX, double mouseY) {
        int i = ((this.width - BACKGROUND_WIDTH) / 2);
        int j = ((this.height - BACKGROUND_VERTICAL_START) / 2);
        int k = i + 213;
        int l = j + 12;
        int m = k + 18;
        int n = l + 20;
        return mouseX >= (double) k && mouseY >= (double) l && mouseX < (double) m && mouseY < (double) n;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && descStart >= 0 && descStart <= 95) {
            int j = ((this.height - BACKGROUND_VERTICAL_START) / 2) + 48;
            descStart = (int) mouseY - j;
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
            } else if (this.insideLeftArrow(mouseX, mouseY) && showLeftArrow) {
                previousPage();
            } else if (this.insideRightArrow(mouseX, mouseY) && showRightArrow) {
                nextPage();
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
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

    public boolean mouseScrolled(double delta) {
        return mouseScrolled(0, 0, 0, delta);
    }

    protected void renderSelectOrDoneButton() {
        if (selectedElement == null) selectOrDone.setMessage(Component.translatable("gui.done"));
        else selectOrDone.setMessage(Component.translatable("gui.choose"));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            selectOrDone.onPress();
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_LEFT)
            return previousElement();
        else if (keyCode == GLFW.GLFW_KEY_RIGHT)
            return nextElement();
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

    protected boolean previousPage() {
        if (showLeftArrow) {
            shownStart -= 5;
            shownElementButtons.forEach(this::removeWidget);
            shownElementButtons.clear();
            for (int i = 0; i < 5; i++) {
                ElementButton b = allElementButtons.get(shownStart + i);
                addRenderableWidget(b);
                shownElementButtons.add(b);
            }
            shownElementButtons.get(shownElementButtons.size() - 1).onPress();
        }

        refreshArrows();

        return showLeftArrow;
    }

    protected boolean nextPage() {
        if (showRightArrow) {
            shownStart += 5;
            shownElementButtons.forEach(this::removeWidget);
            shownElementButtons.clear();
            for (int i = 0; i < 5; i++) {
                if (shownStart + i >= allElementButtons.size()) break;
                ElementButton b = allElementButtons.get(shownStart + i);
                addRenderableWidget(b);
                shownElementButtons.add(b);
            }
            shownElementButtons.get(0).onPress();
        }

        refreshArrows();

        return showRightArrow;
    }

    protected void refreshArrows() {
        showLeftArrow = shownElementButtons.get(0) != allElementButtons.get(0);
        showRightArrow = shownElementButtons.get(shownElementButtons.size() - 1) != allElementButtons.get(allElementButtons.size() - 1);
    }

    protected boolean nextElement() {
        if (shownElementButtons.size() > shownElementButtons.indexOf(selectedElementButton) + 1) {
            shownElementButtons.get(shownElementButtons.indexOf(selectedElementButton) + 1).onPress();
            return true;
        } else if (nextPage()) {
            shownElementButtons.get(0).onPress();
            return true;
        }
        return false;
    }

    protected boolean previousElement() {
        if (selectedElementButton == null || !shownElementButtons.contains(selectedElementButton)) {
            shownElementButtons.get(shownElementButtons.size() - 1).onPress();
            return true;
        }
        if (shownElementButtons.indexOf(selectedElementButton) - 1 >= 0) {
            shownElementButtons.get(shownElementButtons.indexOf(selectedElementButton) - 1).onPress();
            return true;
        } else if (previousPage()) {
            shownElementButtons.get(shownElementButtons.size() - 1).onPress();
            return true;
        }

        return false;
    }
}
