package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillScreen extends Screen {
    public static final String TITLE = "gui.minejago.skill";

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/skill/background.png");
    private static final int BACKGROUND_WIDTH = 146;
    private static final int BACKGROUND_HEIGHT = 134;

    public SkillScreen() {
        super(Component.translatable(TITLE));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND, (width - BACKGROUND_WIDTH) / 2, (height - BACKGROUND_HEIGHT) / 2, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 256, 256);
        guiGraphics.drawCenteredString(font, Component.translatable(TITLE), width / 2, height / 2 - 60, 0xFFFFFF);
        SkillDataSet skillData = minecraft.player.getData(MinejagoAttachmentTypes.SKILL);
        guiGraphics.blit(Skill.AGILITY.getIcon(), width / 2 - 61, height / 2 - 46, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(Skill.AGILITY.toLanguageKey()), width / 2 - 60, height / 2 - 3, 0xFFFFFF);
        int agility = skillData.get(Skill.AGILITY).level();
        guiGraphics.drawString(font, String.valueOf(agility), width / 2 - (agility >= 10 ? 21 : 18), height / 2 - 30, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(Skill.STEALTH.getIcon(), width / 2 + 6, height / 2 - 46, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(Skill.STEALTH.toLanguageKey()), width / 2 + 5, height / 2 - 3, 0xFFFFFF);
        int stealth = skillData.get(Skill.STEALTH).level();
        guiGraphics.drawString(font, String.valueOf(stealth), width / 2 + (stealth >= 10 ? 46 : 50), height / 2 - 30, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(Skill.DEXTERITY.getIcon(), width / 2 - 61, height / 2 + 12, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(Skill.DEXTERITY.toLanguageKey()), width / 2 - 67, height / 2 + 54, 0xFFFFFF);
        int dexterity = skillData.get(Skill.DEXTERITY).level();
        guiGraphics.drawString(font, String.valueOf(dexterity), width / 2 - (dexterity >= 10 ? 21 : 18), height / 2 + 28, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(Skill.TOOL_PROFICIENCY.getIcon(), width / 2 + 7, height / 2 + 12, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(Skill.TOOL_PROFICIENCY.toLanguageKey()), width / 2 - 14, height / 2 + 54, 0xFFFFFF);
        int toolProficiency = skillData.get(Skill.TOOL_PROFICIENCY).level();
        guiGraphics.drawString(font, String.valueOf(toolProficiency), width / 2 + (toolProficiency >= 10 ? 46 : 50), height / 2 + 28, ChatFormatting.GREEN.getColor());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == MinejagoKeyMappings.OPEN_SKILL_SCREEN.getKey().getValue()) {
            minecraft.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
