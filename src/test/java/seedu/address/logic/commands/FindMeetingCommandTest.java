package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_MEETINGS_FOUND_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetingBook.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalMeetings.DISCUSSION;
import static seedu.address.testutil.TypicalMeetings.REHEARSAL;
import static seedu.address.testutil.TypicalMeetings.URGENT;
import static seedu.address.testutil.TypicalMeetings.WEEKLY;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.util.MeetingTitleContainsKeywordsPredicate;

public class FindMeetingCommandTest {

    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        MeetingTitleContainsKeywordsPredicate firstPredicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("first"), Collections.emptyList());
        MeetingTitleContainsKeywordsPredicate secondPredicate = new MeetingTitleContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("second"), Collections.emptyList());

        FindMeetingCommand findFirstCommand = new FindMeetingCommand(firstPredicate);
        FindMeetingCommand findSecondCommand = new FindMeetingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMeetingCommand findFirstCommandCopy = new FindMeetingCommand(firstPredicate);
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
        String expectedMessage = String.format(MESSAGE_MEETINGS_FOUND_OVERVIEW, 0);
        MeetingTitleContainsKeywordsPredicate predicate = preparePredicate(" ", " ", " ");
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleKeywords_somePrefix() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_FOUND_OVERVIEW, 2);
        MeetingTitleContainsKeywordsPredicate predicate = preparePredicate(" ",
            "urgent features", " ");
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(URGENT, DISCUSSION), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleKeywords_allPrefix() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_FOUND_OVERVIEW, 1);
        MeetingTitleContainsKeywordsPredicate predicate = preparePredicate("meetup", " ", " ");
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(WEEKLY), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleKeywords_nonePrefix() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_FOUND_OVERVIEW, 2);
        MeetingTitleContainsKeywordsPredicate predicate = preparePredicate(" ", " ", "settle affair");
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(WEEKLY, REHEARSAL), model.getFilteredMeetingList());
    }

    /**
     * Parses {@code userInput} into a {@code MeetingTitleContainsKeywordsPredicate}.
     */
    private MeetingTitleContainsKeywordsPredicate preparePredicate(String userInputForAllPrefix,
                                                                   String userInputForSomePrefix,
                                                                   String userInputForNonePrefix) {
        return new MeetingTitleContainsKeywordsPredicate(Arrays.asList(userInputForAllPrefix.split("\\s+")),
            Arrays.asList(userInputForSomePrefix.split("\\s+")),
            Arrays.asList(userInputForNonePrefix.split("\\s+")));
    }
}
