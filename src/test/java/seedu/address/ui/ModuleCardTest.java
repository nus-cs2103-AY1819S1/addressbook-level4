package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

import org.junit.Test;

import guitests.guihandles.ModuleCardHandle;
import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

public class ModuleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Module moduleWithNoTags = new ModuleBuilder().withTags(new String[0]).build();
        ModuleCard moduleCard = new ModuleCard(moduleWithNoTags, 1);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithNoTags, 1);

        // with tags
        Module moduleWithTags = new ModuleBuilder().build();
        moduleCard = new ModuleCard(moduleWithTags, 2);
        uiPartRule.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithTags, 2);
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(module, 0);

        // same person, same index -> returns true
        ModuleCard copy = new ModuleCard(module, 0);
        assertTrue(moduleCard.equals(copy));

        // same object -> returns true
        assertTrue(moduleCard.equals(moduleCard));

        // null -> returns false
        assertFalse(moduleCard.equals(null));

        // different types -> returns false
        assertFalse(moduleCard.equals(0));

        // different person, same index -> returns false
        Module differentModule = new ModuleBuilder().withModuleTitle("differentTitle").build();
        assertFalse(moduleCard.equals(new ModuleCard(differentModule, 0)));

        // same person, different index -> returns false
        assertFalse(moduleCard.equals(new ModuleCard(module, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ModuleCard moduleCard, Module expectedModule, int expectedId) {
        guiRobot.pauseForHuman();

        ModuleCardHandle moduleCardHandle = new ModuleCardHandle(moduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", moduleCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysModule(expectedModule, moduleCardHandle);
    }

}
