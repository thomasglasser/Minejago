package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.world.item.ItemStack;

public class PowersEntry extends IndexModeEntryProvider {
    private static final String ID = "powers";

    public PowersEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("example", () -> BookTextPageModel.create()
                .withAnchor("example")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                An example JSON power is shown below:
                \\
                ```json
                {
                  "border_particle": "minejago:sparks",
                  "display": {
                    "description": {
                      "translate": "power.minejago.fire.description"
                    },
                    "lore": {
                      "translate": "power.minejago.fire.lore"
                    }
                  },
                  "has_sets": true,
                  "id": "minejago:fire",
                  "power_color": "#B90E04",
                  "tagline": {
                    "translate": "power.minejago.fire.tagline"
                  }
                }
                ```
                \\
                Now, let's break it down.
                """);

        page("format_1", () -> BookTextPageModel.create()
                .withAnchor("format_1")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                The "border_particle" field specifies the particle to display around the player when spinjitzu is active.
                \\
                The "display" field specifies the description, lore, and tagline of the power for the power selection screen and power discovery.
                \\
                The "has_sets" field specifies if the power should generate gi sets.
                """);

        page("format_2", () -> BookTextPageModel.create()
                .withAnchor("format_2")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                The "id" field specifies the unique identifier for the power.
                \\
                The "power_color" field specifies the color of the power for use in text and spinjitzu.
                \\
                The "tagline" field specifies the tagline of the power for the power selection screen and power discovery.
                """);

        page("other_steps_1", () -> BookTextPageModel.create()
                .withAnchor("other_steps_1")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Other Steps");
        add(context().pageText(), """
                Once you've added the power file to your datapack,
                you'll need to add some other files with it:
                - If you want your power to work with modded behaviors,
                such as golden weapon compatibility,
                you will need to add it to the tag for that behavior.
                \\
                You will also need a resource pack for client assets with the following files:
                - A language file at "assets/<namespace>/lang/<language>.json" with the following entries:
                    - "power.<namespace>.<power_id>" for the power's name
                    - "power.<namespace>.<power_id>.description" for the power's description
                    - "power.<namespace>.<power_id>.lore" for the power's lore
                    - "power.<namespace>.<power_id>.tagline" for the power's tagline
                """);

        page("other_steps_2", () -> BookTextPageModel.create()
                .withAnchor("other_steps_2")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Other Steps");
        add(context().pageText(), """
                - Model files for the gi sets in hand at "assets/<namespace>/models/item/minejago_armor/<power_id>_training_gi_<piece>.json" for the hood, jacket, pants, and boots.
                (For example, "assets/minejago/models/item/minejago_armor/fire_training_gi_jacket.json")
                - Texture files for the gi sets in hand at "assets/<namespace>/textures/item/minejago_armor/<power_id>_training_gi_<piece>.png" for the hood, jacket, pants, and boots.
                - Texture files for the gi sets on the player at "assets/<namespace>/textures/models/armor/minejago_armor/training_gi_<power_id>.png"
                Refer to existing gi sets for the correct texture layout.
                - A texture file for the power's icon at "assets/<namespace>/textures/power/<power_id>.png"
                """);
    }

    @Override
    protected String entryName() {
        return "Powers";
    }

    @Override
    protected String entryDescription() {
        return """
                Powers are data-driven, requiring a datapack and resource pack with a few simple files.
                A generator for these powers is available [here](https://jsons.thomasglasser.dev/minejago/power/).
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        ItemStack icon = MinejagoArmors.TRAINING_GI_SET.CHEST.toStack();
        icon.set(MinejagoDataComponents.POWER, MinejagoPowers.FIRE);
        return BookIconModel.create(icon);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
