package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.model.person.NameContainsKeywordsPredicate;


public class SuggestionCommandTest {


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

