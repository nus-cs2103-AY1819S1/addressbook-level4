package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysIssue;

import org.junit.Test;

import guitests.guihandles.IssueCardHandle;
import seedu.address.model.Issue;
import seedu.address.testutil.IssueBuilder;

public class IssueCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Issue issueWithNoTags = new IssueBuilder().withTags(new String[0]).build();
        IssueCard issueCard = new IssueCard(issueWithNoTags, 1);
        uiPartRule.setUiPart(issueCard);
        assertCardDisplay(issueCard, issueWithNoTags, 1);

        // with tags
        Issue issueWithTags = new IssueBuilder().build();
        issueCard = new IssueCard(issueWithTags, 2);
        uiPartRule.setUiPart(issueCard);
        assertCardDisplay(issueCard, issueWithTags, 2);
    }

    @Test
    public void equals() {
        Issue issue = new IssueBuilder().build();
        IssueCard issueCard = new IssueCard(issue, 0);

        // same issue, same index -> returns true
        IssueCard copy = new IssueCard(issue, 0);
        assertTrue(issueCard.equals(copy));

        // same object -> returns true
        assertTrue(issueCard.equals(issueCard));

        // null -> returns false
        assertFalse(issueCard.equals(null));

        // different types -> returns false
        assertFalse(issueCard.equals(0));

        // different issue, same index -> returns false
        Issue differentIssue = new IssueBuilder().withStatement("differentName").build();
        assertFalse(issueCard.equals(new IssueCard(differentIssue, 0)));

        // same issue, different index -> returns false
        assertFalse(issueCard.equals(new IssueCard(issue, 1)));
    }

    /**
     * Asserts that {@code issueCard} displays the details of {@code expectedIssue} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(IssueCard issueCard, Issue expectedIssue, int expectedId) {
        guiRobot.pauseForHuman();

        IssueCardHandle issueCardHandle = new IssueCardHandle(issueCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", issueCardHandle.getId());

        // verify issue details are displayed correctly
        assertCardDisplaysIssue(expectedIssue, issueCardHandle);
    }
}
