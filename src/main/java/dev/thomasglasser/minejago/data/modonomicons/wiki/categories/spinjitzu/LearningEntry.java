package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.resources.ResourceLocation;

public class LearningEntry extends IndexModeEntryProvider {
    private static final String ID = "learning";
    public static final ResourceLocation COURSE_LOCATION = WikiBookSubProvider.wikiTexture("spinjitzu/learning/course.png");

    public LearningEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("course", () -> BookImagePageModel.create()
                .withAnchor("course")
                .withImages(COURSE_LOCATION)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "The Spinjitzu Course");
        add(context().pageText(), """
                The Spinjitzu course is a series of challenges designed to help you master Spinjitzu.
                It is located in the center of the [Monastery of Spinjitzu](entry://locations/monastery_of_spinjitzu).,
                and is the first step to becoming a Spinjitzu Master.
                """);

        page("training", () -> BookTextPageModel.create()
                .withAnchor("training")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Training");
        add(context().pageText(), """
                To begin training, you must first activate the course.
                You are able to run through the course as many times as you like,
                but you must complete it in time with [Wu](entry://characters/ninja_team@wu) tracking you to become a Spinjitzu Master.
                """);

        page("testing", () -> BookTextPageModel.create()
                .withAnchor("testing")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Testing");
        add(context().pageText(), """
                When you feel you are ready, interact with [Wu](entry://characters/ninja_team@wu) to begin the test.
                He will sit down at his teapot and make tea while you run through the course.
                If you complete the course and interact with him before he finishes his tea, you will unlock Spinjitzu.
                If you run out of time, you will have to try again the next day.
                \\
                It is possible to interrupt [Wu](entry://characters/ninja_team@wu)'s tea-making process by hitting his cup with your hand or a projectile as he lifts it.
                This will cause him to begin pouring tea again, giving you more time to complete the course.
                """);

        page("configuration", () -> BookTextPageModel.create()
                .withAnchor("configuration")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Configuration");
        add(context().pageText(), """
                There are currently four configuration options for the Spinjitzu course:
                - "require_course_completion" (default: true) - Whether the course must be completed to unlock Spinjitzu.
                - "course_time_limit" (default: 30) - The time limit in seconds to complete the course.
                - "course_radius" (default: 64) - The radius in blocks that [Wu](entry://characters/ninja_team@wu) will check to find a teapot and include course elements
                - "course_speed" (default: 0.5) - The speed at which the course elements rotate and move
                """);
    }

    @Override
    protected String entryName() {
        return "Learning";
    }

    @Override
    protected String entryDescription() {
        return "The steps to mastering Spinjitzu";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
