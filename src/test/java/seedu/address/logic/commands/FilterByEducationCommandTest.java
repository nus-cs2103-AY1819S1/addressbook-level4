package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EducationFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class FilterByEducationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = new String("Sec");
        String second = new String(" ");


        FilterByEducationCommand FilterByEducationFirstCommand = new FilterByEducationCommand(first);
        FilterByEducationCommand FilterByEducationSecondCommand = new FilterByEducationCommand(second);

        // same object -> returns true
        assertTrue(FilterByEducationFirstCommand.equals(FilterByEducationFirstCommand));

        // same values -> returns true
        FilterByEducationCommand FilterByEducationFirstCommandCopy = new FilterByEducationCommand(first);
        assertTrue(FilterByEducationFirstCommand.equals(FilterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterByEducationFirstCommand.equals(1));

        // null -> returns false
        assertFalse(FilterByEducationFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(FilterByEducationFirstCommand.equals(FilterByEducationSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EducationFilterPredicate predicate = new EducationFilterPredicate("Sec");
        FilterByEducationCommand command = new FilterByEducationCommand("Sec");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
