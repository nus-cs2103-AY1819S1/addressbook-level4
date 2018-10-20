package seedu.address.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import seedu.address.model.tag.Tag;

public class GroupCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Tag group = new Tag("Test");
        GroupCard groupCard = new GroupCard(group, 1);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, group, 1);
    }

    @Test
    public void equals() {
        Tag group = new Tag("Test");
        GroupCard groupCard = new GroupCard(group, 0);

        // same group, same index -> returns true
        GroupCard copy = new GroupCard(group, 0);
        assertTrue(groupCard.equals(copy));

        // same object -> returns true
        assertTrue(groupCard.equals(groupCard));

        // null -> returns false
        assertFalse(groupCard.equals(null));

        // differet types -> return false
        assertFalse(groupCard.equals(0));

        // different group, same index -> return false
        Tag differentGroup = new Tag("tseT");
        assertFalse(groupCard.equals(new GroupCard(differentGroup, 0)));

        // same group, different index -> returns false
        assertFalse(groupCard.equals(new GroupCard(group, 1)));
    }

    /**
     * Asserts that {@code groupCard} displays the details of {@code expectedGroup} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GroupCard groupCard, Tag expectedGroup, int expectedId) {
        guiRobot.pauseForHuman();

        GroupCardHandle groupCardHandle = new GroupCardHandle(groupCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", groupCardHandle.getId());

        // verify group details are displayed correctly
        assertCardDisplaysGroup(expectedGroup, groupCardHandle);
    }
}
