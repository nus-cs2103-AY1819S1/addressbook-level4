package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;


public class SuggestionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {
        String first = "Alice";
        SuggestionCommand suggestionFirstCommand = new SuggestionCommand(first);
        // same object -> returns true
        assertTrue(suggestionFirstCommand.equals(suggestionFirstCommand));

        // same values -> returns true
        SuggestionCommand suggestionFirstCommandCopy = new SuggestionCommand(first);
        assertTrue(suggestionFirstCommand.equals(suggestionFirstCommandCopy));

        // different types -> returns false
        assertFalse(suggestionFirstCommand.equals(1));

        String second = "Bob";

        SuggestionCommand suggestionSecondCommand = new SuggestionCommand(second);
        // same object -> returns true
        assertTrue(suggestionSecondCommand.equals(suggestionSecondCommand));

        // same values -> returns true
        SuggestionCommand suggestionSecondCommandCopy = new SuggestionCommand(second);
        assertTrue(suggestionSecondCommand.equals(suggestionSecondCommandCopy));

        // different types -> returns false
        assertFalse(suggestionFirstCommand.equals(1));
    }

    
    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

