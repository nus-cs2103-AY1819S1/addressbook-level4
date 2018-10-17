package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.IssueCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.Issue;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(IssueCardHandle expectedCard, IssueCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getSolutions(), actualCard.getSolutions());
        assertEquals(expectedCard.getStatement(), actualCard.getStatement());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedIssue}.
     */
    public static void assertCardDisplaysIssue(Issue expectedIssue, IssueCardHandle actualCard) {
        assertEquals(expectedIssue.getStatement().issue, actualCard.getStatement());
        assertEquals(expectedIssue.getDescription().value, actualCard.getDescription());
        assertEquals(expectedIssue.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code issues} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Issue... issues) {
        for (int i = 0; i < issues.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysIssue(issues[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code issues} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Issue> issues) {
        assertListMatching(personListPanelHandle, issues.toArray(new Issue[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
