package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skill.MinejagoSkills;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillScreen extends Screen {
    public static final String TITLE = "gui.minejago.skill";

    private static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/skill/background.png");
    private static final UnaryOperator<ResourceLocation> ICON = (skill) -> skill.withPrefix("textures/gui/skill/").withSuffix(".png");
    private static final int BACKGROUND_WIDTH = 146;
    private static final int BACKGROUND_HEIGHT = 134;

    public SkillScreen() {
        super(Component.translatable(TITLE));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND, (width - BACKGROUND_WIDTH) / 2, (height - BACKGROUND_HEIGHT) / 2, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 256, 256);
        guiGraphics.drawCenteredString(font, Component.translatable(TITLE), width / 2, height / 2 - 60, 0xFFFFFF);
        SkillDataSet skillData = minecraft.player.getData(MinejagoAttachmentTypes.SKILL);
        guiGraphics.blit(RenderType::guiTextured, ICON.apply(MinejagoSkills.AGILITY), width / 2 - 61, height / 2 - 46, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(MinejagoSkills.AGILITY.toLanguageKey("skill")), width / 2 - 60, height / 2 - 3, 0xFFFFFF);
        int agility = skillData.get(MinejagoSkills.AGILITY).level();
        guiGraphics.drawString(font, String.valueOf(agility), width / 2 - (agility >= 10 ? 21 : 18), height / 2 - 30, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(RenderType::guiTextured, ICON.apply(MinejagoSkills.STEALTH), width / 2 + 6, height / 2 - 46, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(MinejagoSkills.STEALTH.toLanguageKey("skill")), width / 2 + 5, height / 2 - 3, 0xFFFFFF);
        int stealth = skillData.get(MinejagoSkills.STEALTH).level();
        guiGraphics.drawString(font, String.valueOf(stealth), width / 2 + (stealth >= 10 ? 46 : 50), height / 2 - 30, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(RenderType::guiTextured, ICON.apply(MinejagoSkills.DEXTERITY), width / 2 - 61, height / 2 + 12, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(MinejagoSkills.DEXTERITY.toLanguageKey("skill")), width / 2 - 67, height / 2 + 54, 0xFFFFFF);
        int dexterity = skillData.get(MinejagoSkills.DEXTERITY).level();
        guiGraphics.drawString(font, String.valueOf(dexterity), width / 2 - (dexterity >= 10 ? 21 : 18), height / 2 + 28, ChatFormatting.GREEN.getColor());
        guiGraphics.blit(RenderType::guiTextured, ICON.apply(MinejagoSkills.TOOL_PROFICIENCY), width / 2 + 7, height / 2 + 12, 0, 0, 32, 39, 32, 39);
        guiGraphics.drawString(font, Component.translatable(MinejagoSkills.TOOL_PROFICIENCY.toLanguageKey("skill")), width / 2 - 14, height / 2 + 54, 0xFFFFFF);
        int toolProficiency = skillData.get(MinejagoSkills.TOOL_PROFICIENCY).level();
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
