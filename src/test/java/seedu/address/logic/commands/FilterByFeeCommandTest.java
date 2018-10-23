package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FeeFilterPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class FilterByFeeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String first = "20";
        String second = " ";


        FilterByFeeCommand filterByFeeFirstCommand = new filterByFeeCommand(first);
        FilterByFeeCommand filterByFeeSecondCommand = new filterByFeeCommand(second);

        // same object -> returns true
        assertTrue(filterByFeeFirstCommand.equals(filterByFeeFirstCommand));

        // same values -> returns true
        FilterByFeeCommand filterByEducationFirstCommandCopy = new FilterByFeeCommand(first);
        assertTrue(filterByFeeFirstCommand.equals(filterByEducationFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterByFeeFirstCommand.equals(1));


        // different person -> returns false
        assertFalse(filterByFeeFirstCommand.equals(filterByFeeSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FeeFilterPredicate predicate = new FeeFilterPredicate(20);
        FilterByFeeCommand command = new FilterByFeeCommand("20");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
