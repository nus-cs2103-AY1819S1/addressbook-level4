package seedu.meeting.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.commons.core.Messages.MESSAGE_GROUPS_FOUND_OVERVIEW;
import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.NUS_BASKETBALL;
import static seedu.meeting.testutil.TypicalGroups.NUS_COMPUTING;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindGroupCommand}.
 */
public class FindGroupCommandTest {
    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        GroupTitleContainsKeywordsPredicate firstPredicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("first"), Collections.emptyList());
        GroupTitleContainsKeywordsPredicate secondPredicate = new GroupTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("second"), Collections.emptyList());

        FindGroupCommand findFirstCommand = new FindGroupCommand(firstPredicate);
        FindGroupCommand findSecondCommand = new FindGroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindGroupCommand findFirstCommandCopy = new FindGroupCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertNotNull(findFirstCommand);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGroupFound() {
        String expectedMessage = String.format(MESSAGE_GROUPS_FOUND_OVERVIEW, 0);
        GroupTitleContainsKeywordsPredicate predicate = preparePredicate(" ", " ", " ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGroupList());
    }

    @Test
    public void execute_multipleKeywords_somePrefix() {
        String expectedMessage = String.format(MESSAGE_GROUPS_FOUND_OVERVIEW, 3);
        GroupTitleContainsKeywordsPredicate predicate = preparePredicate(" ",
            "Team Clique", " ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROJECT_2103T, GROUP_2101, NUS_BASKETBALL), model.getFilteredGroupList());
    }

    @Test
    public void execute_multipleKeywords_allPrefix() {
        String expectedMessage = String.format(MESSAGE_GROUPS_FOUND_OVERVIEW, 2);
        GroupTitleContainsKeywordsPredicate predicate = preparePredicate("Project Team", " ", " ");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PROJECT_2103T, GROUP_2101), model.getFilteredGroupList());
    }

    @Test
    public void execute_multipleKeywords_nonePrefix() {
        String expectedMessage = String.format(MESSAGE_GROUPS_FOUND_OVERVIEW, 2);
        GroupTitleContainsKeywordsPredicate predicate = preparePredicate(" ", " ", "Project");
        FindGroupCommand command = new FindGroupCommand(predicate);
        expectedModel.updateFilteredGroupList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NUS_COMPUTING, NUS_BASKETBALL), model.getFilteredGroupList());
    }

    /**
     * Parses {@code userInput} into a {@code GroupTitleContainsKeywordsPredicate}.
     */
    private GroupTitleContainsKeywordsPredicate preparePredicate(String userInputForAllPrefix,
                                                                 String userInputForSomePrefix,
                                                                 String userInputForNonePrefix) {
        return new GroupTitleContainsKeywordsPredicate(Arrays.asList(userInputForAllPrefix.split("\\s+")),
            Arrays.asList(userInputForSomePrefix.split("\\s+")),
            Arrays.asList(userInputForNonePrefix.split("\\s+")));
    }
}
