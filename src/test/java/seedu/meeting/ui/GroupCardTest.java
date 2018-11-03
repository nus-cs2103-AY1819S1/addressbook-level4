package seedu.meeting.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;

// @@author jeffreyooi
public class GroupCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Group group = new Group(new Title("Test"));
        GroupCard groupCard = new GroupCard(group, 1);
        uiPartRule.setUiPart(groupCard);
        assertCardDisplay(groupCard, group, 1);
    }

    @Test
    public void equals() {
        Group group = new Group(new Title("Test"));
        GroupCard groupCard = new GroupCard(group, 0);

        // same group, same index -> returns true
        GroupCard copy = new GroupCard(group, 0);
        assertTrue(groupCard.equals(copy));

        // same object -> returns true
        assertTrue(groupCard.equals(groupCard));

        // null -> returns false
        assertFalse(groupCard.equals(null));

        // different types -> return false
        assertFalse(groupCard.equals(0));

        // different group, same index -> return false
        Group differentGroup = new Group(new Title("tseT"));
        assertFalse(groupCard.equals(new GroupCard(differentGroup, 0)));

        // same group, different index -> returns false
        assertFalse(groupCard.equals(new GroupCard(group, 1)));
    }

    /**
     * Asserts that {@code groupCard} displays the details of {@code expectedGroup} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GroupCard groupCard, Group expectedGroup, int expectedId) {
        guiRobot.pauseForHuman();

        GroupCardHandle groupCardHandle = new GroupCardHandle(groupCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", groupCardHandle.getId());

        // verify group details are displayed correctly
        assertCardDisplaysGroup(expectedGroup, groupCardHandle);
    }
}
