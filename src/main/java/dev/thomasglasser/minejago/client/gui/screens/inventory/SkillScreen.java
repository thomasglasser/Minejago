package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;

public class SkillScreen extends Screen {
    public static final Component TITLE = Component.translatable("gui.minejago.skill");

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/skill/background.png");
    private static final int BACKGROUND_WIDTH = 146;
    private static final int BACKGROUND_HEIGHT = 134;

    private final int keyCode;

    private int topLeftX;
    private int topLeftY;

    public SkillScreen(int keyCode) {
        super(TITLE);
        this.keyCode = keyCode;
    }

    @Override
    protected void init() {
        super.init();
        this.topLeftX = (this.width - BACKGROUND_WIDTH) / 2;
        this.topLeftY = (this.height - BACKGROUND_HEIGHT) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTitle(guiGraphics);
        SkillDataSet data = minecraft.player.getData(MinejagoAttachmentTypes.SKILL);
        int leftX = topLeftX + 12;
        int topY = topLeftY + 21;
        int rightX = topLeftX + 79;
        int bottomY = topLeftY + 79;
        renderSkill(Skill.AGILITY, data.get(Skill.AGILITY).level(), guiGraphics, leftX, topY);
        renderSkill(Skill.STEALTH, data.get(Skill.STEALTH).level(), guiGraphics, rightX, topY);
        renderSkill(Skill.DEXTERITY, data.get(Skill.DEXTERITY).level(), guiGraphics, leftX, bottomY);
        renderSkill(Skill.PROFICIENCY, data.get(Skill.PROFICIENCY).level(), guiGraphics, rightX, bottomY);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND, topLeftX, topLeftY, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    protected void renderTitle(GuiGraphics guiGraphics) {
        guiGraphics.drawCenteredString(font, getTitle(), topLeftX + (BACKGROUND_WIDTH / 2) - 1, topLeftY + 7, CommonColors.WHITE);
    }

    protected void renderSkill(Skill skill, int level, GuiGraphics guiGraphics, int startX, int startY) {
        guiGraphics.blit(skill.getIcon(), startX, startY, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawCenteredString(font, skill.displayName(), startX + 16, startY + 42, CommonColors.WHITE);
        guiGraphics.drawCenteredString(font, String.valueOf(level), startX + 46, startY + 16, CommonColors.GREEN);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == this.keyCode) {
            minecraft.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
