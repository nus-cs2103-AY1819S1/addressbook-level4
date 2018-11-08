package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysAssignment;

import org.junit.Test;

import guitests.guihandles.AssignmentCardHandle;
import seedu.address.model.project.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class AssignmentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Assignment assignmentWithNoTags = new AssignmentBuilder().build();
        AssignmentCard assignmentCard = new AssignmentCard(assignmentWithNoTags, 1);
        uiPartRule.setUiPart(assignmentCard);
        assertCardDisplay(assignmentCard, assignmentWithNoTags, 1);

        // with tags
        Assignment assignmentWithTags = new AssignmentBuilder().build();
        assignmentCard = new AssignmentCard(assignmentWithTags, 2);
        uiPartRule.setUiPart(assignmentCard);
        assertCardDisplay(assignmentCard, assignmentWithTags, 2);
    }

    @Test
    public void equals() {
        Assignment assignment = new AssignmentBuilder().build();
        AssignmentCard assignmentCard = new AssignmentCard(assignment, 0);

        // same assignment, same index -> returns true
        AssignmentCard copy = new AssignmentCard(assignment, 0);
        assertTrue(assignmentCard.equals(copy));

        // same object -> returns true
        assertTrue(assignmentCard.equals(assignmentCard));

        // null -> returns false
        assertFalse(assignmentCard.equals(null));

        // different types -> returns false
        assertFalse(assignmentCard.equals(0));

        // different assignment, same index -> returns false
        Assignment differentAssignment = new AssignmentBuilder().withAssignmentName("differentName").build();
        assertFalse(assignmentCard.equals(new AssignmentCard(differentAssignment, 0)));

        // same assignment, different index -> returns false
        assertFalse(assignmentCard.equals(new AssignmentCard(assignment, 1)));

        // same assignment name, different author -> return false
        Assignment differentAssignmentAuthor = new AssignmentBuilder().withAuthor("differentName").build();
        assertFalse(assignmentCard.equals(new AssignmentCard(differentAssignmentAuthor, 0)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(AssignmentCard assignmentCard, Assignment expectedAssignment, int expectedId) {
        guiRobot.pauseForHuman();

        AssignmentCardHandle assignmentCardHandle = new AssignmentCardHandle(assignmentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", assignmentCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysAssignment(expectedAssignment, assignmentCardHandle);
    }
}
