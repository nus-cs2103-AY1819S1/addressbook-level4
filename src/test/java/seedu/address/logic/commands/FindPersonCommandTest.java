package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_FOUND_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetingBook.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {
    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        PersonNameContainsKeywordsPredicate firstPredicate = new PersonNameContainsKeywordsPredicate(
                    Collections.emptyList(), Collections.singletonList("first"), Collections.emptyList());
        PersonNameContainsKeywordsPredicate secondPredicate = new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Collections.singletonList("second"), Collections.emptyList());

        FindPersonCommand findFirstCommand = new FindPersonCommand(firstPredicate);
        FindPersonCommand findSecondCommand = new FindPersonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertNotNull(findFirstCommand);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 0);
        PersonNameContainsKeywordsPredicate predicate = preparePredicate(" ", " ", " ");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_somePrefix() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 3);
        PersonNameContainsKeywordsPredicate predicate = preparePredicate(" ",
                "Kurz Elle Kunz", " ");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_allPrefix() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 2);
        PersonNameContainsKeywordsPredicate predicate = preparePredicate(KEYWORD_MATCHING_MEIER, " ", " ");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_nonePrefix() {
        String expectedMessage = String.format(MESSAGE_PERSONS_FOUND_OVERVIEW, 5);
        PersonNameContainsKeywordsPredicate predicate = preparePredicate(" ", " ", KEYWORD_MATCHING_MEIER);
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PersonNameContainsKeywordsPredicate}.
     */
    private PersonNameContainsKeywordsPredicate preparePredicate(String userInputForAllPrefix,
                                                                 String userInputForSomePrefix,
                                                                 String userInputForNonePrefix) {
        return new PersonNameContainsKeywordsPredicate(Arrays.asList(userInputForAllPrefix.split("\\s+")),
            Arrays.asList(userInputForSomePrefix.split("\\s+")),
            Arrays.asList(userInputForNonePrefix.split("\\s+")));
    }
}
