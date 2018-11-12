package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_VOLUNTEERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalVolunteers.CARL;
import static seedu.address.testutil.TypicalVolunteers.ELLE;
import static seedu.address.testutil.TypicalVolunteers.FIONA;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteerAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalVolunteerAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        NameContainsKeywordsPredicate firstVolunteerPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondVolunteerPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findFirstVolunteerCommand = new FindCommand(firstVolunteerPredicate);
        FindCommand findSecondVolunteerCommand = new FindCommand(secondVolunteerPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        // same object -> returns true
        assertTrue(findFirstVolunteerCommand.equals(findFirstVolunteerCommand));

        // same values -> returns true
        FindCommand findFirstVolunteerCommandCopy = new FindCommand(firstVolunteerPredicate);
        assertTrue(findFirstVolunteerCommand.equals(findFirstVolunteerCommandCopy));

        // different types -> returns false
        assertFalse(findFirstVolunteerCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstVolunteerCommand.equals(null));

        // different volunteer -> returns false
        assertFalse(findFirstVolunteerCommand.equals(findSecondVolunteerCommand));
    }

    @Test
    public void execute_zeroKeywords_noVolunteerFound() {
        String expectedMessage = String.format(MESSAGE_VOLUNTEERS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredVolunteerList());
    }

    @Test
    public void execute_multipleKeywords_multipleVolunteersFound() {
        String expectedMessage = String.format(MESSAGE_VOLUNTEERS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredVolunteerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredVolunteerList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareVolunteerPredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
