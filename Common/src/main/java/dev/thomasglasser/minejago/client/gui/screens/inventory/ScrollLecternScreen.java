package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.world.inventory.ScrollLecternMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

public class ScrollLecternScreen extends ScrollViewScreen implements MenuAccess<ScrollLecternMenu> {
    private final ScrollLecternMenu menu;
    private final ContainerListener listener = new ContainerListener() {
        /**
         * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
         * contents of that slot.
         */
        public void slotChanged(AbstractContainerMenu p_99054_, int p_99055_, ItemStack p_99056_) {
            bookChanged();
        }

        public void dataChanged(AbstractContainerMenu p_169772_, int p_169773_, int p_169774_) {
            if (p_169773_ == 0) {
                pageChanged();
            }

        }
    };

    public ScrollLecternScreen(ScrollLecternMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super();
        this.menu = pMenu;
    }

    public ScrollLecternMenu getMenu() {
        return this.menu;
    }

    protected void init() {
        super.init();
        this.menu.addSlotListener(this.listener);
    }

    public void onClose() {
        this.minecraft.player.closeContainer();
        super.onClose();
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this.listener);
    }

    protected void createMenuControls() {
        if (this.minecraft.player.mayBuild()) {
            this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p_99033_) -> {
                this.onClose();
            }).bounds(this.width / 2 - 100, 196, 98, 20).build());
            this.addRenderableWidget(Button.builder(Component.translatable("lectern.take_scroll"), (p_99024_) -> {
                this.sendButtonClick(3);
            }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        } else {
            super.createMenuControls();
        }

    }

    /**
     * Moves the display back one page
     */
    protected void pageBack() {
        this.sendButtonClick(1);
    }

    /**
     * Moves the display forward one page
     */
    protected void pageForward() {
        this.sendButtonClick(2);
    }

    /**
     * I'm not sure why this exists. The function it calls is public and does all the work.
     */
    protected boolean forcePage(int pPageNum) {
        if (pPageNum != this.menu.getPage()) {
            this.sendButtonClick(100 + pPageNum);
            return true;
        } else {
            return false;
        }
    }

    private void sendButtonClick(int pPageData) {
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, pPageData);
    }

    public boolean isPauseScreen() {
        return false;
    }

    void bookChanged() {
        ItemStack itemstack = this.menu.getBook();
        this.setBookAccess(ScrollViewScreen.fromItem(itemstack));
    }

    void pageChanged() {
        this.setPage(this.menu.getPage());
    }

    protected void closeScreen() {
        this.minecraft.player.closeContainer();
    }
}