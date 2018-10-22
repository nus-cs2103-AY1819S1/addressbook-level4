package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FeeFilterPredicate;
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

public class FilterByGradeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = new String("20");
        String second = new String(" ");


        FilterByGradeCommand FilterByGradeFirstCommand = new FilterByGradeCommand(first);
        FilterByGradeCommand FilterByGradeSecondCommand = new FilterByGradeCommand(second);

        // same object -> returns true
        assertTrue(FilterByGradeFirstCommand.equals(FilterByGradeFirstCommand));

        // same values -> returns true
        FilterByGradeCommand FilterByEducationFirstCommandCopy = new FilterByGradeCommand(first);
        assertTrue(FilterByGradeFirstCommand.equals(FilterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(FilterByGradeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(FilterByGradeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(FilterByGradeFirstCommand.equals(FilterByGradeSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FeeFilterPredicate predicate = new FeeFilterPredicate(20);
        FilterByGradeCommand command = new FilterByGradeCommand("20");
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
