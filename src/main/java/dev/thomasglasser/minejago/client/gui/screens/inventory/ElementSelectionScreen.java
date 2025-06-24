package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.network.ServerboundSetElementDataPayload;
import dev.thomasglasser.minejago.world.entity.ElementGiver;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class ElementSelectionScreen extends Screen {
    public static final Component GUI_CHOOSE = Component.translatable("gui.choose");
    public static final Component TITLE = Component.translatable("gui.element_selection.title");

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/element_selection.png");
    private static final int BACKGROUND_WIDTH = 248;
    private static final int BACKGROUND_HEIGHT = 166;
    private static final int BACKGROUND_VERTICAL_START = BACKGROUND_HEIGHT + 50;
    private static final int BUTTONS_PER_PAGE = 5;

    private final ReferenceArrayList<Holder<Element>> elements;
    private final ReferenceArrayList<ElementButton> shownElementButtons = new ReferenceArrayList<>();
    private final ReferenceArrayList<ElementButton> allElementButtons = new ReferenceArrayList<>();

    @Nullable
    private final ElementGiver giver;

    private int topLeftX;
    private int topLeftY;
    private Button selectOrDone;

    private int startIndex = 0;
    private int startDescLine = 0;
    private boolean showLeftArrow = false;
    private boolean showRightArrow = false;
    private boolean canScroll = false;
    private boolean scrolling = false;

    @Nullable
    private ElementButton selected;

    public ElementSelectionScreen(List<Holder<Element>> elements, @Nullable ElementGiver giver) {
        super(TITLE);
        this.elements = new ReferenceArrayList<>(elements);
        this.giver = giver;
    }

    @Override
    protected void init() {
        clearWidgets();
        if (minecraft != null && minecraft.level != null) {
            this.topLeftX = (this.width - BACKGROUND_WIDTH) / 2;
            this.topLeftY = (this.height - BACKGROUND_VERTICAL_START) / 2;

            shownElementButtons.clear();
            allElementButtons.clear();
            elements.forEach(element -> {
                ElementButton button = new ElementButton(topLeftX + 41 + ((elements.indexOf(element) % 5) * 34), topLeftY + 7, element);
                if (elements.indexOf(element) < BUTTONS_PER_PAGE) {
                    addRenderableWidget(button);
                    shownElementButtons.add(button);
                }
                allElementButtons.add(button);
                showRightArrow = elements.size() > BUTTONS_PER_PAGE;
            });

            selectOrDone = Button.builder(CommonComponents.GUI_DONE, button -> {
                if (selected != null && minecraft.player != null) {
                    if (giver != null) {
                        if (giver.distanceTo(minecraft.player) > 1) {
                            TommyLibServices.NETWORK.sendToServer(new ServerboundSetElementDataPayload(selected.element, true, Optional.of(giver.getId())));
                        }
                    } else {
                        TommyLibServices.NETWORK.sendToServer(new ServerboundSetElementDataPayload(selected.element, true, Optional.empty()));
                    }
                }
                button.playDownSound(minecraft.getSoundManager());
                onClose();
            }).build();
            selectOrDone.setX(topLeftX + 50);
            selectOrDone.setY(topLeftY + 175);
            addRenderableWidget(selectOrDone);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        this.renderBase(guiGraphics);
        this.renderArrows(guiGraphics, mouseX, mouseY);
        this.renderElementInfo(guiGraphics);
        this.renderScrollBar(guiGraphics);
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    protected void renderBase(GuiGraphics guiGraphics) {
        guiGraphics.blit(BACKGROUND, topLeftX, topLeftY, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    protected void renderArrows(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = topLeftX + 20;
        int y = topLeftY + 12;
        if (showLeftArrow) guiGraphics.blit(BACKGROUND, x, y, 156 + (!insideLeftArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20);
        x += 216;
        if (showRightArrow) guiGraphics.blit(BACKGROUND, x, y, 169 + (!insideRightArrow(mouseX, mouseY) ? 26 : 0), 166, 13, 20);
    }

    public void renderElementInfo(GuiGraphics guiGraphics) {
        if (selected != null && minecraft != null && minecraft.level != null) {
            Holder<Element> element = selected.element;

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(1.5f, 1.5f, 1f);
            guiGraphics.pose().translate(-73f, -20f, 0f);
            Component title = Element.getFormattedName(element);
            FormattedCharSequence cutTitle = this.font.split(title, 60).getFirst();
            int titleColor = Optional.ofNullable(title.getStyle().getColor()).orElse(Element.DEFAULT_COLOR).getValue();
            guiGraphics.drawString(this.font, cutTitle, topLeftX + 131, topLeftY + 49, titleColor, false);
            guiGraphics.pose().popPose();
            guiGraphics.pose().pushPose();
            Element.Display display = element.value().display();
            Component lore = display.lore();
            List<FormattedCharSequence> loreLines = this.font.split(lore, 89);
            int loreColor = Optional.ofNullable(lore.getStyle().getColor()).map(TextColor::getValue).orElse(titleColor);
            int i;
            for (i = 0; i < Math.min(9, loreLines.size()); i++) {
                guiGraphics.drawString(this.font, loreLines.get(i), topLeftX + 17, topLeftY + 59 + (i * 10), loreColor, false);
            }
            Component tagline = element.value().tagline();
            List<FormattedCharSequence> taglineLines = this.font.split(tagline, 89);
            int taglineColor = Optional.ofNullable(tagline.getStyle().getColor()).map(TextColor::getValue).orElse(titleColor);
            for (i = 0; i < Math.min(2, taglineLines.size()); i++) {
                guiGraphics.drawString(this.font, taglineLines.get(i), topLeftX + 131, topLeftY + 64 + (i * 10), taglineColor, false);
            }
            Component desc = display.description();
            List<FormattedCharSequence> descLines = this.font.split(desc, 89);
            int descColor = Optional.ofNullable(desc.getStyle().getColor()).map(TextColor::getValue).orElse(titleColor);
            for (i = 0; i < descLines.size(); i++) {
                if (i < startDescLine + 7 && i >= startDescLine) {
                    guiGraphics.drawString(this.font, descLines.get(i), topLeftX + 131, topLeftY + 86 + ((i - startDescLine) * 10), descColor, false);
                }
            }
            guiGraphics.pose().popPose();
            canScroll = i > 7;
        } else {
            canScroll = false;
        }
    }

    protected void renderScrollBar(GuiGraphics guiGraphics) {
        guiGraphics.blitSprite(this.canScroll ? CreativeModeInventoryScreen.SCROLLER_SPRITE : CreativeModeInventoryScreen.SCROLLER_DISABLED_SPRITE, topLeftX + 228, topLeftY + 48 + this.startDescLine, 12, 15);
    }

    protected boolean insideLeftArrow(double mouseX, double mouseY) {
        int startX = topLeftX + 17;
        int startY = topLeftY + 12;
        int endX = startX + 18;
        int endY = startY + 20;
        return mouseX >= startX && mouseY >= startY && mouseX < endX && mouseY < endY;
    }

    protected boolean insideRightArrow(double mouseX, double mouseY) {
        int startX = topLeftX + 213;
        int startY = topLeftY + 12;
        int endX = startX + 18;
        int endY = startY + 20;
        return mouseX >= startX && mouseY >= startY && mouseX < endX && mouseY < endY;
    }

    protected boolean insideScrollbar(double mouseX, double mouseY) {
        int startX = topLeftX + 227;
        int startY = topLeftY + 47;
        int endX = startX + 13;
        int endY = startY + 110;
        return mouseX >= startX && mouseY >= startY && mouseX < endX && mouseY < endY;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (scrolling && startDescLine >= 0 && startDescLine <= 95) {
            int scrollAmount = topLeftY + 48;
            startDescLine = (int) mouseY - scrollAmount;
            if (startDescLine < 0) {
                startDescLine = 0;
            } else if (startDescLine > 95) {
                startDescLine = 95;
            }
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (insideScrollbar(mouseX, mouseY)) {
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
        if (canScroll && startDescLine >= 0 && startDescLine <= 95) {
            if (scrollY < 0) {
                this.startDescLine++;
            } else if (scrollY > 0) {
                this.startDescLine--;
            }
            if (startDescLine < 0) {
                startDescLine = 0;
            } else if (startDescLine > 95) {
                startDescLine = 95;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean mouseScrolled(double delta) {
        return mouseScrolled(0, 0, 0, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            selectOrDone.onPress();
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_LEFT) {
            return previousElement();
        } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
            return nextElement();
        } else if (keyCode == GLFW.GLFW_KEY_PAGE_UP) {
            if (!previousPage()) {
                firstElement();
            }
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
            if (!nextPage()) {
                lastElement();
            }
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_HOME) {
            firstElement();
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_END) {
            lastElement();
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_UP) {
            return mouseScrolled(10);
        } else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            return mouseScrolled(-10);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected boolean previousPage() {
        if (showLeftArrow) {
            startIndex -= BUTTONS_PER_PAGE;
            shownElementButtons.forEach(this::removeWidget);
            shownElementButtons.clear();
            for (int i = 0; i < BUTTONS_PER_PAGE; i++) {
                ElementButton button = allElementButtons.get(startIndex + i);
                addRenderableWidget(button);
                shownElementButtons.add(button);
            }
            shownElementButtons.getLast().onPress();
        }

        refreshArrows();

        return showLeftArrow;
    }

    protected boolean nextPage() {
        if (showRightArrow) {
            startIndex += BUTTONS_PER_PAGE;
            shownElementButtons.forEach(this::removeWidget);
            shownElementButtons.clear();
            for (int i = 0; i < BUTTONS_PER_PAGE; i++) {
                if (startIndex + i >= allElementButtons.size()) break;
                ElementButton button = allElementButtons.get(startIndex + i);
                addRenderableWidget(button);
                shownElementButtons.add(button);
            }
            shownElementButtons.getFirst().onPress();
        }

        refreshArrows();

        return showRightArrow;
    }

    protected boolean nextElement() {
        if (selected == null || !shownElementButtons.contains(selected)) {
            shownElementButtons.getFirst().onPress();
            return true;
        }
        int next = shownElementButtons.indexOf(selected) + 1;
        if (shownElementButtons.size() > next) {
            shownElementButtons.get(next).onPress();
            return true;
        } else if (nextPage()) {
            shownElementButtons.getFirst().onPress();
            return true;
        }
        return false;
    }

    protected boolean previousElement() {
        if (selected == null || !shownElementButtons.contains(selected)) {
            shownElementButtons.getLast().onPress();
            return true;
        }
        int previous = shownElementButtons.indexOf(selected) - 1;
        if (previous >= 0) {
            shownElementButtons.get(previous).onPress();
            return true;
        } else if (previousPage()) {
            shownElementButtons.getLast().onPress();
            return true;
        }
        return false;
    }

    protected void firstElement() {
        while (showLeftArrow) {
            previousPage();
        }
        shownElementButtons.getFirst().onPress();
    }

    protected void lastElement() {
        while (showRightArrow) {
            nextPage();
        }
        shownElementButtons.getLast().onPress();
    }

    protected void refreshArrows() {
        showLeftArrow = shownElementButtons.getFirst() != allElementButtons.getFirst();
        showRightArrow = shownElementButtons.getLast() != allElementButtons.getLast();
    }

    protected void refreshSelectOrDone() {
        selectOrDone.setMessage(selected != null ? GUI_CHOOSE : CommonComponents.GUI_DONE);
    }

    private class ElementButton extends AbstractButton {
        private static final int ICON_SIZE = 32;

        private boolean isSelected;
        private final Holder<Element> element;
        private final ResourceLocation iconLoc;

        protected ElementButton(int x, int y, Holder<Element> element) {
            super(x, y, ICON_SIZE, ICON_SIZE, Element.getFormattedName(element));
            this.element = element;
            this.iconLoc = Element.getIcon(element);
            setTooltip(Tooltip.create(getMessage()));
        }

        @Override
        public void onPress() {
            boolean select = !isSelected;
            allElementButtons.forEach(elementButton -> elementButton.isSelected = false);
            isSelected = select;
            selected = isSelected ? this : null;
            if (this != selected) {
                startDescLine = 0;
            }
            refreshArrows();
            refreshSelectOrDone();
            playDownSound(minecraft.getSoundManager());
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            this.renderButton(guiGraphics);
            this.renderIcon(guiGraphics);
        }

        protected void renderButton(GuiGraphics guiGraphics) {
            guiGraphics.blit(BACKGROUND, getX(), getY(), isSelected ? 0 : 32, isHovered ? 199 : 167, ICON_SIZE, ICON_SIZE);
        }

        protected void renderIcon(GuiGraphics guiGraphics) {
            int x = getX() - 1;
            int y = getY() - 1;
            guiGraphics.blit(iconLoc, x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.active && this.visible) {
                if (this.isValidClickButton(button)) {
                    if (this.clicked(mouseX, mouseY)) {
                        this.onClick(mouseX, mouseY, button);
                        return true;
                    }
                }

            }
            return false;
        }
    }
}
